package cl.duoc.getmensajes.processor;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.getmensajes.dto.Mensaje;
import cl.duoc.getmensajes.dto.MensajesResultSet;
import cl.duoc.getmensajes.dto.ResponseDto;
import cl.duoc.getmensajes.errors.EmptyResponseException;
import cl.duoc.getmensajes.util.Utility;

import oracle.jdbc.OracleType;
import oracle.jdbc.OracleTypes;

/**
 * Processor GetMensajeProcessor
 * Genera llamada a PL LIBROVIRTUALCLASES.LVC_PORTAL_DOCENTE.GET_MESAJE_DOCENTE
 * 
 * @author Gonzalo Cabrera B., Crosswave SPA , Diciembre 2020
 * @version 1.0
 * 
 * Email: gcabrera@crosswave.cl
 */

public class GetMensajeProcessor {

	private static final Logger log = Logger.getLogger(GetMensajeProcessor.class.getName());
	
	private String nombreUsuario;
	private String dbSource;

	public GetMensajeProcessor(String nombreUsuario, String dbSource) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.dbSource = dbSource;

	}

	public ResponseDto getMensajes() throws Exception{

		Connection connection = null;
		try {
			// Establish a connection
			connection = initDB();
			CallableStatement callableStatement = prepareStatement(connection);
			ResultSet horariosResultSet = (ResultSet) callableStatement.getObject(2);

			if (Objects.isNull(horariosResultSet)) {
				throw new EmptyResponseException("Respuesta vacia");
			}

			JSONArray mensajeArray = Utility.convertToJSONArray(horariosResultSet);
			List<Mensaje> mensajeList = mapHorarioList(mensajeArray);
			MensajesResultSet mensajes = new MensajesResultSet();
			mensajes.setMensajes(mensajeList);			
			ResponseDto response = new ResponseDto("001", "NA", mensajes);
			callableStatement.close();
			return response;
		} catch (SQLException e) {			
			System.err.println(e.getErrorCode() + e.getMessage());
		} finally {
			connection.close();
			log.info("CIERRA CONEXION DB");
		}
		return null;
	}

	private Connection initDB() throws SQLException, NamingException {
		 InitialContext ctx = new InitialContext();
		 DataSource ds = (DataSource)ctx.lookup(dbSource);
		 log.info("INICIA CONEXION DB : "+dbSource);
		 return ds.getConnection();
	}

	private CallableStatement prepareStatement(Connection connection) throws SQLException {
		log.info("NOMBRE-USUARIO : "+this.nombreUsuario);
		CallableStatement callableStatement = connection
				.prepareCall("{call LIBROVIRTUALCLASES.LVC_PORTAL_DOCENTE.GET_MESAJE_DOCENTE(?,?,?,?)}");
		callableStatement.setString(1, this.nombreUsuario);
		callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
		callableStatement.registerOutParameter(3, OracleType.NUMBER);
		callableStatement.registerOutParameter(4, OracleType.VARCHAR2);
		callableStatement.executeUpdate();
		return callableStatement;
	}

	private List<Mensaje> mapHorarioList(JSONArray array)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Mensaje> mensajeList = new ArrayList<Mensaje>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject o = array.getJSONObject(i);
			ObjectMapper mapper = new ObjectMapper();
			Mensaje mensaje = mapper.readValue(o.toString(), Mensaje.class);
			mensajeList.add(mensaje);
		}
		return mensajeList;
	}

}

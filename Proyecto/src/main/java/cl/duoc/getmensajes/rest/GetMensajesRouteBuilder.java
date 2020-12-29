package cl.duoc.getmensajes.rest;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.model.rest.RestBindingMode;

import com.fasterxml.jackson.core.JsonParseException;

import cl.duoc.getmensajes.dto.ResponseDto;
import cl.duoc.getmensajes.processor.GetMensajeProcessor;
import cl.duoc.getmensajes.util.UtilProperties;


/**
 * RouteBuilder GetCursosRouteBuilder
 * Genera rutas para servicio CAMEL 
 * 
 * @author Gonzalo Cabrera B., Crosswave SPA , Diciembre 2020
 * @version 1.0
 * 
 * Email: gcabrera@crosswave.cl
 */

@ApplicationScoped
@ContextName("GetMensajesDocente-camel-context")
public class GetMensajesRouteBuilder extends RouteBuilder {

	private static final Logger logger = Logger.getLogger(GetMensajesRouteBuilder.class.getName());

		public void configure() throws Exception {

		/**
		 * Configure an error handler to trap instances where the data posted to the
		 * REST API is invalid
		 */
		onException(JsonParseException.class).handled(true).setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.TEXT_PLAIN)).setBody()
				.constant("Invalid json data: ${body}");

		/**
		 * Properties Config
		 */
		UtilProperties utilProperties = new UtilProperties();
		try {
			utilProperties.loadFile("/EAP/apps/GetMensajesDocente/getMensajesDocente.properties", true);
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"No se ha podido iniciar la aplicación,\n ERROR al cargar propiedades de arranque -> '/EAP/apps/GetMensajesDocente/getMensajesDocente.properties'",
					e);
			throw new Exception(e.getMessage());
		}

		/**
		 * Configuración REST usando la im plementacion undertow
		 * 
		 */
		restConfiguration().bindingMode(RestBindingMode.json).component("undertow")
				.dataFormatProperty("prettyPrint", "true").contextPath("GetMensajesDocente/api")
				.host(System.getProperty("jboss.bind.address", "127.0.0.1")).port(8080).enableCORS(true)
				.corsHeaderProperty("Access-Control-Allow-Origin", "*")
				.corsHeaderProperty("Access-Control-Allow-Headers",
						"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization")
				.apiProperty("api.title", "GetMensajesDocente REST API").apiProperty("api.version", "1.0")
				.apiContextPath("swagger");

		// Configurar el EndPoint

		/**
		 * Configuracion Path REST para Proveedores
		 */
		logger.info("Configurando Path EndPoint /mensaje/parametros/{nombre-usuario} ");
		
		/**
		 * Configuracion Path Routes
		 */

		rest("/mensaje").get("/parametros/{nombre-usuario}")
						.consumes("application/json")
						.produces("application/json")
						.outType(ResponseDto.class)
						.description("OBTIENE MENSAJES DE DOCENTE DUOC")
						.to("direct:getMensajes");
		
		from("direct:getMensajes").process(exchange -> {
			log.info("OBTIENE MENSAJES DE DOCENTE DUOC");
			String nombreUsuario = (String) exchange.getIn().getHeader("nombre-usuario");
			log.info("nombre-usuario = " + nombreUsuario);
			String db = utilProperties.get("dbsource");
			log.info("DATASOURCE = " + db);
			GetMensajeProcessor horarioService = new GetMensajeProcessor(nombreUsuario, db);
			HashMap<String, Object> mapa = new HashMap<>();
			mapa.put("Content-Type", "aplication/json");
			exchange.getIn().setHeaders(mapa);
			exchange.getIn().setBody(horarioService.getMensajes());
		});

	}
}

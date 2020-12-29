package cl.duoc.getmensajes.dto;

import java.io.Serializable;


public class ResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoRetorno;
	private String descripcionError;
	private MensajesResultSet resultSet;
	
	public ResponseDto(String codigoRetorno, String descripcionError,MensajesResultSet resultSet) {
		super();
		this.codigoRetorno = codigoRetorno;
		this.descripcionError = descripcionError;
		this.resultSet = resultSet;
	}

	public String getCodigoRetorno() {
		return codigoRetorno;
	}

	public void setCodigoRetorno(String codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
	}

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	

	public MensajesResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(MensajesResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public String toStringError() {
		return "{\"codigoRetorno\":" + codigoRetorno + ", \"descripcionError\":\"" + descripcionError + "\", \"resultSet\":"
				+ resultSet + "}";
	}
	

}

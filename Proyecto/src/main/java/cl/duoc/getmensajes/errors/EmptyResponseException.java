package cl.duoc.getmensajes.errors;


public class EmptyResponseException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmptyResponseException (String errorMsg)  {
		super(errorMsg);
		
	}
	
	
	
} 
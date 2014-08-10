package br.com.webnow.exception;

public class UsuarioException extends RuntimeException{

	
	private static final long serialVersionUID = 8429089458149052874L;
	
	 public UsuarioException(String message) {
	        super(message);
	    }
	    
	    public UsuarioException(String message, Throwable cause) {
	        super(message, cause);
	    }

}

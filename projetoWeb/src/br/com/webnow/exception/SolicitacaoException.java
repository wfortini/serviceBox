package br.com.webnow.exception;

public class SolicitacaoException extends RuntimeException{
	
	private static final long serialVersionUID = 8429089458149052874L;
	private int code;
	
	public SolicitacaoException(String message, int code) {
        super(message);
        this.code = code;
    }
	
	public SolicitacaoException(String message) {
	        super(message);
    }
    
    public SolicitacaoException(String message, Throwable cause) {
        super(message, cause);
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}

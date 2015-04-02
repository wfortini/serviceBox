package br.com.mobilenow.exception;

public class GCMException extends Exception{

	
	private static final long serialVersionUID = 2264150520095072278L;
	
	public GCMException(String msg) {
		super(msg);
	}
	
	public GCMException(Throwable arg0) {
		super(arg0);
	}
	
	public GCMException(String msg, Throwable arg1) {
		super(msg, arg1);
	}
	
	public GCMException() {
		
	}

}

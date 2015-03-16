package br.com.servicebox.common.net;


public class ListaPrestarServicoResponse extends Response{
	
	private static final long serialVersionUID = 3624169665091174312L;
	
	private PrestacaoLocalizada[] prestacaoLocalizadas;
	
	public ListaPrestarServicoResponse() {
		
	}	
	
	public ListaPrestarServicoResponse(boolean sucesso, String message, Long nodeId, int code){
		this.setSucesso(sucesso);
		this.setMessage(message);
		this.setNodeId(nodeId);
		this.setCode(code);
	}

	public PrestacaoLocalizada[] getPrestacaoLocalizadas() {
		return prestacaoLocalizadas;
	}

	public void setPrestacaoLocalizadas(PrestacaoLocalizada[] prestacaoLocalizadas) {
		this.prestacaoLocalizadas = prestacaoLocalizadas;
	}
	
	

}

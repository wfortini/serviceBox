package br.com.servicebox.common.net;

public class PrestarServicoRequest extends Request{	
	
	private static final long serialVersionUID = 8497195528947212068L;
	
	private String enderecoPartida;
	private Double latitude;
	private Double longitude;
	private Double distancia;
	private String enderecoDestino;	
	private boolean soAmigos;
	private boolean soAmigosDosAmigos;
	private boolean todos;
	
	// Planejamento
	private boolean segunda;
	private boolean terca;
	private boolean quarta;
	private boolean quinta;
	private boolean sexta;
	private boolean sabado;
	private boolean domingo;
	private String horaFixa;
	private String horaEntre;
	private String horaE;
	
	

}

package br.com.webnow.domain;

import java.util.Date;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * Classe que relaciona um usuario a um servico
 * @author wpn0510
 *
 */
@RelationshipEntity(type = "PRESTA_SERVICO")
public class PrestarServico {
	
	@GraphId
	private Long nodeId;
	
	@StartNode
	private Usuario usuario;
	@EndNode
	private Servico servico;
	
	private Date data;
	private boolean ativa;
	
	/**
	 * Planejamento
	 */
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
	
	/**
	 * Itinerario
	 */
	private boolean soAmigos;
	private boolean soAmigosDosAmigos;
	private boolean todos;
	
	/**
	 * Destino
	 */
	private String enderecoDestino;
	private Double latitudeDestino;
	private Double longitudeDestino;
	
	/**
	 * Partida
	 */
	private String enderecoPartida;
	private Double latitudePartida;
	private Double longitudePartida;
	
	public PrestarServico() {
		
	}

	
	public boolean isSegunda() {
		return segunda;
	}


	public void setSegunda(boolean segunda) {
		this.segunda = segunda;
	}


	public boolean isTerca() {
		return terca;
	}


	public void setTerca(boolean terca) {
		this.terca = terca;
	}


	public boolean isQuarta() {
		return quarta;
	}


	public void setQuarta(boolean quarta) {
		this.quarta = quarta;
	}


	public boolean isQuinta() {
		return quinta;
	}


	public void setQuinta(boolean quinta) {
		this.quinta = quinta;
	}


	public boolean isSexta() {
		return sexta;
	}


	public void setSexta(boolean sexta) {
		this.sexta = sexta;
	}


	public boolean isSabado() {
		return sabado;
	}


	public void setSabado(boolean sabado) {
		this.sabado = sabado;
	}


	public boolean isDomingo() {
		return domingo;
	}


	public void setDomingo(boolean domingo) {
		this.domingo = domingo;
	}


	public String getHoraFixa() {
		return horaFixa;
	}


	public void setHoraFixa(String horaFixa) {
		this.horaFixa = horaFixa;
	}


	public String getHoraEntre() {
		return horaEntre;
	}


	public void setHoraEntre(String horaEntre) {
		this.horaEntre = horaEntre;
	}


	public String getHoraE() {
		return horaE;
	}


	public void setHoraE(String horaE) {
		this.horaE = horaE;
	}


	public boolean isSoAmigos() {
		return soAmigos;
	}


	public void setSoAmigos(boolean soAmigos) {
		this.soAmigos = soAmigos;
	}


	public boolean isSoAmigosDosAmigos() {
		return soAmigosDosAmigos;
	}


	public void setSoAmigosDosAmigos(boolean soAmigosDosAmigos) {
		this.soAmigosDosAmigos = soAmigosDosAmigos;
	}


	public boolean isTodos() {
		return todos;
	}


	public void setTodos(boolean todos) {
		this.todos = todos;
	}


	public String getEnderecoDestino() {
		return enderecoDestino;
	}


	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}


	public Double getLatitudeDestino() {
		return latitudeDestino;
	}


	public void setLatitudeDestino(Double latitudeDestino) {
		this.latitudeDestino = latitudeDestino;
	}


	public Double getLongitudeDestino() {
		return longitudeDestino;
	}


	public void setLongitudeDestino(Double longitudeDestino) {
		this.longitudeDestino = longitudeDestino;
	}


	public String getEnderecoPartida() {
		return enderecoPartida;
	}


	public void setEnderecoPartida(String enderecoPartida) {
		this.enderecoPartida = enderecoPartida;
	}


	public Double getLatitudePartida() {
		return latitudePartida;
	}


	public void setLatitudePartida(Double latitudePartida) {
		this.latitudePartida = latitudePartida;
	}


	public Double getLongitudePartida() {
		return longitudePartida;
	}


	public void setLongitudePartida(Double longitudePartida) {
		this.longitudePartida = longitudePartida;
	}


	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}	

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

	
	
	

}
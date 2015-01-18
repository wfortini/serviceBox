package br.com.webnow.domain;

import java.util.Date;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelatedTo;
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
	private Long id;
	
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
	
	@RelatedTo(type = "VEM_DE", direction = Direction.INCOMING)
	private Partida partida;
	
	@RelatedTo(type = "VAI_PARA", direction = Direction.INCOMING)
	private Destino destino;
	
	
	
	
	private Double distanciaPartidaDestino;
	private Double distanciaMaxima;	
	
	
	
	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public Destino getDestino() {
		return destino;
	}

	public void setDestino(Destino destino) {
		this.destino = destino;
	}

	public Double getDistanciaPartidaDestino() {
		return distanciaPartidaDestino;
	}
	
	public Double getDistanciaMaxima() {
		return distanciaMaxima;
	}

	public void setDistanciaPartidaDestino(Double distanciaPartidaDestino) {
		this.distanciaPartidaDestino = distanciaPartidaDestino;
	}

	public void setDistanciaMaxima(Double distanciaMaxima) {
		this.distanciaMaxima = distanciaMaxima;
	}


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

	public Long getId() {
		return id;
	}

	public void setId(Long nodeId) {
		this.id = nodeId;
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

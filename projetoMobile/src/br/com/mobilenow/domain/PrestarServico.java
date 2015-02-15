package br.com.mobilenow.domain;

import java.util.Date;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.RelatedTo;

import br.com.servicebox.common.domain.Localizacao;
import br.com.servicebox.common.domain.Servico;
import br.com.servicebox.common.domain.Usuario;
import br.com.webnow.domain.GeoDestino;
import br.com.webnow.domain.GeoPartida;

/**
 * Classe que relaciona um usuario a um servico ( Common )
 * @author wpn0510
 *
 */
public class PrestarServico {
	
	
	private Long nodeId;	
	private Date data;
	private boolean ativa;
	private String descricao;
	

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
	
	
	private GeoPartida partida;
	
	
	private GeoDestino destino;	
	
	
	

}

package br.com.webnow.repository.servico;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import br.com.webnow.domain.Servico;
import br.com.webnow.domain.Usuario;

public interface ServicoRepository extends GraphRepository<Servico>, NamedIndexRepository<Servico>,
                                           RelationshipOperationsRepository<Servico>{
	
	Servico findByTipoServico(Integer tipo);

}

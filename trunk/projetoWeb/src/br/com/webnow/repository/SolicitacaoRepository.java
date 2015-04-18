package br.com.webnow.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import br.com.webnow.domain.Solicitacao;

public interface SolicitacaoRepository extends GraphRepository<Solicitacao>{

	Solicitacao findById(Long id);
}

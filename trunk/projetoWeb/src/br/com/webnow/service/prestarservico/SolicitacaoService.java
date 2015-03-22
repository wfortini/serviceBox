package br.com.webnow.service.prestarservico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.webnow.repository.UsuarioRepository;

@Service
public class SolicitacaoService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

}

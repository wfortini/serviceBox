package br.com.webnow.util;



import java.util.List;
import java.util.Set;

import br.com.servicebox.common.domain.StatusSolicitacao;
import br.com.servicebox.common.json.PrestarServicoJSON;
import br.com.servicebox.common.json.ServicoJSON;
import br.com.servicebox.common.net.PrestacaoLocalizada;
import br.com.servicebox.common.net.PrestarServicoRequest;
import br.com.servicebox.common.net.UsuarioResponse;
import br.com.webnow.domain.Carona;
import br.com.webnow.domain.Estacionamento;
import br.com.webnow.domain.GeoDestino;
import br.com.webnow.domain.GeoPartida;
import br.com.webnow.domain.Itinerario;
import br.com.webnow.domain.Planejamento;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.domain.Reboque;
import br.com.webnow.domain.Servico;
import br.com.webnow.domain.Solicitacao;
import br.com.webnow.domain.TipoServico;
import br.com.webnow.domain.Usuario;
import br.com.webnow.returno.cypher.ServicoLocalizado;

public class ServiceBoxWebUtil {
	
	public static PrestarServico preencherPrestacao(PrestarServico servico, 
			    Itinerario itinerario, Planejamento planejamento){
		
		
		servico.setDomingo(planejamento.isDomingo());
		servico.setSegunda(planejamento.isSegunda());
		servico.setTerca(planejamento.isTerca());
		servico.setQuarta(planejamento.isQuarta());
		servico.setQuinta(planejamento.isQuinta());
		servico.setSexta(planejamento.isSexta());
		servico.setSabado(planejamento.isSabado());
		servico.setHoraFixa(planejamento.getHoraFixa());
		servico.setHoraEntre(planejamento.getHoraEntre());
		servico.setHoraE(planejamento.getHoraE());		
		servico.setDistanciaPartidaDestino(itinerario.getDistanciaPartidaDestino());
		servico.setDistanciaMaxima(itinerario.getDistanciaMaxima());
		servico.setLugares(planejamento.getLugares());
		
		GeoPartida partida = new GeoPartida();
		partida.setEnderecoPartida(itinerario.getPartida().getEnderecoPartida());
		partida.setLatitude(itinerario.getPartida().getLatitude());
		partida.setLongitude(itinerario.getPartida().getLongitude());
		servico.setPartida(partida);
		
		GeoDestino destino = new GeoDestino();
		destino.setEnderecoDestino(itinerario.getDestino().getEnderecoDestino());
		destino.setLatitude(itinerario.getDestino().getLatitude());
		destino.setLongitude(itinerario.getDestino().getLongitude());
		servico.setDestino(destino);
		
		return servico;
	}
	
	
	public static Itinerario preencherObjetoItinerario(PrestarServicoRequest request){
		
		Itinerario itinerario = new Itinerario();
		GeoPartida partida = new GeoPartida();
		GeoDestino destino = new GeoDestino();
		
		partida.setEnderecoPartida(request.getEnderecoPartida());
		partida.setLatitude(request.getLatitudePartida());
		partida.setLongitude(request.getLongitudePartida());
		
		destino.setEnderecoDestino(request.getEnderecoDestino());
		destino.setLatitude(request.getLatitudeDestino());
		destino.setLongitude(request.getLongitudeDestino());
		
		itinerario.setPartida(partida);
		itinerario.setDestino(destino);
		itinerario.setSoAmigos(request.isSoAmigos());
		itinerario.setSoAmigosDosAmigos(request.isSoAmigosDosAmigos());
		itinerario.setTodos(request.isTodos());
		itinerario.setDistanciaMaxima(request.getDistanciaMaxima());
		itinerario.setDistanciaPartidaDestino(request.getDistanciaPartidaDestino());
		return itinerario;
	}
	
	public static Planejamento preencherObjetoPlanejamento(PrestarServicoRequest request){
		
		Planejamento planejamento = new Planejamento();
		planejamento.setDomingo(request.isDomingo());
		planejamento.setSegunda(request.isSegunda());
		planejamento.setTerca(request.isTerca());
		planejamento.setQuarta(request.isQuarta());
		planejamento.setQuinta(request.isQuinta());
		planejamento.setSexta(request.isSexta());
		planejamento.setSabado(request.isSabado());
		planejamento.setHoraFixa(request.getHoraFixa());
		planejamento.setHoraEntre(request.getHoraEntre());
		planejamento.setHoraE(request.getHoraE());
		planejamento.setLugares(request.getLugares());
		
		return planejamento;		
		
	}
	
	
	public static Servico getServico(TipoServico tipo){
		
         switch (tipo) {
		case CARONA:
			
			return new Carona();
			
        case ESTACIONAMENTO:
			
			return new Estacionamento();
			
       case REBOQUE:
	
	       	return new Reboque();

		default:
			
			return null;
		}		
		
	}
	
	public static PrestarServicoJSON[] preencherPrestarServicoJSON(List<PrestarServico> lista){
		
		PrestarServicoJSON[] prestarServicoJSONs = null;
		
		if(lista != null && lista.size() > 0){
			
			int i = 0;
			prestarServicoJSONs = new PrestarServicoJSON[lista.size()];
			for(PrestarServico p : lista){
				
				PrestarServicoJSON json = new PrestarServicoJSON();
				
				json.setNodeId(p.getId());
				json.setAtivo(p.isAtiva());
				json.setData(p.getData());
				json.setDescricao(p.getDescricao());
				json.setDistanciaMaxima(p.getDistanciaMaxima());
				json.setDistanciaPartidaDestino(p.getDistanciaPartidaDestino());
				json.setDomingo(p.isDomingo());
				json.setEnderecoPartida(p.getPartida().getEnderecoPartida());
				json.setEnderecoDestino(p.getDestino().getEnderecoDestino());
				json.setHoraE(p.getHoraE());
				json.setHoraEntre(p.getHoraEntre());
				json.setHoraFixa(p.getHoraFixa());
				json.setLatitudeDestino(p.getDestino().getLatitude());
				json.setLongitudeDestino(p.getDestino().getLongitude());
				json.setLatitudePartida(p.getPartida().getLatitude());
				json.setLongitudePartida(p.getPartida().getLongitude());
				json.setQuarta(p.isQuarta());
				json.setQuinta(p.isQuinta());
				json.setTerca(p.isTerca());
				json.setSegunda(p.isSegunda());
				json.setSexta(p.isSexta());
				json.setSabado(p.isSabado());
				json.setSoAmigos(p.isSoAmigos());
				json.setSoAmigosDosAmigos(p.isSoAmigosDosAmigos());
				json.setTodos(p.isTodos());
				json.setLugares(p.getLugares());
				
				prestarServicoJSONs[i] = json;
				i++;	   			
					
			}		
		}
			
	return 	prestarServicoJSONs;	
			
	}
	
	/**
	 * 
	 * @param usuario
	 * @return
	 */
	public static ServicoJSON[] preencherServicoJSON(Usuario usuario) {
		
		ServicoJSON[] servicoJSONs = null;
		
		Set<Servico> lista = usuario.getServicosDisponiveis();
		if(lista != null && lista.size() > 0){
			//this.servicos = lista.toArray(new Servico[lista.size()]);  
			
			int i = 0;
			servicoJSONs = new ServicoJSON[lista.size()];
			for(Servico s : lista){
				
				ServicoJSON sj = new ServicoJSON(s.getId(),
						         s.getServicoDisponivel(), s.getDataInicialPrestacao(), s.getTipoServico());
				
				servicoJSONs[i] = sj;
				i++;	   			
				
			}		
			
		}
		
		return servicoJSONs;
	} 
	
	public static PrestacaoLocalizada[] preencherPrestacaoLocalizada(List<ServicoLocalizado> listaServico){
		
		PrestacaoLocalizada[] arrayPrestacao = null;
		
		  if(listaServico != null && listaServico.size() > 0){
			  
			  
			  int cont = 0;
			  arrayPrestacao = new PrestacaoLocalizada[listaServico.size()];
			  for(ServicoLocalizado sl : listaServico){
				  
				  PrestacaoLocalizada pl = new PrestacaoLocalizada();
				  
				  UsuarioResponse ur = new UsuarioResponse();
				  ur.setNodeId(sl.getUsuario().getId());
				  ur.setNome(sl.getUsuario().getNome());
				  ur.setLogin(sl.getUsuario().getLogin());
				  ur.setSobreNome(sl.getUsuario().getSobreNome());
				  ur.setApelido(sl.getUsuario().getApelido());
				  ur.setDataCadastro(sl.getUsuario().getDataCadastro());
				  ur.setFotoPerfil(sl.getUsuario().getFotoPerfil());
				  ur.setSexo(sl.getUsuario().getSexo());
				  if(sl.getUsuario().getTelefone() != null){
					  ur.setTelefone(sl.getUsuario().getTelefone());  
				  }else{
					  ur.setTelefone("");
				  }
				  
				  ur.setRegIdGCM(sl.getUsuario().getRegIdGCM());
				  ur.setSocialId(sl.getUsuario().getSocialId());		  
				  
				  pl.setUsuario(ur);
				  
				  PrestarServicoJSON json = new PrestarServicoJSON();
				  
				 json.setNodeId(sl.getPrestarServico().getId());
				json.setAtivo(sl.getPrestarServico().isAtiva());
				json.setData(sl.getPrestarServico().getData());
				if(sl.getPrestarServico().getDescricao() != null){
					json.setDescricao(sl.getPrestarServico().getDescricao());	
				}else{
					json.setDescricao("");
				}
				
				json.setDistanciaMaxima(sl.getPrestarServico().getDistanciaMaxima());
				json.setDistanciaPartidaDestino(sl.getPrestarServico().getDistanciaPartidaDestino());
				json.setDomingo(sl.getPrestarServico().isDomingo());
				json.setEnderecoPartida(sl.getPrestarServico().getPartida().getEnderecoPartida());
				json.setEnderecoDestino(sl.getPrestarServico().getDestino().getEnderecoDestino());
				json.setHoraE(sl.getPrestarServico().getHoraE());
				json.setHoraEntre(sl.getPrestarServico().getHoraEntre());
				json.setHoraFixa(sl.getPrestarServico().getHoraFixa());
				json.setLatitudeDestino(sl.getPrestarServico().getDestino().getLatitude());
				json.setLongitudeDestino(sl.getPrestarServico().getDestino().getLongitude());
				json.setLatitudePartida(sl.getPrestarServico().getPartida().getLatitude());
				json.setLongitudePartida(sl.getPrestarServico().getPartida().getLongitude());
				json.setQuarta(sl.getPrestarServico().isQuarta());
				json.setQuinta(sl.getPrestarServico().isQuinta());
				json.setTerca(sl.getPrestarServico().isTerca());
				json.setSegunda(sl.getPrestarServico().isSegunda());
				json.setSexta(sl.getPrestarServico().isSexta());
				json.setSabado(sl.getPrestarServico().isSabado());
				json.setSoAmigos(sl.getPrestarServico().isSoAmigos());
				json.setSoAmigosDosAmigos(sl.getPrestarServico().isSoAmigosDosAmigos());
				json.setTodos(sl.getPrestarServico().isTodos());
				json.setLugares(sl.getPrestarServico().getLugares());
				
				pl.setPrestarServicoJSON(json);
				  
				arrayPrestacao[cont] = pl;
				cont++;		  
				  
			  }
			  
			  
		  }
		
		return arrayPrestacao;
		
	}
	
	
	public static String getRegIDGCMConformeStatusSolicitacao(Solicitacao solicitacao){
		
		StatusSolicitacao status = StatusSolicitacao.getStatusSolicitacao(solicitacao.getStatusSolicitacao());
		
		String descStatus = status.getDescricao();
		
		if(descStatus.contains("solicitante")){
			return solicitacao.getSolicitante().getRegIdGCM();
		}else{
			return solicitacao.getSolicitado().getRegIdGCM();
		}		
		
		
	}
		
	
	
	

}

package br.com.webnow.util;



import java.util.List;

import br.com.servicebox.common.domain.Itinerario;
import br.com.servicebox.common.domain.Planejamento;
import br.com.servicebox.common.json.PrestarServicoJSON;
import br.com.servicebox.common.json.ServicoJSON;
import br.com.servicebox.common.net.PrestarServicoRequest;
import br.com.webnow.domain.Carona;
import br.com.webnow.domain.Estacionamento;
import br.com.webnow.domain.GeoDestino;
import br.com.webnow.domain.GeoPartida;
import br.com.webnow.domain.PrestarServico;
import br.com.webnow.domain.Reboque;
import br.com.webnow.domain.Servico;
import br.com.webnow.domain.TipoServico;

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
		br.com.servicebox.common.domain.Partida partida = new br.com.servicebox.common.domain.Partida();
		br.com.servicebox.common.domain.Destino destino = new br.com.servicebox.common.domain.Destino();
		
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
				
				prestarServicoJSONs[i] = json;
				i++;	   			
					
			}		
		}
			
	return 	prestarServicoJSONs;	
			
	}
		
	
	
	

}

package br.com.webnow.util;



import br.com.servicebox.common.domain.Itinerario;
import br.com.servicebox.common.domain.Planejamento;
import br.com.servicebox.common.net.PrestarServicoRequest;
import br.com.webnow.domain.Carona;
import br.com.webnow.domain.Destino;
import br.com.webnow.domain.Estacionamento;
import br.com.webnow.domain.Partida;
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
		
		Partida partida = new Partida();
		partida.setEnderecoPartida(itinerario.getPartida().getEnderecoPartida());
		partida.setLatitude(itinerario.getPartida().getLatitude());
		servico.setPartida(partida);
		
		Destino destino = new Destino();
		destino.setEnderecoDestino(itinerario.getDestino().getEnderecoDestino());
		destino.setLatitude(itinerario.getDestino().getLatitude());
		destino.setLongitude(itinerario.getPartida().getLongitude());
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
	
	

}

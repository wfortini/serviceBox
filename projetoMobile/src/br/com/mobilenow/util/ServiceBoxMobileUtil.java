package br.com.mobilenow.util;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.text.TextUtils;
import br.com.mobilenow.R;
import br.com.mobilenow.domain.Carona;
import br.com.mobilenow.domain.Estacionamento;
import br.com.mobilenow.domain.GeoDestino;
import br.com.mobilenow.domain.GeoPartida;
import br.com.mobilenow.domain.Itinerario;
import br.com.mobilenow.domain.Planejamento;
import br.com.mobilenow.domain.PrestarServico;
import br.com.mobilenow.domain.Reboque;
import br.com.mobilenow.domain.Usuario;
import br.com.servicebox.android.common.util.CommonUtils;
import br.com.servicebox.android.common.util.GuiUtils;
import br.com.servicebox.common.domain.TipoServico;
import br.com.servicebox.common.json.PrestarServicoJSON;
import br.com.servicebox.common.json.ServicoJSON;
import br.com.servicebox.common.net.ListaServicoResponse;
import br.com.servicebox.common.net.LoginResponse;
import br.com.servicebox.common.net.PrestarServicoRequest;
import br.com.servicebox.common.net.Response;

public class ServiceBoxMobileUtil {
	
	private static final String TAG = ServiceBoxMobileUtil.class.getSimpleName();
	
	public static PrestarServicoRequest preencheObjetoPrestarServicoRequest(Itinerario itinerario, 
			         Planejamento planejamento){
		
		PrestarServicoRequest request = new PrestarServicoRequest();
		request.setDomingo(planejamento.isDomingo());
		request.setSegunda(planejamento.isSegunda());
		request.setTerca(planejamento.isTerca());
		request.setQuarta(planejamento.isQuarta());
		request.setQuinta(planejamento.isQuinta());
		request.setSexta(planejamento.isSexta());
		request.setSabado(planejamento.isSabado());
		request.setHoraFixa(planejamento.getHoraFixa());
		request.setHoraEntre(planejamento.getHoraEntre());
		request.setHoraE(planejamento.getHoraE());
		
		request.setEnderecoDestino(itinerario.getDestino().getEnderecoDestino());
		request.setLatitudeDestino(itinerario.getDestino().getLatitude());
		request.setLongitudeDestino(itinerario.getDestino().getLongitude());
		
		request.setEnderecoPartida(itinerario.getPartida().getEnderecoPartida());
		request.setLatitudePartida(itinerario.getPartida().getLatitude());
		request.setLongitudePartida(itinerario.getPartida().getLongitude());
		request.setDistanciaPartidaDestino(itinerario.getDistanciaPartidaDestino());
		request.setDistanciaMaxima(itinerario.getDistanciaMaxima());
		
		return request;
		
		
	}
	
	public static boolean checkResponseValido(Response response)
    {
        boolean result = response.isSucesso();
        if (!result)
        {
            String message = response.getMessage();
            if (!TextUtils.isEmpty(message))
            {
                GuiUtils.alert(message);
            } else
            {
                GuiUtils.alert(R.string.erroNaoConhecido, response.getCode());
            }
        }
        return result;
    }
    

	/**
	 * Retorna um RestTemplate configurado
	 * @return restTemplate
	 */
	public static RestTemplate getRestTemplate(){
		
		FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        formHttpMessageConverter.setCharset(Charset.forName("UTF8"));
        
        RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add( formHttpMessageConverter );
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		
		 restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		 
		 return restTemplate;
		
		
	}
	
	public static String dateToString(Date data, int formato){
		
		DateFormat dateFormat = DateFormat.getDateInstance(formato);
		return dateFormat.format(data);
		
	}
	
   public static Date stringToDate(String data, int formato) throws ParseException{
		
		DateFormat dateFormat = DateFormat.getDateInstance(formato);
		return dateFormat.parse(data);
		
	}
   
   /**
    * Monta o Objeto Usuario com seus respectivos atributos List, Set, etc
    * @return Objeto Usuario da pacore br.com.servicebox.common.domain
    */
   public static Usuario preencherUsuario(LoginResponse response){
  	 
  	 Usuario usuario = new Usuario(response.getLogin(), response.getPassword(),
  			 response.getNome(), response.getSobreNome(), response.getSexo(), response.getApelido());
  	 usuario.setNodeId(response.getNodeId());
  	 usuario.setFotoPerfil(response.getFotoPerfil());
  	 usuario.setTelefone(response.getTelefone());
  	 usuario.setDataCadastro(response.getDataCadastro());
  	 
  	 if (response.getServicoJSONs() != null){
               
  		 for(ServicoJSON s : response.getServicoJSONs()){
      		 
      		 if(s.getTipoServico().equals(TipoServico.CARONA.getCodigo())){
      			 
      			 Carona c = new Carona();
      			 c.setNodeId(s.getNodeId());
      			 c.setDataInicialPrestacao(s.getDataInicialPrestacao());
      			 c.setServicoDisponivel(s.getServicoDisponivel());
      			 c.setTipoServico(s.getTipoServico());
      			 usuario.addServico(c);
      			 
      		 } else if(s.getTipoServico().equals(TipoServico.ESTACIONAMENTO.getCodigo())){
      			 
      			 Estacionamento c = new Estacionamento();
      			 c.setNodeId(s.getNodeId());
      			 c.setDataInicialPrestacao(s.getDataInicialPrestacao());
      			 c.setServicoDisponivel(s.getServicoDisponivel());
      			 c.setTipoServico(s.getTipoServico());
      			 usuario.addServico(c);
      			 
      		 } else if(s.getTipoServico().equals(TipoServico.REBOQUE.getCodigo())){
      			
      			 Reboque c = new Reboque();
      			 c.setNodeId(s.getNodeId());
      			 c.setDataInicialPrestacao(s.getDataInicialPrestacao());
      			 c.setServicoDisponivel(s.getServicoDisponivel());
      			 c.setTipoServico(s.getTipoServico());
      			 usuario.addServico(c);
      		 }
      		 
      	 }
      	 
      
  	 }
  	 
  	 return usuario;
  	 
   }
   
   /**
    * 
    * @param response
    * @return
    */
   public static List<PrestarServico> preencherServico(ListaServicoResponse response){
	   
	   List<PrestarServico> lista = new ArrayList<PrestarServico>();
	   
	   for(PrestarServicoJSON json : response.getPrestarServicoJSON()){
		   
		   PrestarServico servico = new PrestarServico();
		   
		   servico.setDescricao(json.getDescricao());
		   servico.setAtiva(json.isAtivo());
		   servico.setData(json.getData());
		   servico.setNodeId(json.getNodeId());
		   
		   Itinerario itinerario = new Itinerario();
		   GeoPartida partida = new GeoPartida();
		   GeoDestino destino = new GeoDestino();
		   
			partida.setEnderecoPartida(json.getEnderecoPartida());
			partida.setLatitude(json.getLatitudePartida());
			partida.setLongitude(json.getLongitudePartida());
			
			destino.setEnderecoDestino(json.getEnderecoDestino());
			destino.setLatitude(json.getLatitudeDestino());
			destino.setLongitude(json.getLongitudeDestino());
			
			itinerario.setPartida(partida);
			itinerario.setDestino(destino);
			itinerario.setSoAmigos(json.isSoAmigos());
			itinerario.setSoAmigosDosAmigos(json.isSoAmigosDosAmigos());
			itinerario.setTodos(json.isTodos());
			itinerario.setDistanciaMaxima(json.getDistanciaMaxima());
			itinerario.setDistanciaPartidaDestino(json.getDistanciaPartidaDestino());
			
			servico.setItinerario(itinerario);
			
			Planejamento planejamento = new Planejamento();
			planejamento.setDomingo(json.isDomingo());
			planejamento.setSegunda(json.isSegunda());
			planejamento.setTerca(json.isTerca());
			planejamento.setQuarta(json.isQuarta());
			planejamento.setQuinta(json.isQuinta());
			planejamento.setSexta(json.isSexta());
			planejamento.setSabado(json.isSabado());
			planejamento.setHoraFixa(json.getHoraFixa());
			planejamento.setHoraEntre(json.getHoraEntre());
			planejamento.setHoraE(json.getHoraE());
			planejamento.setLugares(json.getLugares());
			
			servico.setPlanejamento(planejamento);
			
			lista.add(servico);
		   
	   }
	   
	  return lista; 
	   
   }
   
   public static String getUrlImagemPerfil(String urlServer, String fotoPerfil, String loginPath){
		
		String urlTemp = "";
		
		if(urlServer != null && !urlServer.equals("")){
			
			urlTemp = urlServer.concat(":8080/projetoWeb/imagemPerfil?fileName=").concat(fotoPerfil)
					  .concat("&login=").concat(loginPath);
			
			
		}
		return urlTemp;
	}
   
   public static Date stringToTime(String time){
	   Date date = null;
	   Locale enLocale = new Locale("pt", "BR");
	  try{       
	   DateFormat sdf = new SimpleDateFormat("hh:mm:ss", enLocale);
	   date = sdf.parse(time);
	  }catch(ParseException e){
		  CommonUtils.error(TAG, "Erro parse string para Hora"); 
	  }
	   
	  return date; 
   }
}

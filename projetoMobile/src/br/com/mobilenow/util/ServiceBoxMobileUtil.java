package br.com.mobilenow.util;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.Log;
import br.com.mobilenow.MainActivity;
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
import br.com.servicebox.common.net.PrestacaoLocalizada;
import br.com.servicebox.common.net.PrestarServicoRequest;
import br.com.servicebox.common.net.Response;
import br.com.servicebox.common.net.UsuarioResponse;

public class ServiceBoxMobileUtil {
	
	private static final String REG_ID = "REG_ID";
	private static final String APP_VERSION = "appVersion";
	
	private static final String TAG = ServiceBoxMobileUtil.class.getSimpleName();
	
	public static PrestarServicoRequest preencheObjetoPrestarServicoRequest(Itinerario itinerario, 
			         Planejamento planejamento){
		
		PrestarServicoRequest request = new PrestarServicoRequest();
		if(planejamento != null){
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
				request.setLugares(planejamento.getLugares());
		}
		
		if(itinerario != null){
				request.setEnderecoDestino(itinerario.getDestino().getEnderecoDestino());
				request.setLatitudeDestino(itinerario.getDestino().getLatitude());
				request.setLongitudeDestino(itinerario.getDestino().getLongitude());
				request.setTodos(itinerario.isTodos());
				request.setSoAmigos(itinerario.isSoAmigos());
				request.setSoAmigosDosAmigos(itinerario.isSoAmigosDosAmigos());
				
				request.setEnderecoPartida(itinerario.getPartida().getEnderecoPartida());
				request.setLatitudePartida(itinerario.getPartida().getLatitude());
				request.setLongitudePartida(itinerario.getPartida().getLongitude());
				request.setDistanciaPartidaDestino(itinerario.getDistanciaPartidaDestino());
				request.setDistanciaMaxima(itinerario.getDistanciaMaxima());
		}
		
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
   public static Usuario preencherUsuario(UsuarioResponse response){
  	 
  	 Usuario usuario = new Usuario(response.getLogin(), response.getPassword(),
  			 response.getNome(), response.getSobreNome(), response.getSexo(), response.getApelido());
  	 usuario.setNodeId(response.getNodeId());
  	 usuario.setFotoPerfil(response.getFotoPerfil());
  	 usuario.setTelefone(response.getTelefone());
  	 usuario.setDataCadastro(response.getDataCadastro());
  	 usuario.setRegIdGCM(response.getRegIdGCM());
  	 usuario.setSocialId(response.getSocialId());
  	 
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
	   
	   if(response != null && response.getPrestarServicoJSON() != null){
	   
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
	   }
	   
	  return lista; 
	   
   }
   
   public static String getUrlImagemPerfil(String urlServer, String fotoPerfil, String loginPath){
		
		String urlTemp = "";
		
		if(fotoPerfil.contains("facebook")){
			return fotoPerfil;
		}
		
		if(loginPath == null){			
			Map<String, String> param = extrairLogin(fotoPerfil);
			fotoPerfil = param.get("nomeFoto");
			loginPath = param.get("login");			
		}
		
		if(urlServer != null && !urlServer.equals("")){
			
			urlTemp = urlServer.concat(":8080/projetoWeb/imagemPerfil?fileName=").concat(fotoPerfil)
					  .concat("&login=").concat(loginPath);
			
			
		}
		return urlTemp;
	}
   
   private static Map<String, String> extrairLogin(String param){
	   
	   String foto = "";
	   String login = "";
	   Map<String, String> dados = new HashMap<String, String>();
	   
	   if(param == null)
		   return null;
	   
	   int posicao = param.indexOf("+");
	   if(posicao >= 0){		   
		   foto = param.substring(0, posicao);
		   login = param.substring(posicao + 1); // não pegar o +		   
		   dados.put("nomeFoto", foto);
		   dados.put("login", login);
	   }else{
		   dados.put("nomeFoto", "default.jpg");
		   dados.put("login", "default");
	   }
	   
	   
	   
	  return dados; 
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
   
   public static Info preencherObjetoInfo(PrestacaoLocalizada localizada){
	   
	          Info info = new Info();
	      
	         info.setDescricao(localizada.getPrestarServicoJSON().getDescricao());
	         info.setAtiva(localizada.getPrestarServicoJSON().isAtivo());
	        info.setData(localizada.getPrestarServicoJSON().getData());
	        info.setNodeIdPrestacao(localizada.getPrestarServicoJSON().getNodeId());
		   
		   Itinerario itinerario = new Itinerario();
		   GeoPartida partida = new GeoPartida();
		   GeoDestino destino = new GeoDestino();
		   
			partida.setEnderecoPartida(localizada.getPrestarServicoJSON().getEnderecoPartida());
			partida.setLatitude(localizada.getPrestarServicoJSON().getLatitudePartida());
			partida.setLongitude(localizada.getPrestarServicoJSON().getLongitudePartida());
			
			destino.setEnderecoDestino(localizada.getPrestarServicoJSON().getEnderecoDestino());
			destino.setLatitude(localizada.getPrestarServicoJSON().getLatitudeDestino());
			destino.setLongitude(localizada.getPrestarServicoJSON().getLongitudeDestino());
			
			itinerario.setPartida(partida);
			itinerario.setDestino(destino);
			itinerario.setSoAmigos(localizada.getPrestarServicoJSON().isSoAmigos());
			itinerario.setSoAmigosDosAmigos(localizada.getPrestarServicoJSON().isSoAmigosDosAmigos());
			itinerario.setTodos(localizada.getPrestarServicoJSON().isTodos());
			itinerario.setDistanciaMaxima(localizada.getPrestarServicoJSON().getDistanciaMaxima());
			itinerario.setDistanciaPartidaDestino(localizada.getPrestarServicoJSON().getDistanciaPartidaDestino());
			
			info.setItinerario(itinerario);
			
			Planejamento planejamento = new Planejamento();
			planejamento.setDomingo(localizada.getPrestarServicoJSON().isDomingo());
			planejamento.setSegunda(localizada.getPrestarServicoJSON().isSegunda());
			planejamento.setTerca(localizada.getPrestarServicoJSON().isTerca());
			planejamento.setQuarta(localizada.getPrestarServicoJSON().isQuarta());
			planejamento.setQuinta(localizada.getPrestarServicoJSON().isQuinta());
			planejamento.setSexta(localizada.getPrestarServicoJSON().isSexta());
			planejamento.setSabado(localizada.getPrestarServicoJSON().isSabado());
			planejamento.setHoraFixa(localizada.getPrestarServicoJSON().getHoraFixa());
			planejamento.setHoraEntre(localizada.getPrestarServicoJSON().getHoraEntre());
			planejamento.setHoraE(localizada.getPrestarServicoJSON().getHoraE());
			planejamento.setLugares(localizada.getPrestarServicoJSON().getLugares());
			
			info.setApelidoUsuario(localizada.getUsuario().getApelido());
			info.setDataCadastroUsuario(localizada.getUsuario().getDataCadastro());
			info.setFotoPerfilUsuario(localizada.getUsuario().getFotoPerfil());
			info.setLoginUsuario(localizada.getUsuario().getLogin());
			info.setNodeIdUsuario(localizada.getUsuario().getNodeId());
			info.setNomeUsuario(localizada.getUsuario().getNome());
			info.setSobreNomeUsuario(localizada.getUsuario().getSobreNome());
			info.setSexoUsuario(localizada.getUsuario().getSexo());
			info.setTelefoneUsuario(localizada.getUsuario().getTelefone());
			info.setRegIdGCMUsuario(localizada.getUsuario().getRegIdGCM());
			
			info.setPlanejamento(planejamento);
			
			return info;
	   
   }
   /**
    * Retorna versao do app conforme pacote
    * @param context
    * @return versao do app
    */
   public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			CommonUtils.debug(TAG, "Erro obter version" + e);
			throw new RuntimeException(e);
		}
	}
   
   /**
    * retorna regId do GCM cada não haja alteração no pacote do app
    * @param context
    * @param prefs
    * @return REgId GCM
    */
   public static String getRegistrationId(Context context, final SharedPreferences prefs) {
	   
		String registrationId = prefs.getString(REG_ID, "");
		if (registrationId.isEmpty()) {
			CommonUtils.debug(TAG, "Registro não existe.");
			return "";
		}
		int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			CommonUtils.debug(TAG, "Versão do App alterada");
			return "";
		}
		return registrationId;
	}
   
   /**
    * quarda o regId
    * @param context
    * @param regId
    * @param prefs com Activity exe: MainActivity.class.getSimpleName() e modo exe: Context.MODE_PRIVATE
    */
   public static  void storeRegistrationId(Context context, String regId, final SharedPreferences prefs) {
		int appVersion = getAppVersion(context);
		CommonUtils.debug(TAG, "Salvando regId GCM " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(REG_ID, regId);
		editor.putInt(APP_VERSION, appVersion);
		editor.commit();
	}
}

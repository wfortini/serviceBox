package br.com.mobilenow.util;

import java.nio.charset.Charset;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.text.TextUtils;
import br.com.mobilenow.R;
import br.com.servicebox.android.common.net.Itinerario;
import br.com.servicebox.android.common.net.Planejamento;
import br.com.servicebox.android.common.util.GuiUtils;
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
}

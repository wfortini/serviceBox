package br.com.mobilenow.util;

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
    

}

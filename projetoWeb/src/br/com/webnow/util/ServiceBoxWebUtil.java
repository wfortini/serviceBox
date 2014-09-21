package br.com.webnow.util;


import br.com.webnow.domain.Carona;
import br.com.webnow.domain.Estacionamento;
import br.com.webnow.domain.Reboque;
import br.com.webnow.domain.Servico;
import br.com.webnow.domain.TipoServico;

public class ServiceBoxWebUtil {
	
	
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

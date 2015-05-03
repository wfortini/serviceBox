package br.com.mobilenow.domain;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;
import br.com.servicebox.common.domain.TipoServico;




public abstract class Servico implements Parcelable{
	
	
	private Long nodeId;
	private Boolean servicoDisponivel;
	private Date dataInicialPrestacao;	
	private Integer tipoServico;
	
	public abstract String obterMensagem();
	
	public static Servico getIntance(TipoServico tipo) {

	    switch (tipo) {
	        case CARONA:
	            return new Carona();
	        case REBOQUE:
	            return new Reboque();
	        case ESTACIONAMENTO:
	        	return new Estacionamento();
	        default:
	            return null;
	    }
	}
	
	public static Servico getConcreteClass(Parcel source) {

	    switch (source.readInt()) {
	        case 1:
	            return new Carona(source);
	        case 2:
	            return new Reboque(source);
	        case 3:
	        	return new Estacionamento(source);
	        default:
	            return null;
	    }
	}
	
	public Servico(Parcel source) {
	   nodeId  = source.readLong();
	   servicoDisponivel = source.readByte() == 1;
	   dataInicialPrestacao = new Date(source.readLong());
	}
	
	public Servico() {
		// TODO Auto-generated constructor stub
	}	
	
	/**
	 * Implementação da interface Parcelable
	 */
	@Override
	public int describeContents() {		
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeLong(nodeId);
		dest.writeByte( (byte) (servicoDisponivel ? 1 : 0));
		dest.writeLong(dataInicialPrestacao.getTime());
		
	}
	
	
	 public static final Parcelable.Creator<Servico> CREATOR = new Parcelable.Creator<Servico>() {
	        @Override
	        public Servico createFromParcel(Parcel in) {
	            return Servico.getConcreteClass(in);
	        }

	        @Override
	        public Servico[] newArray(int size) {
	            return new Servico[size];
	        }
	    };
	
	public abstract Integer getTipoServico() ;



	public void setTipoServico(Integer tipoServico) {
		this.tipoServico = tipoServico;
	}



	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Boolean getServicoDisponivel() {
		return servicoDisponivel;
	}

	public void setServicoDisponivel(Boolean servicoDisponivel) {
		this.servicoDisponivel = servicoDisponivel;
	}

	public Date getDataInicialPrestacao() {
		return dataInicialPrestacao;
	}

	public void setDataInicialPrestacao(Date dataInicialPrestacao) {
		this.dataInicialPrestacao = dataInicialPrestacao;
	}	

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Servico person = (Servico) o;
        if (nodeId == null) return super.equals(o);
        return nodeId.equals(person.nodeId);

    }
	
	@Override
    public int hashCode() {
        return nodeId != null ? nodeId.hashCode() : super.hashCode();
    }
	
	@Override
    public String toString() {
        return String.format("%s [%s]", TipoServico.getTipoServico(this.tipoServico).getDescricao(), nodeId);
    }
	

}

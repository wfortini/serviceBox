package br.com.mobilenow.domain;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe que relaciona um usuario a um servico ( Common )
 * @author wpn0510
 *
 */
public class PrestarServico implements Parcelable{
	
	
	private Long nodeId;	
	private Date data;
	private boolean ativa;
	private String descricao;
	

	private Planejamento planejamento;
	
	private Itinerario itinerario;
	
	public PrestarServico() {
		// TODO Auto-generated constructor stub
	}
	
	public PrestarServico(Long nodeId, Date data, boolean ativa,
			String descricao, Planejamento planejamento, Itinerario itinerario) {
		super();
		this.nodeId = nodeId;
		this.data = data;
		this.ativa = ativa;
		this.descricao = descricao;
		this.planejamento = planejamento;
		this.itinerario = itinerario;
	}



	@Override
	public int describeContents() {		
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(planejamento, flags);
		dest.writeParcelable(itinerario, flags);
		dest.writeByte((byte) (ativa ? 1 : 0));
		dest.writeString(descricao);
		dest.writeLong(data.getTime());
		dest.writeLong(nodeId);
		
	}
	
	private PrestarServico(Parcel in) {
		planejamento = in.readParcelable(getClass().getClassLoader());
		itinerario = in.readParcelable(getClass().getClassLoader());        
        ativa = in.readByte() == 1;
        descricao = in.readString();
        data = new Date(in.readLong());
        nodeId = in.readLong();
    }
	
	 public static final Parcelable.Creator<PrestarServico> CREATOR = new Parcelable.Creator<PrestarServico>() {
	        @Override
	        public PrestarServico createFromParcel(Parcel in) {
	            return new PrestarServico(in);
	        }

	        @Override
	        public PrestarServico[] newArray(int size) {
	            return new PrestarServico[size];
	        }
	    };

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Planejamento getPlanejamento() {
		return planejamento;
	}

	public void setPlanejamento(Planejamento planejamento) {
		this.planejamento = planejamento;
	}

	public Itinerario getItinerario() {
		return itinerario;
	}

	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}
	
	
	
	

}

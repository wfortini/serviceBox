package br.com.mobilenow.domain;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Notificacao implements Parcelable{
	
	
	private Integer _id;
	private String mensagem;
	private Integer idSolicitante;
	private Integer idSolicitado;
	private Integer idPrestacao;
	private Integer tipoSolicitacao;
	private String fotoPerfil;
	private Date dataSolicitacao;
	private Integer statusNotificacao;
	private Integer idSolicitacao;
	
	/**
	 * Implementação da interface Parcelable
	 */
	@Override
	public int describeContents() {		
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(_id);
		dest.writeString(mensagem);
		dest.writeInt(idSolicitante);
		dest.writeInt(idSolicitado);
		dest.writeInt(idPrestacao);
		dest.writeInt(tipoSolicitacao);
		dest.writeString(fotoPerfil);
		dest.writeLong(dataSolicitacao.getTime());
		dest.writeInt(statusNotificacao);
		dest.writeInt(idSolicitacao);
	}
	
	private Notificacao(Parcel in) {
		_id = in.readInt();
		mensagem = in.readString();
		idSolicitante = in.readInt();
		idSolicitado = in.readInt();
		idPrestacao = in.readInt();
		tipoSolicitacao = in.readInt();
		fotoPerfil = in.readString();
		dataSolicitacao = new Date(in.readLong());
		statusNotificacao = in.readInt();
		idSolicitacao = in.readInt();
    }
	
	 public static final Parcelable.Creator<Notificacao> CREATOR = new Parcelable.Creator<Notificacao>() {
	        @Override
	        public Notificacao createFromParcel(Parcel in) {
	            return new Notificacao(in);
	        }

	        @Override
	        public Notificacao[] newArray(int size) {
	            return new Notificacao[size];
	        }
	    };
	
	
	public Notificacao() {
		// TODO Auto-generated constructor stub
	}


	public Integer get_id() {
		return _id;
	}


	public void set_id(Integer _id) {
		this._id = _id;
	}


	public String getMensagem() {
		return mensagem;
	}


	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}


	public Integer getIdSolicitante() {
		return idSolicitante;
	}


	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}


	public Integer getIdSolicitado() {
		return idSolicitado;
	}


	public void setIdSolicitado(Integer idSolicitado) {
		this.idSolicitado = idSolicitado;
	}


	public Integer getIdPrestacao() {
		return idPrestacao;
	}


	public void setIdPrestacao(Integer idPrestacao) {
		this.idPrestacao = idPrestacao;
	}


	public Integer getTipoSolicitacao() {
		return tipoSolicitacao;
	}


	public void setTipoSolicitacao(Integer tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}


	public String getFotoPerfil() {
		return fotoPerfil;
	}


	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}


	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}


	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}


	public Integer getIdSolicitacao() {
		return idSolicitacao;
	}


	public void setIdSolicitacao(Integer idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}


	public Integer getStatusNotificacao() {
		return statusNotificacao;
	}


	public void setStatusNotificacao(Integer statusNotificacao) {
		this.statusNotificacao = statusNotificacao;
	}
	
	
	
	

}

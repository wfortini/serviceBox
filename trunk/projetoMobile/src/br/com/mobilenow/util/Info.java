package br.com.mobilenow.util;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;
import br.com.mobilenow.domain.Itinerario;
import br.com.mobilenow.domain.Planejamento;
import br.com.mobilenow.domain.PrestarServico;
import br.com.mobilenow.domain.Usuario;

public class Info implements Parcelable{
	
	private Long nodeId;	
	private Date data;
	private boolean ativa;
	private String descricao;
	
	private Itinerario itinerario;
	private Planejamento planejamento;
	
   private Long nodeIdUsuario;	
   private String loginUsuario;	
   private String nomeUsuario;
   private String sobreNomeUsuario;
   private String sexoUsuario;
   private String apelidoUsuario;
   private String fotoPerfilUsuario;
   private Date dataCadastroUsuario;
   private String telefoneUsuario;

	public Info() {
		// TODO Auto-generated constructor stub
	}
	
	public Info(PrestarServico prestar, Usuario usuario){
		this.planejamento = prestar.getPlanejamento();
		this.itinerario = prestar.getItinerario();
		
		this.nodeId = prestar.getNodeId();
		this.data = prestar.getData();
		this.descricao = prestar.getDescricao();
		this.ativa = prestar.isAtiva();
		
		this.nodeIdUsuario = usuario.getNodeId();
		this.loginUsuario = usuario.getLogin();
		this.nomeUsuario = usuario.getNome();
		this.sobreNomeUsuario = usuario.getSobreNome();
		this.sexoUsuario = usuario.getSexo();
		this.apelidoUsuario = usuario.getApelido();
		this.fotoPerfilUsuario = usuario.getFotoPerfil();
		this.dataCadastroUsuario = usuario.getDataCadastro();
		this.telefoneUsuario = usuario.getTelefone();
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
		
		dest.writeParcelable(itinerario, flags);
		dest.writeParcelable(planejamento, flags);
		dest.writeLong(getNodeIdUsuario());
		dest.writeString(getLoginUsuario());
		dest.writeString(getNomeUsuario());
		dest.writeString(getSobreNomeUsuario());
		dest.writeString(getSexoUsuario());
		dest.writeString(getApelidoUsuario());
		dest.writeString(getFotoPerfilUsuario());
		dest.writeLong(getDataCadastroUsuario().getTime());
		dest.writeString(getTelefoneUsuario());
		dest.writeLong(nodeId);
		dest.writeByte((byte) (ativa ? 1 : 0));
		dest.writeLong(data.getTime());
		dest.writeString(descricao);
		
	}
	
	private Info(Parcel in) {
		
        itinerario = in.readParcelable(getClass().getClassLoader());  
        planejamento = in.readParcelable(getClass().getClassLoader());
        nodeIdUsuario = in.readLong();
        loginUsuario = in.readString();
        nomeUsuario = in.readString();
        sobreNomeUsuario = in.readString();
        sexoUsuario = in.readString();
        apelidoUsuario = in.readString();
        fotoPerfilUsuario = in.readString();
        dataCadastroUsuario = new Date(in.readLong());
        telefoneUsuario = in.readString();        
        nodeId = in.readLong();
        ativa = in.readByte() == 1;
        data = new Date(in.readLong());
        descricao = in.readString();
        
    }
	
	 public static final Parcelable.Creator<Info> CREATOR = new Parcelable.Creator<Info>() {
	        @Override
	        public Info createFromParcel(Parcel in) {
	            return new Info(in);
	        }

	        @Override
	        public Info[] newArray(int size) {
	            return new Info[size];
	        }
	    };

	

	public Itinerario getItinerario() {
		return itinerario;
	}


	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}


	public Planejamento getPlanejamento() {
		return planejamento;
	}


	public void setPlanejamento(Planejamento planejamento) {
		this.planejamento = planejamento;
	}


	public Long getNodeIdUsuario() {
		return nodeIdUsuario;
	}


	public void setNodeIdUsuario(Long nodeIdUsuario) {
		this.nodeIdUsuario = nodeIdUsuario;
	}


	public String getLoginUsuario() {
		return loginUsuario;
	}


	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}


	public String getNomeUsuario() {
		return nomeUsuario;
	}


	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}


	public String getSobreNomeUsuario() {
		return sobreNomeUsuario;
	}


	public void setSobreNomeUsuario(String sobreNomeUsuario) {
		this.sobreNomeUsuario = sobreNomeUsuario;
	}


	public String getSexoUsuario() {
		return sexoUsuario;
	}


	public void setSexoUsuario(String sexoUsuario) {
		this.sexoUsuario = sexoUsuario;
	}


	public String getApelidoUsuario() {
		return apelidoUsuario;
	}


	public void setApelidoUsuario(String apelidoUsuario) {
		this.apelidoUsuario = apelidoUsuario;
	}


	public String getFotoPerfilUsuario() {
		return fotoPerfilUsuario;
	}


	public void setFotoPerfilUsuario(String fotoPerfilUsuario) {
		this.fotoPerfilUsuario = fotoPerfilUsuario;
	}


	public Date getDataCadastroUsuario() {
		return dataCadastroUsuario;
	}


	public void setDataCadastroUsuario(Date dataCadastroUsuario) {
		this.dataCadastroUsuario = dataCadastroUsuario;
	}


	public String getTelefoneUsuario() {
		return telefoneUsuario;
	}


	public void setTelefoneUsuario(String telefoneUsuario) {
		this.telefoneUsuario = telefoneUsuario;
	}

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
	
	

	    
	    
}

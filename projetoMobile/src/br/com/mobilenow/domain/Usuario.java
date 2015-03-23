package br.com.mobilenow.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Classe Usuario Modulo Commons
 * @author wpn0510
 *
 */
public class Usuario implements Parcelable{	
	
	private Long nodeId;
	
	private String login;
	
	private String password;	
	
	private String nome;
	private String sobreNome;
	private String sexo;
	private String apelido;
	private String fotoPerfil;
	private Date dataCadastro;
	private String telefone;
	private String regIdGCM;
	
	private Set<Usuario> amigos;	
	private Set<Servico> servicosDisponiveis;	
	
	
	public Usuario() {
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
		dest.writeString(login);
		dest.writeString(password);
		dest.writeString(nome);
		dest.writeString(sobreNome);
		dest.writeString(sexo);
		dest.writeString(apelido);
		dest.writeString(fotoPerfil);
		dest.writeLong(dataCadastro.getTime());
		dest.writeString(telefone);
		dest.writeString(regIdGCM);
		//dest.writeTypedList(new ArrayList<Usuario>(getAmigos()));
		//dest.writeTypedList(new ArrayList<Servico>(servicosDisponiveis));
		
	}
	
	public void readFromParcel(Parcel in) {
		
		nodeId = in.readLong();
		login = in.readString();
		password = in.readString();
		nome = in.readString();
		sobreNome = in.readString();
		sexo = in.readString();
		apelido = in.readString();
		dataCadastro = new Date(in.readLong());
		telefone = in.readString();
		regIdGCM = in.readString();
		
		/**
		if(amigos == null)
			amigos = new HashSet<Usuario>();
		
        in.readTypedList(new ArrayList<Usuario>(amigos), Usuario.CREATOR);        
        
        if(servicosDisponiveis == null)
        	servicosDisponiveis = new HashSet<Servico>();
        
        in.readTypedList(new ArrayList<Servico>(servicosDisponiveis), Servico.CREATOR);
		**/
		
	}
	
	private Usuario(Parcel in) {
		readFromParcel(in);
		
    }
	
	 public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
	        @Override
	        public Usuario createFromParcel(Parcel in) {
	            return new Usuario(in);
	        }

	        @Override
	        public Usuario[] newArray(int size) {
	            return new Usuario[size];
	        }
	    };
	
	
	
	public boolean addServico(Servico servico){
		if(this.servicosDisponiveis != null){
			return this.servicosDisponiveis.add(servico);
		}else{
			this.servicosDisponiveis = new HashSet<Servico>();
			return this.servicosDisponiveis.add(servico);	
		}
    	
    }
	
	public boolean addAllServico(Collection<? extends Servico> servicos){
    	
    	if(this.servicosDisponiveis != null){
    		return this.servicosDisponiveis.addAll(servicos);
		}else{
			this.servicosDisponiveis = new HashSet<Servico>();
			return this.servicosDisponiveis.addAll(servicos);
		}
    }
    
    public boolean isPrestaServico(Servico servico){
    	return servico != null && getServicosDisponiveis().contains(servico);
    }
    

	public Set<Servico> getServicosDisponiveis() {
		if(servicosDisponiveis != null)
		  return servicosDisponiveis;
		else
			return new HashSet<Servico>();
	}
	
	
	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

    
	public boolean addAmigo(Usuario amigo){
		if(this.amigos != null){
			return this.amigos.add(amigo);
		}else{
			this.amigos = new HashSet<Usuario>();
			return this.amigos.add(amigo);
		}
	}
	
	public boolean addAllAmigo(List<Usuario> amigo){
		if(this.amigos != null){
			return this.amigos.addAll(amigo);
		}else{
			this.amigos = new HashSet<Usuario>();
			return this.amigos.addAll(amigo);
		}
	}

	public Set<Usuario> getAmigos() {
		if(amigos != null)
		 return this.amigos;
		else
			return new HashSet<Usuario>();
	}
     
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	

	public String getRegIdGCM() {
		return regIdGCM;
	}

	public void setRegIdGCM(String regIdGCM) {
		this.regIdGCM = regIdGCM;
	}

	public Usuario(String login, String password, String nome,
			String sobreNome, String sexo, String apelido) {
		super();
		this.login = login;
		this.password = password;
		this.nome = nome;
		this.sobreNome = sobreNome;
		this.sexo = sexo;
		this.apelido = apelido;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario user = (Usuario) o;
        if (nodeId == null) return super.equals(o);
        return nodeId.equals(user.nodeId);

    }
	
	@Override
    public int hashCode() {

        return nodeId != null ? nodeId.hashCode() : super.hashCode();
    }
	
	@Override
	public String toString() {
		
		return "Node Id: " + this.getNodeId() + ", Login: " + this.getLogin() + ", Nome: " + this.getNome();
	}

}

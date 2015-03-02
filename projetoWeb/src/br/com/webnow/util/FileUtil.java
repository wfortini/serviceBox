package br.com.webnow.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import br.com.webnow.domain.Usuario;

public class FileUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	private static final String PATH = "c:/fotos";
	private static final File raiz = new File("c:/fotos");
	
	public static String renomear(File arquivoAntigo , File arquivoNovo){
		
		try {
			if(!arquivoAntigo.renameTo(arquivoNovo)	){
				int i = 1;
				do{
					String nome = arquivoNovo.getName();
					int index = nome.indexOf(".");
					nome = nome.substring(0, index);
					nome += "_00" + i + ".jpg"; 
					arquivoNovo = new File(raiz, nome);
					i++;
					
				}while(!arquivoAntigo.renameTo(arquivoNovo));
			}			
			return arquivoNovo.getName();
		} catch (Exception e) {
			logger.error("Erro renomear aquivo: ", e);
			return null;
		}
		
	}
	
	public static String renomear(File arquivoAntigo, String nomeArquivoNovo){
		
		try {
			File arquivoNovo = new File(raiz, nomeArquivoNovo);
			return renomear(arquivoAntigo, arquivoNovo);
		} catch (Exception e) {
			logger.error("Erro renomear aquivo: ", e);
			return null;
		}
		
		
	}
	/**
	 * Salva imagem em path local 
	 * @param usuario - para obetr o login que será o path do usuario e seu nome que será o nome do arquivo
	 * @param image Foto
	 * @return
	 */
	public static Usuario salvarArquivoLocal(Usuario usuario, MultipartFile image){
		
		String loginPath = usuario.getLogin();
		String nomeUsuario = usuario.getNome();		
		
		File pathUsuario = new File(raiz, File.separator + loginPath);
		pathUsuario.mkdir();
		
		try {
			File nomeFoto = new File(pathUsuario, nomeUsuario.concat("_perfil.jpg"));
			FileUtils.writeByteArrayToFile(nomeFoto, image.getBytes());
			usuario.setFotoPerfil(nomeUsuario.concat("_perfil.jpg"));
			return usuario;
			
		} catch (Exception e) {
			logger.error("Erro renomear aquivo: ", e.getMessage());
			return null;
		}		
		
	}

}

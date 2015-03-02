package br.com.webnow.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class ImagemController {		
 
 
	/**
	 * Metodo para retorna fotos de perfil
	 * http://localhost:8080/projetoWeb/imagemPerfil?fileName=teste.jpg&login=teste
	 * @param fileName
	 * @param login
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/imagemPerfil", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] imagemPerfil(@RequestParam(
			   value="fileName", required=true) String  fileName, @RequestParam(
					   value="login", required=true) String login) throws IOException {		
		
		BufferedInputStream in = null;
		try {
			File f = new File("c:\\fotos\\"+ login +"\\" + fileName);
			 in = new BufferedInputStream(new FileInputStream(f));
			return IOUtils.toByteArray(in);
			
		}catch(FileNotFoundException fn){
			return new byte[1];
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
	

}

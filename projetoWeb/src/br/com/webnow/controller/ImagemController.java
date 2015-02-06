package br.com.webnow.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class ImagemController {
	
	/**
	 *http://localhost:8080/projetoweb/getimage.html?fileName=14.jpg	 
	 */
	
	@RequestMapping(value = "/getimage", method = RequestMethod.GET)
	public void getImage(@RequestParam(value="fileName", required=true) String  fileName,HttpServletResponse response,HttpServletRequest request) throws IOException {
	    File f = new File("C:\\tomcat\\downloads\\"+ fileName);
	    BufferedImage bi = ImageIO.read(f);
	    OutputStream out = response.getOutputStream();
	    ImageIO.write(bi, "jpg", out);
	    out.close();
	}

}

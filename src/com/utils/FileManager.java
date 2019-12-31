package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;


/**
 *This class is used to get files from a root directory
 *This root directory contains all the input files from users and other stuff like
 *repository files
 *Usually the root is present in another disk different from where the server and source code are.
 *
 *this class can be called on templates to provide files
 * @author RuiMenoita
 */


@ManagedBean
@ApplicationScoped
public class FileManager {


	private static final String ROOT_PATH= "C:/Users/Rui Menoita/Desktop/Rui Menoita/WorkSpaces"		//FILES HARD PATH
			+ "/Git-repository/Site_Iscte_ACM_Student_chapter"
			+ "/WebContent/resources/files";	

	private static final String imageRegexValidator = "([^\\s]+(\\.(?i)(jpg|png|gif|jpeg))$)";
	private final static String typeRegex = ".*(jpg|png|gif|jpeg)$" ;






	public static List<String> saveEventImages(List<Part> files) throws IOException {
		List<String> paths = new ArrayList<>();

		for (Part part : files) {
			if(!part.getContentType().matches(typeRegex)) {
				InputStream input = part.getInputStream();
				Files.copy(input, Paths.get(ROOT_PATH +"/events/"));
				input.close();
				paths.add("/events/"+ part.getName());
			}
		}
		
		return paths;
	}





	/**
	 * @return validates if the path given pertences to an image
	 */
	public static boolean validImage(String path) {
		return path.matches(imageRegexValidator);
	}
}

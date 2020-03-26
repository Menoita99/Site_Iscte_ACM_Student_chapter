package com.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

//	private static final String imageRegexValidator = "([^\\s]+(\\.(?i)(jpg|png|gif|jpeg))$)";
	private final static String typeRegex = ".*(jpg|png|gif|jpeg)$" ;





	/**
	 * @return validates if the path given pertences to an image
	 */
	public static boolean validImage(String path) {
		return path.matches(typeRegex);
	}




	/**
	 * Saves Part objects into projects folder and returns the paths
	 * @return
	 */
	public static List<String> saveFiles(List<Part> parts, String path) {
		List<String> paths = new ArrayList<>();

		//		File uploads = new File(properties.getProperty("upload.location"));
		File uploads = new File(ROOT_PATH+File.separator+path);

		for (Part part : parts) {

			if(part.getContentType().matches(typeRegex)) {

				try(InputStream input = part.getInputStream()) {
					
					String fileName =part.getSubmittedFileName();
					String name = fileName.substring(0,fileName.lastIndexOf('.'));
					String extension = fileName.substring(fileName.lastIndexOf('.'), fileName.length());

					File file = File.createTempFile(name+"-", extension , uploads);

					Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

					paths.add(path+"/"+file.getName());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return paths;
	}
	
	/**
	 * Saves an file and returns the path to the file saved
	 */
	public static String saveFile(Part part, String path) {
		try(InputStream input = part.getInputStream()) {
			
			//		File uploads = new File(properties.getProperty("upload.location"));
			File uploads = new File(ROOT_PATH+File.separator+path);
			String fileName =part.getSubmittedFileName();
			String name = fileName.substring(0,fileName.lastIndexOf('.'));
			String extension = fileName.substring(fileName.lastIndexOf('.'), fileName.length());

			File file = File.createTempFile(name+"-", extension , uploads);

			Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

			return path+"/"+file.getName();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 
	 * @param imagePath
	 */
	public static boolean deleteFile(String path) {
		return new File(ROOT_PATH+File.separator+path).delete();
	}
	
	
}

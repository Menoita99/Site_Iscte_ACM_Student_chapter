package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

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

	private static final String ROOT_PATH= "D:/Files/";
	
	
	/**
	 * returns the a FileInputStream object of the image that we pretend to get
	 */
	public InputStream getImage(String filepath) {
			try {
				File file = new File(ROOT_PATH+filepath);
				if(file.getName().matches("([^\\s]+(\\.(?i)(jpg|png|gif|jpeg))$)"))
					return new FileInputStream(file);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	/**
	 * returns the a string array with all the file names inside of the given dir
	 */
	public String[] getFileList(String dir) {
		return new File(ROOT_PATH+dir).list();
	}
	
	/**
	 * returns the a file array with all the files inside of the given dir
	 */
	public File[] getFiles(String dir) {
		return new File(ROOT_PATH+dir).listFiles();
	}

	public static boolean isImage(String path) {
		return path.matches("([^\\s]+(\\.(?i)(jpg|png|gif|jpeg))$)");
	}
}

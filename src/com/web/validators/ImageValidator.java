package com.web.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class ImageValidator implements Validator {

	private final static int maxSize = 25 *1024 *1024 ; //25 MB
	
	private final static String types = ".png,.jpg,.jpeg,gif,.tiff" ; 
	
	
	/**
	 * Validates the file using static metrics
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		
	}

}

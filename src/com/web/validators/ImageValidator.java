package com.web.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;


@FacesValidator(value = "ImageValidator")
public class ImageValidator implements Validator {

	private final static int maxSize = 2 *1024 *1024;

	private final static String typeRegex = ".*(jpg|png|gif|jpeg)$" ; 
	//private final static String types = "jpg png gif jpeg" ; 



	/**
	 * Validates the given file using static metrics.
	 * This method will check max size (2 MB) and file type (images jpg , png , giff , jpeg)
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Part file = (Part) value;

		FacesMessage message = null;

		try {
			
			if (!file.getContentType().matches(typeRegex))
				message = new FacesMessage("File selected is not an image");
			
			else if (file.getSize()>maxSize)
				message=new FacesMessage("File size too big. File size allowed  is less than or equal to "+ maxSize/(1024*1024) +"MB.");

			if (message!=null && !message.getDetail().isEmpty()) {
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}

		} catch (Exception ex) {
			System.out.println("Sending error message from image validator: "+ ex.getMessage());
			throw new ValidatorException(new FacesMessage(ex.getMessage()));
		}

	}



	/**
	 * @return the maxsize
	 */
	public static int getMaxsize() {
		return maxSize;
	}
}

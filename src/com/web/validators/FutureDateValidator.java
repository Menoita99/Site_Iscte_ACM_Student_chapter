package com.web.validators;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "futureDateValidator")
public class FutureDateValidator implements Validator{

	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Date dateToTest = (Date) value;
		Date now = new Date(System.currentTimeMillis());
		
		if(dateToTest.before(now))
			throw new ValidatorException(new FacesMessage("Date must be into future"));
	}

	
	
	
	/*
	String date = (String)value;
	
	FacesMessage message = null;
	
	try {
		if(date != null && !date.isBlank()) {
			
			if(date.length() == 16) {
				
				try {
				
					LocalDateTime localDate = LocalDateTime.parse(date);
					LocalDateTime now = LocalDateTime.now();
					
					if(now.isAfter(localDate))
						message = new FacesMessage("Date is on past");
				
				}catch (Exception e) {
					message = new FacesMessage("Invalid date format");
					e.printStackTrace();
				}
				
			}else
				message = new FacesMessage("Invalid date format");
			
		}else
			message = new FacesMessage("Date may not be empty");
		
		
		if (message!=null && !message.getDetail().isEmpty()) {
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message );
		}
		
	} catch (Exception e) {
		System.out.println("Sending error message from image validator: "+ e.getMessage());
		throw new ValidatorException(new FacesMessage(e.getMessage()));
	}
	*/
}

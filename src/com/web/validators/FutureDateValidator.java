package com.web.validators;

import java.time.LocalDate;
import java.time.ZoneId;
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
		LocalDate dateToTest = ((Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate maxDate = LocalDate.of(9999, 12, 31);
		LocalDate now = LocalDate.now();
		
		if(dateToTest.isAfter(maxDate))
			throw new ValidatorException(new FacesMessage("Invalid date"));
		
		if(dateToTest.isBefore(now))
			throw new ValidatorException(new FacesMessage("Date must be into future"));
	}
}

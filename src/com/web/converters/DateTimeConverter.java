package com.web.converters;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.utils.ArrayUtil;

@FacesConverter(value="dateTimeConverter")
public class DateTimeConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) { //value: aaaa-MM-ddThh:mm
		try {
			String[] split = value.split("T");
			String[] date = split[0].split("-");
			String[] time = split[1].split(":");
			Object[] data = ArrayUtil.fusion(date,time);

			int year = Integer.parseInt((String) data[0]);
			int month = Integer.parseInt((String) data[1]);
			int day = Integer.parseInt((String) data[2]);
			int hour = Integer.parseInt((String) data[3]);
			int min = Integer.parseInt((String) data[4]);

			return new GregorianCalendar(year, month, day, hour, min).getTime();

		}catch (Exception e) {
			FacesMessage msg = new FacesMessage("Invalid date");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
			throw new ConverterException(msg);
		}
	}



	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		System.out.println(LocalDateTime.ofInstant(((Date)value).toInstant(), ZoneId.systemDefault()).toString());
		return LocalDateTime.ofInstant(((Date)value).toInstant(), ZoneId.systemDefault()).toString();
	}
}

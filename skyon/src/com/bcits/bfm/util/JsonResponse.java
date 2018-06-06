package com.bcits.bfm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class JsonResponse {

	@Autowired
	private MessageSource messageSource;
	
	private String status = null;
	private Object result = null;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object throwException(final Locale locale)
	{
		JsonResponse errorResponse = new JsonResponse();

		List<String> errors = new ArrayList<String>();
		
		errors.add(messageSource.getMessage(
						"exception.project.exception", null, locale));

		errorResponse.setStatus("EXCEPTION");
		errorResponse.setResult(errors);
		return errorResponse;
	}

	
	
}

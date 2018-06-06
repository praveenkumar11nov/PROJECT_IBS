package com.bcits.bfm.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.configuration.annotation.AnnotationsConfigurer;
import net.sf.oval.configuration.annotation.JPAAnnotationsConfigurer;

@Component
public class ValidationUtil
{
	@Resource
	private MessageSource messageSource;
	
	@Resource
	private Validator validator;
	
	net.sf.oval.Validator validatorOval = new net.sf.oval.Validator(new AnnotationsConfigurer(), new JPAAnnotationsConfigurer());
	
	public JsonResponse validate(BindingResult bindingResult)
	{
		JsonResponse errorResponse = new JsonResponse();

		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			BfmLogger.logger.info("Binding result: "+bindingResult);
			return errorResponse;
		}
		else
		{
			errorResponse.setStatus("SUCCESS");
			return errorResponse;
		}
	}
	
	public JsonResponse validateModelObject(Object object, BindingResult bindingResult)
	{
		JsonResponse errorResponse = new JsonResponse();

		validator.validate(object, bindingResult);
		
		if (bindingResult.hasErrors()) {
			errorResponse.setStatus("FAIL");
			errorResponse.setResult(bindingResult.getAllErrors());
			BfmLogger.logger.info("Binding result: "+bindingResult);

			return errorResponse;
		}
		else
		{
			errorResponse.setStatus("SUCCESS");
			return errorResponse;
		}
			
	}
	
	public Object customValidateModelObject(Object object, String profile, final Locale locale)
	{
		JsonResponse errorResponse = null;
		
		List<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();

		if(profile != null)
			violations = validatorOval.validate(object, profile);
		else
			violations = validatorOval.validate(object);
		
		if(violations.size()>0)
		{
			errorResponse = new JsonResponse();
			
			List<String> errors = new ArrayList<String>();
			for (Iterator<ConstraintViolation> iterator = violations.iterator(); iterator
				.hasNext();)
			{
				ConstraintViolation constraintViolation = (ConstraintViolation) iterator.next();
				errors.add(messageSource.getMessage(constraintViolation.getMessage(), null, locale));
			}
			errorResponse.setStatus("EXCEPTION");
			errorResponse.setResult(errors);
		}
		
		return errorResponse;
	}

}

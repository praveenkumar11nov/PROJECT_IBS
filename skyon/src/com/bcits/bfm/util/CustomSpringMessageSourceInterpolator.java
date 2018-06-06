package com.bcits.bfm.util;

import java.util.Locale;

import javax.annotation.Resource;
import javax.validation.MessageInterpolator;

import org.hibernate.validator.engine.ResourceBundleMessageInterpolator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;

public class CustomSpringMessageSourceInterpolator extends ResourceBundleMessageInterpolator implements MessageInterpolator, MessageSourceAware, InitializingBean 
{
	@Resource
	private MessageSource messageSource;

	@Override
	public String interpolate(String messageTemplate, Context context) {
		try {
			String messageTemplateSpring = removeFirstAndLast(messageTemplate);
			return messageSource.getMessage(messageTemplateSpring, new Object[] {}, Locale.getDefault());
		} catch (NoSuchMessageException e) {
			return super.interpolate(messageTemplate, context);
		}
	}

	@Override
	public String interpolate(String messageTemplate, Context context, Locale locale) {
		try {
			String messageTemplateSpring = removeFirstAndLast(messageTemplate);
			return messageSource.getMessage(messageTemplateSpring, new Object[] {}, locale);
		} catch (NoSuchMessageException e) {
			return super.interpolate(messageTemplate, context, locale);
		}
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void afterPropertiesSet() throws Exception {
		if (messageSource == null) {
			throw new IllegalStateException("MessageSource was not injected, could not initialize " + this.getClass().getSimpleName());
		}
	}

	private static String removeFirstAndLast(String string) {
		return string.substring(1, string.length() - 1);
	}

}

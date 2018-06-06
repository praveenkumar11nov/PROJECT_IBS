package com.bcits.bfm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.Faq;
import com.bcits.bfm.model.PhotoEvent;
import com.bcits.bfm.model.PhotoGallery;
import com.bcits.bfm.service.PhotoEventService;

@Controller
public class PhotoGalleryController 
{
	static Logger lgger= LoggerFactory.getLogger(PhotoGallery.class);

	/*@Autowired
	private PhotoEventService photoEventService;
	
	@SuppressWarnings("serial")	
	@RequestMapping(value = "/photo/read", method = RequestMethod.POST)
	public @ResponseBody
	List<?> readFAQ() {
		List<Map<String, Object>> faqlist = new ArrayList<Map<String, Object>>();
		for (final PhotoEvent record : photoEventService.findAllPhotoevent()) {
			faqlist.add(new HashMap<String, Object>() {
				{
					put("peId", record.getPeId());
					put("eventName", record.getEventName());
					put("eventDesc", record.getEventDesc());
				}
			});
		}
		return faqlist;
	}*/
	
}

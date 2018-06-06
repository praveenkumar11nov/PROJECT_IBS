package com.bcits.bfm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CalculateController 
{
	@RequestMapping(value="/calculate",method={RequestMethod.POST,RequestMethod.GET})
//	@Scheduled(cron = "${scheduling.job.calculateDate}")
	public @ResponseBody void generateBill() throws ParseException{
	    System.out.println("*****************Controller for calculating*******************");
		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2017");
		Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("01/02/2017");
		
	    if(date1.compareTo(date2)<0){ //   date1 - date2 = -1
	       System.out.println(sm.format(date1)+" comes before "+sm.format(date2));
		}
	    else{
	       System.out.println(sm.format(date2)+" comes before "+sm.format(date1));
	    }
	}
}

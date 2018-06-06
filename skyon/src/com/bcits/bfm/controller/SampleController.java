package com.bcits.bfm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController 
{
	@RequestMapping("/link1")
	public String link1()
	{
		return "link1";
	}
	
	@RequestMapping("/link2")
	public String link2()
	{
		return "link2";
	}
}

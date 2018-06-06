package com.bcits.bfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bcits.bfm.amr.service.Tower_A_EB_kwhService;
import com.bcits.bfm.model.Tower_A_EB_kwh;

@Controller
public class TestController {
	
	@Autowired
	private Tower_A_EB_kwhService tower_A_EB_kwhService;
	
	@RequestMapping(value="/test")
	public void method1(){
		List<Tower_A_EB_kwh> list=tower_A_EB_kwhService.read();
		System.out.println(" ================ "+list.size());
}

}

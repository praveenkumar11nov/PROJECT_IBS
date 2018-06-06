/*package com.bcits.bfm.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import navigation.Example;
import navigation.Widget;
import net.sf.ehcache.CacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bcits.bfm.ldap.ItemDetails;
import com.bcits.bfm.ldap.LdapBusinessModel;
import com.bcits.bfm.ldap.ModuleDetails;
import com.bcits.bfm.ldap.SecurityVerification;
import com.bcits.bfm.serviceImpl.facilityManagement.ParkingSlotsAllotmentServiceImpl;
import com.bcits.bfm.util.MyBootstrapCacheLoaderFactory;
import com.bcits.bfm.util.SessionData;
@Service
@Aspect
public class SpringAOP {

	@Autowired
	private SecurityVerification securityVerification;
	@Autowired
	private LdapBusinessModel ldapBusinessModel;	
	@Autowired
	private MyBootstrapCacheLoaderFactory myBootstrapCacheLoaderFactory;
	
	private static final Log logger = LogFactory.getLog(ParkingSlotsAllotmentServiceImpl.class);
	
	@Resource
	private CacheManager ehCacheManager;

	@Around("execution(* com.bcits.bfm.controller.LoginController.setAllMenu())")
	public Object recoverStateView(ProceedingJoinPoint joinPoint) throws Throwable{
		logger.info("Before Executing Task Authentication");
		
		String userId = (String) SessionData.getUserDetails().get("userID");		
		
		ServletRequestAttributes reqAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = reqAttributes.getRequest();		
		HttpSession session = request.getSession(true);      
        ServletContext servletcontext = session.getServletContext();        
		
    	List<JSONObject> memberWithDesciption=myBootstrapCacheLoaderFactory.getMemberWithDesciption();	
    	servletcontext.setAttribute("memberWithDesciption", memberWithDesciption);       		
        
		List<ModuleDetails> memberWithDesciptionForMenu=myBootstrapCacheLoaderFactory.getMemberWithDesciptionMenu();	
	
		List<String> roles = ldapBusinessModel.getLoginRoles(userId);
		
		servletcontext.setAttribute("role", roles);
		
		ObjectMapper mapper = new ObjectMapper(new JsonFactory());  		
		
		List<JSONObject> modules=new ArrayList<JSONObject>();
		List<String> mod=new ArrayList<String>();
		
		for(ModuleDetails mdetails:memberWithDesciptionForMenu){
			for(String rol:roles){
				if(mdetails.getRole().contains(rol)){
					JSONObject module=new JSONObject();
					module.put("text", mdetails.getDescription());					
					module.put("role", mdetails.getRole());
					List<JSONObject> items=new ArrayList<JSONObject>();
					List<String> im=new ArrayList<String>();					
					for(ItemDetails idetails:mdetails.getItems()){   
						
						if(idetails.getRole().contains(rol)){
							JSONObject item=new JSONObject();
							item.put("text", idetails.getText());						
							item.put("url", idetails.getUrl());
							item.put("role", idetails.getRole());						
							if(!(im.contains(idetails.getText()))){
								
								items.add(item);
							}
							im.add(idetails.getText());
						}
						
					}
					module.put("items", items);					
					if(!(mod.contains(mdetails.getDescription()))){
						modules.add(module);
					}
					mod.add(mdetails.getDescription());
				}
			}		
			
		}		 
     
		JSONObject jo = new JSONObject();	
		jo.put("Navigation", modules);   
        
        
        HashMap<String, Widget[]> navigation = mapper.readValue(jo.toString(), new TypeReference<HashMap<String,Widget[]>>() {});    
        
        for (Map.Entry<String, Widget[]> entry : navigation.entrySet()) 
        {
            for (Widget widget : entry.getValue()) 
            {
            	List<Example> examples = new ArrayList<Example>();   
	                for (Example example : widget.getItems()) 
	                {	
            				examples.add(example);
	                }
            	 
            	widget.setItems(examples.toArray(new Example[examples.size()]));
            }
        }          
        
        servletcontext.setAttribute("navigation", navigation);
		
		
		return joinPoint.proceed();
				
	}	
	
}
*/
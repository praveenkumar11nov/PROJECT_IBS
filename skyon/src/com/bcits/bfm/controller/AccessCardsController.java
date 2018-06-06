package com.bcits.bfm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bfm.model.AccessCards;
import com.bcits.bfm.model.PersonAccessCards;
import com.bcits.bfm.model.Personnel;
import com.bcits.bfm.service.customerOccupancyManagement.AccessCardSevice;
import com.bcits.bfm.service.customerOccupancyManagement.PersonAccessCardService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.serviceImpl.facilityManagement.ParkingSlotsAllotmentServiceImpl;
import com.bcits.bfm.util.JsonResponse;
import com.bcits.bfm.view.BreadCrumbTreeService;

/**
 * Controller which includes all the business logic concerned to this module's Usecase
 * Module: Customer Occupancy 
 * Use Case : Access Cards,Access Card Permission
 * 
 * @author Mohammed Farooq
 * @version %I%, %G%
 * @since 0.1
 */
/**
 * @author user47
 *
 */
@Controller
public class AccessCardsController 
{
	@Autowired
	private PersonService personService;
	@Autowired
	private AccessCardSevice accessCardsService;
	
	@Autowired
	private BreadCrumbTreeService breadCrumbService;
	
	@Autowired
	private JsonResponse jsonErrorResponse;
	
	@Autowired
	PersonAccessCardService personAccessCardService;
	
	@PersistenceContext(unitName="MSSQLDataSourceAccessCards")
	private EntityManager msSQLentityManager;
	
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ParkingSlotsAllotmentServiceImpl.class);
	
	
	/**
	 *  Method returns the Access Card View page.  
	 *
	 * @param  - None
	 * @return - None
	 * @since  - 1.0
	 */
	@RequestMapping("/comaccesscards")
	public String accessCardsIndex(HttpServletRequest request,Model model)
	{
		model.addAttribute("ViewName", "Customer Occupancy");
		breadCrumbService.addNode("nodeID", 0, request);
		breadCrumbService.addNode("Customer Occupancy", 1, request);
		breadCrumbService.addNode("Manage Access Cards", 2, request);
		return "com/accesscards";
	}
	
	/**
	 *  Reading all the Access Cards  
	 *
	 * @param  - None
	 * @return - None
	 * @since  - 1.0
	 */
	@RequestMapping("/accesscards/read")
	public @ResponseBody	List<?> accesCardsRead() 
	{
		return accessCardsService.readAccessCardData();
	}
	
	/**
	 *  Reading all the Access Cards For Grid Filter  
	 *
	 * @param  - None
	 * @return - None
	 * @since  - 1.0
	 */
	@RequestMapping("/accessCards/getAllAccessCards/forFilter")
	public @ResponseBody List<?> getAllAccessCards() 
	{
		return accessCardsService.findAllAccessCardNumbers();
	}
	
	/**
	 *  Created and Update Access Cards Based on action type  
	 *
	 * @param  - action [create or Update]
	 * @return - None
	 * @since  - 1.0
	 */
	@RequestMapping("/accesscards/cu")
	public @ResponseBody Object CreateUpdateOwners(@ModelAttribute("accessCards") AccessCards accessCards,HttpServletRequest request,HttpServletResponse response,ModelMap model)
	{
		String operation = request.getParameter("action");
		List<AccessCards> ps = accessCardsService.findAll();
		Iterator<AccessCards> it = ps.iterator();
		String msg = "";
		while (it.hasNext()) 
		{
			AccessCards pslot = it.next();
			if(accessCards.getAcId() != pslot.getAcId())
			{	
				if (pslot.getAcNo().equalsIgnoreCase(accessCards.getAcNo())) 
				{
					msg = "Exists";
				}
			}
			else if((operation.equalsIgnoreCase("create")))
			{
				if (pslot.getAcNo().equalsIgnoreCase(accessCards.getAcNo())) 
				{
					msg = "Exists";
				}
			}
		}
		if (!(msg.equals(""))) 
		{
			final String cardNumber = accessCards.getAcNo();
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("accessCardNumberExists", "Access Card Number : "+cardNumber+" Already Exists");
				}
			};

			jsonErrorResponse.setStatus("CARD_NUM_EXISTS");
			jsonErrorResponse.setResult(errorMapResponse);

			return jsonErrorResponse;
		} 
		else 
		{
			if((operation.equalsIgnoreCase("create")) )
			{
				//accessCards.setCreatedBy((String) SessionData.getUserDetails().get("userID"));
				accessCardsService.save(accessCards);
			}
			else if((operation.equalsIgnoreCase("update")))
			{
				//accessCards.setLastUpdatedBy((String) SessionData.getUserDetails().get("userID"));	
				accessCardsService.update(accessCards);
			}
			return accessCards;
		} 
		
	}
	
	/**
	 *  Delete the Access Cards  
	 *
	 * @param  - None
	 * @return - None
	 * @since  - 1.0
	 */
	@RequestMapping(value = "/accesscards/delete", method = RequestMethod.GET)
	public @ResponseBody Object deleteAccessCard(@ModelAttribute("accessCards") AccessCards accessCards) 
	{
		AccessCards ac = new AccessCards();
		//int id = (Integer) map.get("acId");
		try
		{
			accessCardsService.delete(accessCards.getAcId());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			@SuppressWarnings("serial")
			HashMap<String, String> errorMapResponse = new HashMap<String, String>() {
				{
					put("childFoundException", "Access Card is assigned cannot be deleted");
				}
			};

			jsonErrorResponse.setStatus("CHILD_FOUND_EXCEPTION");
			jsonErrorResponse.setResult(errorMapResponse);

			return jsonErrorResponse;
		}
		return ac;
	}
	
	/**
	 *  Reading all the Access Types to populate in dropdown list
	 *
	 * @param  - None
	 * @return - None
	 * @since  - 1.0
	 */
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/comowner/acccards/readAccessCardTypes", method = RequestMethod.GET)
	public @ResponseBody List<?> readAccessCardTypes()
	{
		String[] accessCardType = {"Permanent","Temporary","Visitor"};
		 List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();        
	        for (int i = 0;i<accessCardType.length;i++) {
	        	final String temp = accessCardType[i];
	            result.add(new HashMap<String, Object>() 
	            {{	            	
	            	put("name",temp);
	            	put("value", temp);
	            	
	            }});
	        }
	        return result;
	}
	
	
	/**
	 *  Reading all the Access Cards to assign the card to persons 
	 *
	 * @param  - None
	 * @return - Sends only access cards which are active and not assigned
	 * @since  - 1.0
	 */
	/*@RequestMapping(value = "/accesscards/getAllAccessCards", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getAllAccessCards(final Locale locale) 
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for(AccessCards accessCard :  accessCardsService.findAllAccessCards()) 
		{
			map = new HashMap<String, Object>();
			map.put("acId", accessCard.getAcId());
			map.put("acNo", accessCard.getAcNo());
			result.add(map);
		}
		return result;
	}*/
	
	@SuppressWarnings({ "unchecked", "serial" })
	@RequestMapping(value = "/accesscards/getAllAccessCards", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> getAllAccessCards(final Locale locale) 
	{
		List<Integer> listAcid=personAccessCardService.readAccessCardsForUniqe();
		List<Personnel> personAccList = msSQLentityManager.createNamedQuery("Personnel.getAccessCards").setParameter("listAcid", listAcid).getResultList();
		List<Map<String, Object>> accessCardList = new ArrayList<Map<String, Object>>();
		if(!(personAccList.isEmpty())){
		for (final Iterator<?> i = personAccList.iterator(); i.hasNext();) {
			accessCardList.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("acId", (Integer)values[0]);
					put("acNo", (Integer)values[1]);
					if((String)values[3]!=null)
					{
					put("fslsName", (String)values[2]+" "+(String)values[3]);
					}
					else
					{
					put("fslsName",(String)values[2]);
					}

					}
				});
			}

		}
		return accessCardList;
	}
	
	
	/**
	 *  Getting Access Card Number based on acId to set in Dropdown for edit mode  
	 *
	 * @param  - acId
	 * @return - accessCardNumber
	 * @since  - 1.0
	 */
	@RequestMapping(value = "/accesscards/getCardNumberOnId/{acId}", method = RequestMethod.GET)
	public @ResponseBody String getCardNumberOnId(@PathVariable int acId,final Locale locale) 
	{
		String cardNumber = accessCardsService.getaccessCradNoBasedOnId(acId); 
		return cardNumber;
	}
	
	 
	/**
	 *  Method to perform Activate/Deactivate the Access Card
	 * @param acId
	 * @param operation
	 * @param response
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = "/accessCards/CardStatus/{acId}/{operation}", method = { RequestMethod.GET,RequestMethod.POST })
	public void accessCardStatus(@PathVariable int acId, @PathVariable String operation, HttpServletResponse response, final Locale locale)
	{
		accessCardsService.updateAccessCardStatus (acId,operation,response);
		
	}
	
		
		/*@RequestMapping(value="/navigation")
	    public String index(Model model,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
	        ServletContext context =request.getSession().getServletContext();
	        
	        String path = context.getRealPath("/resources/user.nav.json");
	    
	        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
	        
	        HashMap<String, Widget[]> navigation = mapper.readValue(new File(path), new TypeReference<HashMap<String,Widget[]>>() {}); 
	        
	        navigation.remove("Framework");
	        
	        for (Map.Entry<String, Widget[]> entry : navigation.entrySet()) {
	            for (Widget widget : entry.getValue()) {
	                List<Example> examples = new ArrayList<Example>();
	                
	                for (Example example : widget.getItems()) {
	                    //path = context.getRealPath("WEB-INF/jsps/" +example.getUrl().replace(".jsp", ".jsp"));
	                  
	                    //if (new File(path).exists()) 
	                	if(example.getRole().equalsIgnoreCase("user"))
	                    {
	                    	
	                        examples.add(example);
	                    }
	                }
	                
	                widget.setItems(examples.toArray(new Example[examples.size()]));
	            }
	        }
	      
	        model.addAttribute("navigation", navigation); 
	        
	        return "com/accesscards";
	    }*/
		
		
		/**
		 * Getting the person details, If card assigned then the person details will be sent
		 * 
		 * @param acId
		 * @param locale
		 * @return - Person Details
		 */
		@RequestMapping(value = "/accessCards/getCardAssignedDetails", method = RequestMethod.POST)
		public @ResponseBody List<?> getCardAssignedDetails(@RequestParam("acId") int acId,final Locale locale) 
		{
			return accessCardsService.getCardAssignedStatusOnId(acId);
		}
		
		
}

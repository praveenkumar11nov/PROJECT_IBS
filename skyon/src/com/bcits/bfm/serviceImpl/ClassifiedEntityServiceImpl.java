package com.bcits.bfm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.ClassifiedEntity;
import com.bcits.bfm.model.Contact;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.ClassifiedEntitySerive;

@Repository
public class ClassifiedEntityServiceImpl extends GenericServiceImpl<ClassifiedEntity>  implements ClassifiedEntitySerive{

	@Override
	public List<?> getData() {
		try{
			return readClassifiedData(entityManager.createNamedQuery("ClassifiedEntity.readALLData").getResultList());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private List<?> readClassifiedData(List resultList) {
	
		List<Map<String, Object>> list=new ArrayList<>();
		Map<String, Object> map=null;
		
		for(Iterator<?> iterator=resultList.iterator(); iterator.hasNext();){
			  final Object[] values=(Object[]) iterator.next();
			  map=new HashMap<>();
			  map.put("prePaidId", values[0]);
			  map.put("propertyName", values[1]);
			  map.put("personName", values[2]);
			  map.put("mobile_No", values[3]);
			  map.put("emailId", values[4]);
			  map.put("information", values[5]);
			  list.add(map);
		}
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getPersonName(int personId) {
		try{
			return entityManager.createNativeQuery("select p.FIRST_NAME,p.LAST_NAME from PERSON p where p.PERSON_ID = "+ personId +" ").getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Contact> getMobilenumbers() {
		
		try{
			return entityManager.createQuery("SELECT  c  From Contact c  where contactType = 'Mobile'").getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Contact> geEmailId() {
		try{
			return entityManager.createQuery("SELECT  c  From Contact c  where contactType = 'Email'").getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}

package com.bcits.bfm.serviceImpl;

import java.util.List;

import javassist.bytecode.Descriptor.Iterator;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.BatchBillsEntity;
import com.bcits.bfm.model.MailMaster;
import com.bcits.bfm.service.BatchBillService;
import com.bcits.bfm.service.MailConfigService;

@Repository
public class BatchBillServiceImpl extends GenericServiceImpl<BatchBillsEntity> implements BatchBillService {

	@Override
	public void batcBillSave(List<BatchBillsEntity> l) 
	{
		try{
			System.out.println(l.size());
		   batchSave(l);
		   }catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("save successfully !");
	}
	

}

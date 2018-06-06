package com.bcits.bfm.serviceImpl.VendorsManagement;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.StoreGoodsReceiptItems;
import com.bcits.bfm.model.VendorLineItems;
import com.bcits.bfm.service.VendorsManagement.VendorLineItemsService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class VendorLineItemsServiceImpl extends GenericServiceImpl<VendorLineItems> implements VendorLineItemsService 
{
	static Logger logger = LoggerFactory.getLogger(VendorLineItemsServiceImpl.class);

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<VendorLineItems> findAll() 
	{
		return entityManager.createNamedQuery("VendorLineItems.findAll").getResultList();
	}
	
	/*@SuppressWarnings("serial")
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() 
	{		
		List<VendorLineItems> list =  findAll();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final VendorLineItems record : list)
		{
			response.add(new HashMap<String, Object>() 
			{{
				put("vilId", record.getVilId());
            	put("viId", record.getViId());
            	put("imId", record.getImId());
            	put("imName",record.getItemMaster().getImName());
            	put("rate", record.getRate());
            	put("vilSlno",record.getVilSlno());
            	put("amount",record.getAmount());
            	put("quantity", record.getQuantity());
			}});
		}
		return response;
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<VendorLineItems> findVendorLineItemsBasedOnVendorInvoice(int viId) 
	{
		return entityManager.createNamedQuery("VendorLineItems.findVendorLineItemsBasedOnVendorInvoice").setParameter("viId",viId).getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StoreGoodsReceiptItems> getStorGoodreceiptData(int storeid) 
	{ 
		return entityManager.createNamedQuery("VendorContracts.readStoreData").setParameter("storeid", storeid).getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getStoreIdBasedOnVcId(int vcid) 
	{
		int storeId = 0;
		List list = entityManager.createNamedQuery("VendorContracts.getStoreIdBasedOnVcId").setParameter("vcid", vcid).getResultList();
		Iterator<Integer> it = list.iterator();
		while(it.hasNext())
		{
			storeId = it.next();
		}
		System.out.println("**************\n\nFROM IMPL STORE ID IS--->"+storeId);
		return storeId;
	}

	/*@Override
	public List getCount() 
	{
		Integer count = 0;
		List list = entityManager.createNamedQuery("VendorLineItems.getMaxCount").getResultList();
		Iterator<Integer> it = list.iterator();
		while(it.hasNext())
		{
			count = it.next().intValue();
		}
		System.out.println("================COUNT+++"+count);
		int countVal = (int)count;
		System.out.println("++++++++++FROM IMPL"+countVal);
		return entityManager.createNamedQuery("VendorLineItems.getMaxCount").getResultList();
	}*/
}

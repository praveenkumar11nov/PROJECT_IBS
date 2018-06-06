package com.bcits.bfm.serviceImpl.accountsManagementImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;



//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Account;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.service.CommonService;
import com.bcits.bfm.service.accountsManagement.AccountService;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;

@Repository
public class AccountServiceImpl extends GenericServiceImpl<Account> implements AccountService
{
	
	//Logger log=Logger.getLogger(AccountServiceImpl.class);
	@Autowired
	private CommonService commonService;
	
	@Autowired	
	private PersonService personService;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Account> findAllPersons()
	{
		List<?> list = entityManager.createNamedQuery("Account.findAllPersonAccounts").getResultList();
		return getAllAccountRequiredFields(list);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	private List<Account> getAllAccountRequiredFields(List<?> accountsList) {
		Map<Integer, Account> accountMap = new HashMap<Integer, Account>();

		for (Iterator<?> i = accountsList.iterator(); i.hasNext();) 
		{
			final Object[] values = (Object[]) i.next();
			
			if (accountMap.containsKey((Integer) values[1]))
			{
				Account account = new Account();
				
				account.setPersonId((Integer) values[1]);
				account.setAccountNo((String) values[2]);
				account.setAccountType((String) values[3]);
				account.setAccountStatus((String) values[4]);

				String personName = "";
				personName = (String) values[5];
				if(((String) values[6]) != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String) values[6]);
				}
				account.setPersonName(personName);
				
				account.setPersonType((String) values[7]);
				account.setPersonStyle((String) values[8]);
				
				account.setPerson(null);
				
				Account oldAccount = accountMap.get(account.getPersonId());
				
				if((oldAccount.getAccountId()) > ((Integer) values[0]))
					account.setAccountId(oldAccount.getAccountId());
				else
					account.setAccountId((Integer) values[0]);
				
				account.setAllAccountNos(oldAccount.getAllAccountNos().concat(",").concat(account.getAccountNo()));
				account.setNoOfAccounts(oldAccount.getNoOfAccounts() + 1);
				
				if((account.getAccountStatus().equalsIgnoreCase("Active")) && (oldAccount.getAccountStatusAllCheck().equalsIgnoreCase("activeAll")))
					account.setAccountStatusAllCheck("activeAll");
				else if((account.getAccountStatus().equalsIgnoreCase("Inactive")) && (oldAccount.getAccountStatusAllCheck().equalsIgnoreCase("deactiveAll")))
					account.setAccountStatusAllCheck("deactiveAll");
				else
					account.setAccountStatusAllCheck("mixed");

				accountMap.put(account.getPersonId(), account);
			}

			else 
			{
				Account account = new Account();
				account.setAccountId((Integer) values[0]);
				account.setPersonId((Integer) values[1]);
				account.setAccountNo((String) values[2]);
				account.setAccountType((String) values[3]);
				account.setAccountStatus((String) values[4]);
				
				String personName = "";
				personName = (String) values[5];
				if(((String) values[6]) != null)
				{
					personName = personName.concat(" ");
					personName = personName.concat((String) values[6]);
				}
				account.setPersonName(personName);
				
				account.setPersonType((String) values[7]);
				account.setPersonStyle((String) values[8]);
				
				account.setPerson(null);
				
				account.setAllAccountNos(account.getAccountNo());
				account.setNoOfAccounts(1);
				
				if(account.getAccountStatus().equalsIgnoreCase("Active"))
					account.setAccountStatusAllCheck("activeAll");
				else
					account.setAccountStatusAllCheck("deactiveAll");

				accountMap.put(account.getPersonId(), account);

			}
		}

		List<Account> newAccountList = new ArrayList<Account>();
		Collection<Account> col = accountMap.values();
		
		for (Iterator<Account> iterator = col.iterator(); iterator.hasNext();)
		{
			Account account2 = (Account) iterator.next();
			
			newAccountList.add(account2);
			
		}
		
		return newAccountList;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Account> findAllAccounts(int personId)
	{
		List<Account> list = entityManager.createNamedQuery("Account.findAllAccounts").setParameter("personId", personId).getResultList();
		return getAllRequiredAccountFields(list);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	private List<Account> getAllRequiredAccountFields(List<Account> list)
	{
		List<Account> newList = new ArrayList<Account>();
		for (Iterator<Account> iterator = list.iterator(); iterator.hasNext();)
		{
			Account account = (Account) iterator.next();
			Person person = account.getPerson();
			account.setPersonType(person.getPersonType());
			account.setPersonStyle(person.getPersonStyle());
			
			String personName = "";
			personName = person.getFirstName();
			if((person.getLastName()) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat(person.getLastName());
			}
			account.setPersonName(personName);
			account.setPerson(null);
			newList.add(account);
		}
		return newList;
	}

	@Override
	public void setAccountStatus(int personId, String operation, HttpServletResponse response)
	{
		try
		{
			Person person = personService.find(personId);
			
			String personName = "";
			personName = person.getFirstName();
			if((person.getLastName()) != null)
			{
				personName = personName.concat(" ");
				personName = personName.concat(person.getLastName());
			}
			
			PrintWriter out = response.getWriter();

			if(operation.equalsIgnoreCase("activateAll"))
			{
				entityManager.createNamedQuery("Account.setAccountStatus").setParameter("accountStatus", "Active").setParameter("personId", personId).executeUpdate();
				out.write("All Accounts Activated for "+personName);
			}
			else
			{
				entityManager.createNamedQuery("Account.setAccountStatus").setParameter("accountStatus", "Inactive").setParameter("personId", personId).executeUpdate();
				out.write("All Accounts Deactivated for "+personName);
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateAccountStatusFromInnerGrid(int accountId, HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			
			List<String> attributesList = new ArrayList<String>();
			attributesList.add("accountStatus");

			Account account = find(accountId);
			
			if(account.getAccountStatus().equalsIgnoreCase("Active"))
			{
				entityManager.createNamedQuery("Account.updateAccountStatusFromInnerGrid").setParameter("accountStatus", "Inactive").setParameter("accountId", accountId).executeUpdate();
				out.write("Account Deactivated");
			}
			else
			{
				entityManager.createNamedQuery("Account.updateAccountStatusFromInnerGrid").setParameter("accountStatus", "Active").setParameter("accountId", accountId).executeUpdate();
				out.write("Account Activated");
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<?> getAccountNumber(int personId,int propertyId) {
		return entityManager.createNamedQuery("Account.getAccountNumber").setParameter("personId", personId).setParameter("propertyId", propertyId).getResultList();
	}

	@Override
	public int autoGeneratedAccountNumber() {
		return ((BigDecimal)entityManager.createNativeQuery("SELECT ACCOUNT_NUM_SEQ.nextval FROM dual").getSingleResult()).intValue();
	}
	
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<?> findPersonForFilters() {
		List<?> details = entityManager.createNamedQuery("Account.findPersonForFilters").getResultList();
		return details;
	}

	@Override
	public int getAccountIdByNo(String accountNo) {
		return (int) entityManager.createNamedQuery("Account.getAccountIdByNo").setParameter("accountNo", accountNo).getSingleResult();
	}

	@Override
	public Integer findAccountNumberBasedOnId(int propid) {
		try 
		{
			return (Integer) entityManager.createNamedQuery("Account.getAccountNumberBasedOnPropertyId").setParameter("propertyId", propid).getSingleResult();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return 0;
		}
		
	}

	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> findUniqueAccountNo(int personId,int propertyId){
		return entityManager.createNamedQuery("Account.testUniqueAccountMeter").setParameter("personId", personId).setParameter("propertyId", propertyId).getResultList();		
	}
	
	
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> testUniqueAccount(String accountNo,int propertyId){
		return entityManager.createNamedQuery("Account.testUniqueAccount").setParameter("accountNo", accountNo).setParameter("propertyId", propertyId).getResultList();		
	}
	
	

}

package com.bcits.bfm.service.customerOccupancyManagement;

import java.util.List;
import java.util.Map;

import com.bcits.bfm.model.PersonAccessCards;
import com.bcits.bfm.service.GenericService;

public interface PersonAccessCardService extends GenericService<PersonAccessCards>
{

	PersonAccessCards getAccessCardsObject(Map<String, Object> map,
			String string, PersonAccessCards personAccessCards);

	List<?> findOnPersonId(int personId);
	
	public List<PersonAccessCards> findPersonAccessCards();

	List<Integer> readAccessCardsForUniqe();

	List<PersonAccessCards> getPersonBasedOnAccessCard(Integer objectIdLo);

	List<PersonAccessCards> getPersonAccessCardBasedOnPersonId(int personId);

}

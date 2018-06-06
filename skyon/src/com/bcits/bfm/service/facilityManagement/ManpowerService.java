package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bcits.bfm.model.Manpower;
import com.bcits.bfm.model.Person;
import com.bcits.bfm.model.StaffNotices;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.GenericService;


public interface ManpowerService extends GenericService<Person> {
	public List<Map<String, Object>> setAllFeildforManpower(Users user);
}
package com.bcits.bfm.calender;

import java.util.List;

import com.bcits.bfm.service.GenericService;

public interface TaskDao extends GenericService<Task> {
    public List<Task> getList();
    
    public void saveOrUpdate(List<Task> tasks);
    
    public void delete(List<Task> tasks);

	public void Update(List<Task> tasks);
}

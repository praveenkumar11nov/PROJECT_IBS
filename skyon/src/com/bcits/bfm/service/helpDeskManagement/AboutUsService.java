package com.bcits.bfm.service.helpDeskManagement;

import java.sql.Blob;
import java.util.List;

import com.bcits.bfm.model.AboutUs;
import com.bcits.bfm.service.GenericService;

public interface AboutUsService extends GenericService<AboutUs> 
{
	List<AboutUs> getAll();
	public void uploadImageOnId(int personId,Blob files);
	public Blob getImage(int personId);
	public List<String> findbyAboutId(int personId);
}

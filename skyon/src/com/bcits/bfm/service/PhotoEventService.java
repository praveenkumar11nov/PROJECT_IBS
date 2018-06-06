package com.bcits.bfm.service;

import java.sql.Blob;
import java.util.List;

import com.bcits.bfm.model.PhotoEvent;

public interface PhotoEventService extends GenericService<PhotoEvent>
{

	public List<PhotoEvent> findAllPhotoevent();
	void addDocument(int peId, Blob blob);
} 

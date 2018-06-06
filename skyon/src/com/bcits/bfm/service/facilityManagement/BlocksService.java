package com.bcits.bfm.service.facilityManagement;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bcits.bfm.model.Blocks;
import com.bcits.bfm.service.GenericService;

public interface BlocksService extends  GenericService<Blocks> {
	public abstract List<Blocks> findAll();

	Blocks getBeanObject(Blocks block, String string,
			Map<String, Object> map, HttpServletRequest request);

	List<Blocks> findBlockOnProjectId(int projectId);

	public abstract Blocks findbyName(String stringCellValue);
	
	Blocks getBlockNameByBlockId( int blockId );

	public abstract Integer getBlockIdByName(String value);

	public  Integer getProjectIdBasedOnBlockName(String value2);

	public  Integer getNoOfBlocks(int projectId);

	public  Long getSumOfBlocks(int projectId);
}
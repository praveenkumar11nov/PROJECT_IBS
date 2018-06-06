package com.bcits.bfm.service.helpDeskManagement;

import java.sql.Blob;
import java.util.List;

import com.bcits.bfm.model.ItemMasterEntity;
import com.bcits.bfm.service.GenericService;

public interface ItemMasterService extends GenericService<ItemMasterEntity>{

public	List<ItemMasterEntity> findAllData();

public void uploadImageOnId(int gid, Blob itemImage);

public Blob getImage(int gid);

public void uploadAssetOnId(int gid, Blob itemImage, String docType);

public void updateVisitorImage(int gid, Blob blob);

public List<ItemMasterEntity> findAllDatas(int id);





	/*public List<ItemMaster> findAll();*/

	
}

package com.bcits.bfm.service.helpDeskManagement;

import java.util.List;

import com.bcits.bfm.model.HelpDeskSettingsEntity;
import com.bcits.bfm.service.GenericService;

public interface HelpDeskAccessSettingsService extends  GenericService<HelpDeskSettingsEntity> {

	List<HelpDeskSettingsEntity> findAll();

	List<Integer> getAllRoleIds();

	List<?> getAllActiveRoles();
}

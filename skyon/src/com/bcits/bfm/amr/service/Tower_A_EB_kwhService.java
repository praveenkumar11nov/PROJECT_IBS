package com.bcits.bfm.amr.service;

import java.util.List;

import com.bcits.bfm.model.Tower_A_EB_kwh;

public interface Tower_A_EB_kwhService extends GenericMSSQLService<Tower_A_EB_kwh> {

	List<Tower_A_EB_kwh> read();
}

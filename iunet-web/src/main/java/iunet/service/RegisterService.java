package iunet.service;

import java.util.Map;

import iunet.model.proj.UmActivation;
import iunet.model.proj.UmUser;

public interface RegisterService {
	
	boolean emailHasRegister(Map<String, Object> map);
	
	boolean emailHasRegisterOccupy(String email);
	
	int insertActivationByUUID(UmActivation record);
	
	String queryActivationDataByUUID(String uuid);
	
	int updateActivationStateByUUID(String uuid);
}

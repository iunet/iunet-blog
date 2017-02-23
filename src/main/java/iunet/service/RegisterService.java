package iunet.service;

import iunet.model.proj.UmActivation;
import iunet.model.proj.UmUser;

public interface RegisterService {
	
	boolean emailHasRegister(String email);
	
	boolean emailHasRegisterOccupy(String email);
	
	int insertActivationByUUID(UmActivation record);
	
	String queryActivationDataByUUID(String uuid);
	
	int updateActivationStateByUUID(String uuid);
	
	int insertUser(UmUser user);
}

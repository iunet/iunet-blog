package iunet.dao;

import iunet.model.proj.UmUser;

public interface BaseUserDao {
	
	UmUser selectUserByLoginName(String loginName);
	
	UmUser selectUserByEmail(String email);
}

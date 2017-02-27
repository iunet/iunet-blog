package iunet.dao;

import java.util.Map;

public interface BaseRegisterDao {
	
	int selectActivationCountByEmail(Map<String, Object> map);
	
}

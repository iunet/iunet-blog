package iunet.dao;

public interface BaseRegisterDao {
	
	int selectActivationCountByEmail(String email);
	
}

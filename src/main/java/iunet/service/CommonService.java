package iunet.service;

public interface CommonService {
	int selectNetSequence(String tableName);
	
	int updateNetSequence(String tableName);
	
	int insertNetSequence(String tableName);
	
	int getNewId(String tableName);
}

package iunet.dao;

public interface CommonDao {
	int selectNetSequence(String tableName);
	
	int updateNetSequence(String tableName);
	
	int insertNetSequence(String tableName);
}

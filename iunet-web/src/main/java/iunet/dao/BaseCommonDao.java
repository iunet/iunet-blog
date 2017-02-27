package iunet.dao;

import java.math.BigDecimal;

public interface BaseCommonDao {
	
	int incrementSerialNumberByTableName(String tableName);
	
	BigDecimal selectSerialNumberByTableName(String tableName);
}

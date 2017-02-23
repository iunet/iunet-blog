package iunet.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BaseIndexDao {
	
	List<Map<String, Object>> queryUserRoleByUserId(BigDecimal user_id);

	List<Map<String, Object>> queryFunctions();
	
}

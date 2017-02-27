package iunet.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BaseLoginDao {
	
	int updateLoginOutTime(Map<String, Object> map);
	
	int selectUserCountByEmail(String email);

	List<String> selectGroupIdsByUserId(BigDecimal userId);

	List<String> selectRoleIdsByUserId(BigDecimal userId);
}

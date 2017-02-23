package iunet.dao;

import java.math.BigDecimal;
import java.util.List;

import iunet.model.proj.UmLoginLog;
import iunet.model.proj.UmUser;

public interface BaseLoginDao {
	
	int updateLoginOutTime(UmLoginLog loginLog);
	
	int selectUserCountByEmail(String email);

	List<String> selectGroupIdsByUserId(BigDecimal userId);

	List<String> selectRoleIdsByUserId(BigDecimal userId);
}

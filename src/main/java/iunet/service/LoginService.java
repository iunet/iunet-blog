package iunet.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import iunet.model.proj.UmLoginLog;
import iunet.model.proj.UmUser;

public interface LoginService {

	UmUser selectUserByLoginName(String loginName);

	List<String> selectGroupIdsByUserId(BigDecimal userId);

	List<String> selectRoleIdsByUserId(BigDecimal userId);

	int insertLoginLog(UmLoginLog loginLog);

	int updateLoginOutTime(BigDecimal user_id);
}

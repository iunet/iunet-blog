package iunet.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import iunet.model.proj.UmFunction;

public interface IndexService {

	List<Map<String, Object>> queryFunctions();

	List<Map<String, Object>> queryUserRoleByUserId(BigDecimal user_id);

	
}

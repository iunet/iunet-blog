package iunet.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iunet.dao.BaseIndexDao;
import iunet.service.IndexService;
import iunet.util.DateUtil;

@Service
public class IndexServiceImpl implements IndexService {

	private static final Logger log = LoggerFactory.getLogger(IndexServiceImpl.class);
	
	@Autowired
	BaseIndexDao baseIndexDao;
	
	@Override
	public List<Map<String, Object>> queryFunctions() {
		log.info("queryFunctions,Time:{}", DateUtil.nowStr());
		return baseIndexDao.queryFunctions();
	}

	@Override
	public List<Map<String, Object>> queryUserRoleByUserId(BigDecimal user_id) {
		log.info("queryUserRoleByUserId user_id：{},Time:{}", user_id, DateUtil.nowStr());
		return baseIndexDao.queryUserRoleByUserId(user_id);
	}
}

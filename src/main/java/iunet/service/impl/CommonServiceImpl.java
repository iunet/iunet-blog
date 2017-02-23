package iunet.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iunet.dao.BaseCommonDao;
import iunet.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {
	
	private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);
	
	@Autowired
	BaseCommonDao baseCommonDao;
	
	@Override
	public BigDecimal getNewId(String tableName) {
		log.info("getNewId incrementSerialNumberByTableName tableName:{}", tableName);
		int increment = baseCommonDao.incrementSerialNumberByTableName(tableName);
		log.info("getNewId incrementSerialNumberByTableName increment:{}", increment);
		log.info("getNewId selectSerialNumberByTableName tableName:{}", tableName);
		BigDecimal serialNumber = baseCommonDao.selectSerialNumberByTableName(tableName);
		log.info("getNewId selectSerialNumberByTableName serialNumber:{}", serialNumber);
		return serialNumber;
	}
}

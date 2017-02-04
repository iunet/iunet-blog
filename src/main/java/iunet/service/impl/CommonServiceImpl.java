package iunet.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iunet.dao.CommonDao;
import iunet.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {
	
	private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);
	
	@Autowired
	CommonDao commonDao;
	
	@Override
	public int selectNetSequence(String tableName) {
		log.info("selectNetSequence tableName：{},Time:{}", tableName, System.currentTimeMillis());
		return commonDao.selectNetSequence(tableName);
	}

	@Override
	public int updateNetSequence(String tableName) {
		log.info("updateNetSequence tableName：{},Time:{}", tableName, System.currentTimeMillis());
		return commonDao.updateNetSequence(tableName);
	}

	@Override
	public int insertNetSequence(String tableName) {
		log.info("insertNetSequence tableName：{},Time:{}", tableName, System.currentTimeMillis());
		return commonDao.insertNetSequence(tableName);
	}
	
	@Override
	public int getNewId(String tableName) {
		log.info("getNewId updateNetSequence tableName:{}", tableName);
		int update = updateNetSequence(tableName);
		log.info("getNewId updateNetSequence update:{}", update);
		log.info("getNewId selectNetSequence tableName:{}", tableName);
		int serial_number = selectNetSequence(tableName);
		log.info("getNewId selectNetSequence serial_number:{}", serial_number);
		return serial_number;
	}
}

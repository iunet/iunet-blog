package iunet.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iunet.dao.UmUserMapper;
import iunet.model.proj.UmUser;
import iunet.service.UserService;
import iunet.util.DateUtil;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UmUserMapper userMapper;

	@Override
	public int insertUser(UmUser user) {
		log.info("insertUser user：{},Time:{}", user, DateUtil.nowStr());
		return userMapper.insertSelective(user);
	}

	@Override
	public int updateUser(UmUser user) {
		log.info("updateUser user：{},Time:{}", user, DateUtil.nowStr());
		return userMapper.updateByPrimaryKeySelective(user);
	}
}

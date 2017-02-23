package iunet.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iunet.dao.BaseUserDao;
import iunet.dao.UmUserMapper;
import iunet.model.proj.UmUser;
import iunet.service.ResetService;
import iunet.util.DateUtil;

@Service
public class ResetServiceImpl implements ResetService {

	private static final Logger log = LoggerFactory.getLogger(ResetServiceImpl.class);

	@Autowired
	UmUserMapper userMapper;
	
	@Autowired
	BaseUserDao baseUserDao;
	
	@Override
	public int updateUserPassword(String password, String email) {
		log.info("updateUserPassword email：{},Time:{}", email, DateUtil.nowStr());
		UmUser _user = baseUserDao.selectUserByEmail(email);
		UmUser user = new UmUser();
		user.setId(_user.getId());
		user.setPassword(password);
		user.setUpdateTime(DateUtil.Timestamp());
		return userMapper.updateByPrimaryKeySelective(user);
	}

	
}

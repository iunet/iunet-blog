package iunet.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import iunet.dao.BaseLoginDao;
import iunet.dao.BaseUserDao;
import iunet.dao.UmLoginLogMapper;
import iunet.dao.UmUserMapper;
import iunet.dao.UmUserToGroupMapper;
import iunet.dao.UmUserToRoleMapper;
import iunet.model.proj.UmLoginLog;
import iunet.model.proj.UmUser;
import iunet.service.LoginService;
import iunet.util.DateUtil;

@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	UmUserMapper userMapper;

	@Autowired
	UmUserToGroupMapper userToGroupMapper;

	@Autowired
	UmUserToRoleMapper userToRoleMapper;

	@Autowired
	UmLoginLogMapper loginLogMapper;
	
	@Autowired
	BaseLoginDao baseLoginDao;
	
	@Autowired
	BaseUserDao baseUserDao;

	@Override
	public UmUser selectUserByLoginName(String loginName) {
		log.info("selectUserByLoginName login_name：{},Time:{}", loginName, DateUtil.nowStr());
		return baseUserDao.selectUserByLoginName(loginName);
	}

	@Override
	public List<String> selectGroupIdsByUserId(BigDecimal userId) {
		log.info("selectGroupIdsByUserId userId：{},Time:{}", userId, DateUtil.nowStr());
		return baseLoginDao.selectGroupIdsByUserId(userId);
	}

	@Override
	public List<String> selectRoleIdsByUserId(BigDecimal userId) {
		log.info("selectRoleIdsByUserId userId：{},Time:{}", userId, DateUtil.nowStr());
		return baseLoginDao.selectRoleIdsByUserId(userId);
	}

	@Override
	public int insertLoginLog(UmLoginLog loginLog) {
		log.info("insertLoginLog loginLog：{},Time:{}", JSONObject.toJSON(loginLog), DateUtil.nowStr());
		return loginLogMapper.insert(loginLog);
	}

	@Override
	public int updateLoginOutTime(Map<String, Object> map) {
		log.info("updateLoginOutTime map：{},Time:{}", map, DateUtil.nowStr());
		return baseLoginDao.updateLoginOutTime(map);
	}
}

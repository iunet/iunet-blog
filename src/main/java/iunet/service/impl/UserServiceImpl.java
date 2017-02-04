package iunet.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import iunet.dao.UserDao;
import iunet.model.um.Function;
import iunet.model.um.LoginLog;
import iunet.model.um.Roleauthority;
import iunet.model.um.User;
import iunet.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User queryUserByLoginName(String login_name) {
		log.info("根据login_name获取用户信息 login_name：{},Time:{}", login_name, System.currentTimeMillis());
		return userDao.queryUserByLoginName(login_name);
	}
	
	@Override
	public int queryUserIdByEmail(String email) {
		log.info("根据email获取用户信息  email：{},Time:{}", email, System.currentTimeMillis());
		return userDao.queryUserIdByEmail(email);
	}
	
	@Override
	public boolean emailHasOccupy(String email) {
		log.info("用户邮箱是否被占用  email：{},Time:{}", email, System.currentTimeMillis());
		return userDao.queryUserByEmailCount(email) > 0;
	}
	
	@Override
	public List<String> queryGroupIdsByUserId(int id) {
		log.info("获得用户所属分组id：{},Time:{}", id, System.currentTimeMillis());
		return userDao.queryGroupIdsByUserId(id);
	}

	@Override
	public List<String> queryRoleIdsByUserId(int id) {
		log.info("获得用户所属角色id：{},Time:{}", id, System.currentTimeMillis());
		return userDao.queryRoleIdsByUserId(id);
	}

	@Override
	public int insertUser(User user) {
		log.info("新建用户 user：{},Time:{}", ((JSONObject) JSONObject.toJSON(user)).toJSONString(), System.currentTimeMillis());
		return userDao.insertUser(user);
	}

	@Override
	public int updateUser(User user) {
		log.info("更新用户信息  user：{},Time:{}", ((JSONObject) JSONObject.toJSON(user)).toJSONString(), System.currentTimeMillis());
		return userDao.updateUser(user);
	}

	@Override
	public int insertLoginLog(LoginLog loginlog) {
		log.info("添加登录日志 loginlog：{},Time:{}", ((JSONObject) JSONObject.toJSON(loginlog)).toJSONString(), System.currentTimeMillis());
		return userDao.insertLoginLog(loginlog);
	}
	
	@Override
	public int updateLoginLog(int user_id, Timestamp logout_time) {
		log.info("更新登出时间  user_id：{},logout_time：{},Time:{}", user_id, logout_time, System.currentTimeMillis());
		return userDao.updateLoginLog(user_id, logout_time);
	}
	
	@Override
	public boolean emailHasRegisterOccupy(String email) {
		log.info("注册邮箱是否被占用  email：{},Time:{}", email, System.currentTimeMillis());
		return userDao.queryRegisterByEmailCount(email) > 0;
	}
	
	@Override
	public String queryActivationDataByUUID(String uuid) {
		log.info("根据UUID 查询用户注册数据 uuid：{},Time:{}", uuid, System.currentTimeMillis());
		return userDao.queryActivationDataByUUID(uuid);
	}

	@Override
	public int insertActivationByUUID(Map<String, Object> map) {
		log.info("插入用户注册数据 map：{},Time:{}", ((JSONObject) JSONObject.toJSON(map)).toJSONString(), System.currentTimeMillis());
		return userDao.insertActivationByUUID(map);
	}

	@Override
	public int updateActivationByUUID(String uuid) {
		log.info("根据UUID 更新账户激活状态 uuid：{},Time:{}", uuid, System.currentTimeMillis());
		return userDao.updateActivationByUUID(uuid);
	}

	@Override
	public int insertFunction(Function function) {
		log.info("添加权限  function：{},Time:{}", ((JSONObject) JSONObject.toJSON(function)).toJSONString(), System.currentTimeMillis());
		return userDao.insertFunction(function);
	}

	@Override
	public int insertRoleauthority(Roleauthority roleauthority) {
		log.info("角色添加权限  roleauthority：{},Time:{}", ((JSONObject) JSONObject.toJSON(roleauthority)).toJSONString(), System.currentTimeMillis());
		return userDao.insertRoleauthority(roleauthority);
	}
}

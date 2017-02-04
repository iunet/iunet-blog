package iunet.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import iunet.model.um.Function;
import iunet.model.um.LoginLog;
import iunet.model.um.Roleauthority;
import iunet.model.um.User;

public interface UserService {
	/**
	 * 根据login_name获取用户信息
	 * @param login_name
	 * @return
	 */
	User queryUserByLoginName(String login_name);
	
	/**
	 * 根据email获取用户id
	 * @param email
	 * @return
	 */
	int queryUserIdByEmail(String email);
	
	
	/**
	 * 用户邮箱是否被占用
	 * @param email
	 * @return
	 */
	boolean emailHasOccupy(String email);
	
	/**
	 * 获得用户所属分组id
	 * @param id
	 * @return
	 */
	List<String> queryGroupIdsByUserId(int id);
	
	/**
	 * 获得用户所属角色id
	 * @param id
	 * @return
	 */
	List<String> queryRoleIdsByUserId(int id);
	
	/**
	 * 新建用户
	 * @param user
	 * @return
	 */
	int insertUser(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	int updateUser(User user);
	
	/**
	 * 添加登录日志
	 * @param loginlog
	 * @return
	 */
	int insertLoginLog(LoginLog loginlog);
	
	/**
	 * 更新登出时间
	 * @param loginlog
	 * @return
	 */
	int updateLoginLog(int user_id, Timestamp logout_time);
	
	/**
	 * 注册邮箱是否被占用
	 * @param email
	 * @return
	 */
	boolean emailHasRegisterOccupy(String email);
	
	/**
	 * 根据UUID 查询用户注册数据
	 * @param user_id
	 * @return
	 */
	String queryActivationDataByUUID(String uuid);
	
	/**
	 * 插入用户注册数据
	 * @param uuid
	 * @return
	 */
	int insertActivationByUUID(Map<String, Object> map);
	
	/**
	 * 根据UUID 更新账户激活状态
	 * @param uuid
	 * @return
	 */
	int updateActivationByUUID(String uuid);
	
	/**
	 * 添加权限
	 * @param function
	 * @return
	 */
	int insertFunction(Function function);
	
	/**
	 * 角色添加权限
	 * @param roleauthority
	 * @return
	 */
	int insertRoleauthority(Roleauthority roleauthority);
}

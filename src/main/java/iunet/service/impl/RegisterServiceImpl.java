package iunet.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iunet.dao.BaseLoginDao;
import iunet.dao.BaseRegisterDao;
import iunet.dao.UmActivationMapper;
import iunet.dao.UmUserMapper;
import iunet.model.proj.UmActivation;
import iunet.model.proj.UmUser;
import iunet.service.RegisterService;
import iunet.util.DateUtil;

@Service
public class RegisterServiceImpl implements RegisterService {

	private static final Logger log = LoggerFactory.getLogger(RegisterServiceImpl.class);

	@Autowired
	UmUserMapper userMapper;

	@Autowired
	UmActivationMapper activationMapper;
	
	@Autowired
	BaseRegisterDao baseRegisterDao;
	
	@Autowired
	BaseLoginDao baseLoginDao;

	@Override
	public boolean emailHasRegister(String email) {
		log.info("emailHasRegister email：{},Time:{}", email, DateUtil.nowStr());
		return baseRegisterDao.selectActivationCountByEmail(email) > 0;
	}

	@Override
	public boolean emailHasRegisterOccupy(String email) {
		log.info("emailHasRegisterOccupy email：{},Time:{}", email, DateUtil.nowStr());
		return baseLoginDao.selectUserCountByEmail(email) > 0;
	}

	@Override
	public int insertActivationByUUID(UmActivation activation) {
		log.info("insertActivationByUUID UmActivation：{},Time:{}", activation, DateUtil.nowStr());
		return activationMapper.insert(activation);
	}

	@Override
	public String queryActivationDataByUUID(String uuid) {
		log.info("queryActivationDataByUUID uuid：{},Time:{}", uuid, DateUtil.nowStr());
		UmActivation activation = activationMapper.selectByPrimaryKey(uuid);
		log.info("queryActivationDataByUUID activation：{},Time:{}", activation, DateUtil.nowStr());
		return activation.getData();
	}

	@Override
	public int updateActivationStateByUUID(String uuid) {
		log.info("updateActivationStateByUUID uuid：{},Time:{}", uuid, DateUtil.nowStr());
		UmActivation activation = new UmActivation();
		activation.setState(new BigDecimal(0));
		return activationMapper.updateByPrimaryKeySelective(activation);
	}
	
	@Override
	public int insertUser(UmUser user) {
		log.info("insertUser user：{},Time:{}", user, DateUtil.nowStr());
		return userMapper.insertSelective(user);
	}
}

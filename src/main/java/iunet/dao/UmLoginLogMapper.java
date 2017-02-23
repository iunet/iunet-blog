package iunet.dao;

import iunet.model.proj.UmLoginLog;

public interface UmLoginLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(UmLoginLog record);

    int insertSelective(UmLoginLog record);

    UmLoginLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UmLoginLog record);

    int updateByPrimaryKey(UmLoginLog record);
}
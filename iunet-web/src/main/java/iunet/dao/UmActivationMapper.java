package iunet.dao;

import iunet.model.proj.UmActivation;

public interface UmActivationMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(UmActivation record);

    int insertSelective(UmActivation record);

    UmActivation selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(UmActivation record);

    int updateByPrimaryKey(UmActivation record);
}
package iunet.dao;

import iunet.model.proj.UmPersonToGroupKey;

public interface UmPersonToGroupMapper {
    int deleteByPrimaryKey(UmPersonToGroupKey key);

    int insert(UmPersonToGroupKey record);

    int insertSelective(UmPersonToGroupKey record);
}
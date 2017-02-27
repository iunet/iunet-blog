package iunet.dao;

import iunet.model.proj.UmUserToGroup;
import iunet.model.proj.UmUserToGroupKey;

public interface UmUserToGroupMapper {
    int deleteByPrimaryKey(UmUserToGroupKey key);

    int insert(UmUserToGroup record);

    int insertSelective(UmUserToGroup record);

    UmUserToGroup selectByPrimaryKey(UmUserToGroupKey key);

    int updateByPrimaryKeySelective(UmUserToGroup record);

    int updateByPrimaryKey(UmUserToGroup record);
}
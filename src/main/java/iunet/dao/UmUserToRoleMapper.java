package iunet.dao;

import iunet.model.proj.UmUserToRoleKey;

public interface UmUserToRoleMapper {
    int deleteByPrimaryKey(UmUserToRoleKey key);

    int insert(UmUserToRoleKey record);

    int insertSelective(UmUserToRoleKey record);
}
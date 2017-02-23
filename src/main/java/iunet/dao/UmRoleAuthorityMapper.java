package iunet.dao;

import iunet.model.proj.UmRoleAuthorityKey;

public interface UmRoleAuthorityMapper {
    int deleteByPrimaryKey(UmRoleAuthorityKey key);

    int insert(UmRoleAuthorityKey record);

    int insertSelective(UmRoleAuthorityKey record);
}
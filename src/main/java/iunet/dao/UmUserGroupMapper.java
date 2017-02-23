package iunet.dao;

import iunet.model.proj.UmUserGroup;
import java.math.BigDecimal;

public interface UmUserGroupMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(UmUserGroup record);

    int insertSelective(UmUserGroup record);

    UmUserGroup selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(UmUserGroup record);

    int updateByPrimaryKey(UmUserGroup record);
}
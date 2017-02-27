package iunet.dao;

import iunet.model.proj.UmRole;
import java.math.BigDecimal;

public interface UmRoleMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(UmRole record);

    int insertSelective(UmRole record);

    UmRole selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(UmRole record);

    int updateByPrimaryKey(UmRole record);
}
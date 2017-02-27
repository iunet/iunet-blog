package iunet.dao;

import iunet.model.proj.UmUser;
import java.math.BigDecimal;

public interface UmUserMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(UmUser record);

    int insertSelective(UmUser record);

    UmUser selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(UmUser record);

    int updateByPrimaryKey(UmUser record);
}
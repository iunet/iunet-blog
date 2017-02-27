package iunet.dao;

import iunet.model.proj.UmFunction;
import java.math.BigDecimal;

public interface UmFunctionMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(UmFunction record);

    int insertSelective(UmFunction record);

    UmFunction selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(UmFunction record);

    int updateByPrimaryKey(UmFunction record);
}
package iunet.dao;

import iunet.model.proj.UmPerson;
import java.math.BigDecimal;

public interface UmPersonMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(UmPerson record);

    int insertSelective(UmPerson record);

    UmPerson selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(UmPerson record);

    int updateByPrimaryKey(UmPerson record);
}
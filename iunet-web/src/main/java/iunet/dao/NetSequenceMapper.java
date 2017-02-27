package iunet.dao;

import iunet.model.proj.NetSequence;

public interface NetSequenceMapper {
    int deleteByPrimaryKey(String tableName);

    int insert(NetSequence record);

    int insertSelective(NetSequence record);

    NetSequence selectByPrimaryKey(String tableName);

    int updateByPrimaryKeySelective(NetSequence record);

    int updateByPrimaryKey(NetSequence record);
}
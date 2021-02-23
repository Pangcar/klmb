package com.server;

import com.doman.BaseConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper
public interface BaseConfigMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(BaseConfig record);

    int insertSelective(BaseConfig record);

    BaseConfig selectByPrimaryKey(Integer id);

    List<BaseConfig> selectByType(String type);

    int updateByPrimaryKeySelective(BaseConfig record);

    int updateByPrimaryKey(BaseConfig record);

    int addBaseConfig(BaseConfig record);
}
package com.server;

import com.doman.Parent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ParentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Parent record);

    int insertSelective(Parent record);

    Parent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Parent record);

    int updateByPrimaryKey(Parent record);
}
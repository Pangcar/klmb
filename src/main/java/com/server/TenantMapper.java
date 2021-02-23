package com.server;

import com.doman.Tenant;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TenantMapper {

    int deleteByPrimaryKey(String id);

    int insert(Tenant record);

    int insertSelective(Tenant record);

    Tenant selectByPrimaryKey(Integer id);

    List<Tenant> selectAll();

    int updateByPrimaryKeySelective(Tenant record);

    int updateByPrimaryKey(Tenant record);






}
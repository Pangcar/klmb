package com.server;

import com.doman.Combo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper
public interface ComboMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByKcode(String kcode);


    int insert(Combo record);

    int insertSelective(Combo record);

    Combo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Combo record);

    int updateByPrimaryKey(Combo record);


    List<Combo> selectAll();

}
package com.server;

import com.doman.Consumer;
import com.doman.LoginFrom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ConsumerMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Consumer record);

    int insertSelective(Consumer record);

    Consumer selectByPrimaryKey(Integer id);

//    int selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Consumer record);

    int updateByPrimaryKey(Consumer record);

    List<Consumer> login(LoginFrom loginFrom);
//    int login(LoginFrom loginFrom);


    List<Consumer> getConsumer(String organize);

}
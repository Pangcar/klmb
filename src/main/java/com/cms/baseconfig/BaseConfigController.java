package com.cms.baseconfig;

import com.auxiliary.Util;
import com.doman.BaseConfig;
import com.resultmsg.BaseEnums;
import com.resultmsg.Result;
import com.resultmsg.Results;
import com.server.BaseConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/educloud")
@RestController
public class BaseConfigController {

    @Autowired
    private BaseConfigMapper mapper;


    @RequestMapping(value = "/grades", method = RequestMethod.GET)
    public Result getGrades() {
        try {
            List<BaseConfig> list = mapper.selectByType("grade");
            return Results.successWithData(list, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
        } catch (Exception e) {
            return Results.failureWithData(e, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
        }
    }

    @RequestMapping(value = "/baseconfig",method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Result addBaseConfig(BaseConfig baseConfig){

        baseConfig.setCreateTime(Util.getCurrentTime());
        baseConfig.setUpdateTime(Util.getCurrentTime());

        int res = mapper.insertSelective(baseConfig);
        if(res>0){
            return Results.success(BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());

        }else{
            return Results.failure(BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());

        }
    }


}

package com.cms.parent;

import com.auxiliary.Util;
import com.doman.Parent;
import com.resultmsg.BaseEnums;
import com.resultmsg.Result;
import com.resultmsg.Results;
import com.server.ParentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/educloud")
@RestController
public class ParentController {

    @Autowired
    private ParentMapper mapper;

    /**
     * 创建家长角色
     * @param parent
     * @return
     */
    @RequestMapping(value = "/parents",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public Result addParent(Parent parent){
        try{
            parent.setCreateTime(Util.getCurrentTime());
            parent.setUpdateTime(Util.getCurrentTime());

            int res = mapper.insert(parent);
            if(res>0){
                return Results.success(BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());
            }else{
                return Results.failure(BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
            }
        }catch (Exception e){
            return Results.failureWithData(e,BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
        }

    }

    /**
     * 删除家长角色(未完成)
     * @param parent
     * @return
     */
    @RequestMapping(value = "/parents",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public Result addCombo(Parent parent){
        try{

            parent.setCreateTime(Util.getCurrentTime());
            parent.setUpdateTime(Util.getCurrentTime());

            int res = mapper.insert(parent);
            if(res>0){
                return Results.success(BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());
            }else{
                return Results.failure(BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
            }
        }catch (Exception e){
            return Results.failureWithData(e,BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
        }

    }




}

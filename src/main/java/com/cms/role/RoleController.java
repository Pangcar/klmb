package com.cms.role;

import com.auxiliary.Util;
import com.doman.Role;
import com.resultmsg.BaseEnums;
import com.resultmsg.Result;
import com.resultmsg.Results;
import com.server.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/educloud")
@RestController
public class RoleController {
    private @Autowired
    RoleMapper mapper;



    /***
     * 获取套餐列表
     * @return
     */
    @RequestMapping(value = "/roles",method = RequestMethod.GET)
    public Result getCombo(){
        try{
            List<Role> list = mapper.selectAll();
            return Results.successWithData(list,BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());

        }catch (Exception e){
            return Results.failureWithData(e,BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());

        }
    }

    @RequestMapping(value = "/roles", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Result addRole(Role role) {

        role.setCreateTime(Util.getCurrentTime());
        role.setUpdateTime(Util.getCurrentTime());
        int res = mapper.insert(role);
        try {
            if (res > 0) {//有此用户
                return Results.success(BaseEnums.OPERATION_SUCCESS.code(), BaseEnums.OPERATION_SUCCESS.desc());
            } else {//没有用户/账号密码错误
                return Results.failure(BaseEnums.OPERATION_FAILURE.code(), BaseEnums.OPERATION_FAILURE.desc());
            }

        } catch (Exception e) {
            return Results.failureWithData(e,BaseEnums.OPERATION_FAILURE.code(), BaseEnums.OPERATION_FAILURE.desc());

        }




    }

    @RequestMapping(value = "/roles", method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public Result updateRole(Role role){
        role.setUpdateTime(Util.getCurrentTime());
        int res = mapper.updateByPrimaryKeySelective(role);
        try {
            if (res > 0) {//有此用户
                return Results.success(BaseEnums.OPERATION_SUCCESS.code(), BaseEnums.OPERATION_SUCCESS.desc());
            } else {//没有用户/账号密码错误
                return Results.failure(BaseEnums.OPERATION_FAILURE.code(), BaseEnums.OPERATION_FAILURE.desc());
            }

        } catch (Exception e) {
            return Results.failureWithData(e,BaseEnums.OPERATION_FAILURE.code(), BaseEnums.OPERATION_FAILURE.desc());

        }
    }


    /**
     * 删除套餐
     * @param kcode
     * @return
     */
    @RequestMapping(value = "/roles/{kcode}",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    public Result delCombo(@PathVariable("kcode") String kcode){

        try {
            int res = mapper.deleteByKcode(kcode);
            if(res>0){
                return Results.success(BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());
            }else{
                return Results.failureWithData("删除失败",BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
            }
        }catch (Exception e){
            return Results.failureWithData(e,BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());

        }


    }


}

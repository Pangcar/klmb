package com.cms.tenant;

import com.auxiliary.Util;
import com.doman.Tenant;
import com.resultmsg.BaseEnums;
import com.resultmsg.Result;
import com.resultmsg.Results;
import com.server.TenantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/educloud")
@RestController
public class TenantController {


    @Autowired
    private TenantMapper mapper;


    /**
     * 获取租户列表
     * @return
     */
    @RequestMapping(value = "/tenants",method = RequestMethod.GET)
    public Result getTenants(){
        List<Tenant>  list =null;
        try {
           list = mapper.selectAll();
           return Results.successWithData(list,BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());

        }catch (Exception e){
            return Results.failureWithData(e,BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
        }
    }


    /**
     * 插入租户
     * @param tenant
     * @return
     */
    @RequestMapping(value = "/tenants",method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Result addTenant(Tenant tenant){
        System.out.println("插入租户");
        tenant.setCreateTime(Util.getCurrentTime());
        tenant.setUpdateTime(Util.getCurrentTime());

        int res = mapper.insert(tenant);
        if(res>0){
            return Results.success(BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());
        }else{
            return Results.failure(BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
        }
    }

    /**
     * 更新租户
     * @param tenant
     * @return
     */
    @RequestMapping(value = "/tenants",method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public Result updateTenant(Tenant tenant){
        System.out.println("更新租户");

        tenant.setUpdateTime(Util.getCurrentTime());
        int res = mapper.updateByPrimaryKeySelective(tenant);

        if(res>0){
            return Results.success(BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());
        }else{
            return Results.failure(BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
        }

    }


    /**
     * 删除租户
     * @param kcode 租户编码
     * @return
     */
    @RequestMapping(value = "/tenants/{kcode}",method = RequestMethod.DELETE)
    public  Result delTenant(@PathVariable("kcode") String kcode){

        int res  = mapper.deleteByPrimaryKey(kcode);

        if(res>0){
            return Results.success(BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());
        }else{
            return Results.failure(BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
        }

    }





}

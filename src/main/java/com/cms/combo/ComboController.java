package com.cms.combo;

import com.doman.Combo;
import com.resultmsg.BaseEnums;
import com.resultmsg.Result;
import com.resultmsg.Results;
import com.server.ComboMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/educloud")
@RestController
public class ComboController {

    @Autowired
    private ComboMapper mapper;

    /**
     * 创建套餐
     * @param combo
     * @return
     */
    @RequestMapping(value = "/combos",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public Result addCombo(Combo combo){
        try{
        int res = mapper.insert(combo);
            if(res>0){
                return Results.success(BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());
            }else{
                return Results.failure(BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
            }
        }catch (Exception e){
            return Results.failureWithData(e,BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
        }

    }

    /***
     * 获取套餐列表
     * @return
     */
    @RequestMapping(value = "/combos",method = RequestMethod.GET)
    public Result getCombo(){
        try{
            List<Combo> list = mapper.selectAll();
            return Results.successWithData(list,BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());

        }catch (Exception e){
            return Results.failureWithData(e,BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());

        }
    }

    /**
     * 修改套餐
     * @param combo
     * @return
     */
    @RequestMapping(value = "/combos",method = RequestMethod.PUT,produces = {"application/json;charset=UTF-8"})
    public Result updateCombo(Combo combo){
        try{
            int res = mapper.updateByPrimaryKeySelective(combo);

            if(res>0){
                return Results.success(BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());
            }else{
                return Results.failureWithData("更新失败",BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
            }
        }catch (Exception e){
            return Results.failureWithData(e,BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
        }
    }


    /**
     * 删除套餐
     * @param kcode
     * @return
     */
    @RequestMapping(value = "/combos/{kcode}",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
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

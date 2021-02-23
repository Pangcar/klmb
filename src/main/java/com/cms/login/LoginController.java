package com.cms.login;

import com.auxiliary.Util;
import com.doman.Consumer;
import com.doman.LoginFrom;
import com.resultmsg.BaseEnums;
import com.resultmsg.Result;
import com.resultmsg.Results;
import com.server.ConsumerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/educloud")
@RestController
public class LoginController {


    @Autowired
    private ConsumerMapper mapper;

    /**
     * 表名称
     */
    public static String dbname = "consumer";

    public LoginController() {

    }

    @PostConstruct  //加上该注解表明该方法会在bean初始化后调用，以替代构造函数无法从配置文件取值
    public void init() {

    }

    /**
     * 首页登录
     * @param loginFrom
     * @return
     */
    @PostMapping(value = "/login")
    public Map login( LoginFrom loginFrom) {

        HashMap<String, Object> response = new HashMap<>();
        HashMap<String, Object> responseData = new HashMap<>();
        List<Consumer> res = mapper.login(loginFrom);
        int res1 = res.size();
        if(res1>0){//有此用户
            responseData.put("token", loginFrom.getUsername()+"-token");
            response.put("code", 20000);
            response.put("msg", "登录成功");
            response.put("data", responseData);
        }else{//没有用户/账号密码错误
            responseData.put("token", -1);
            response.put("code", 10000);
            response.put("msg", "登录失败");
            response.put("data", responseData);
        }
        return response;
    }


    /**
     * 获取管理员用户列表
     * @param organize
     * @return
     */
    @RequestMapping(value = "/consumers/organize/{oType}",method = RequestMethod.GET)
    public Result getConsumers(@PathVariable("oType") String organize ){
        HashMap<String, Object> response = new HashMap<>();
        HashMap<String, Object> responseData = new HashMap<>();
        List<Consumer> list = mapper.getConsumer(organize);
        return Results.successWithData(list, BaseEnums.SUCCESS.code(),BaseEnums.SUCCESS.desc());
    }

    /**
     * 创建管理员用户
     * @param consumer
     * @return
     */
    @RequestMapping(value = "/consumers",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public Result addConsumer(Consumer consumer){

        //未实现功能：创建用户时去重

        HashMap<String, Object> response = new HashMap<>();
        HashMap<String, Object> responseData = new HashMap<>();

        consumer.setCreateTime(Util.getCurrentTime());
        consumer.setUpdateTime(Util.getCurrentTime());
        consumer.setAlive(1);

        int res  = mapper.insert(consumer);
        if(res>0){//有此用户
            return Results.success(BaseEnums.OPERATION_SUCCESS.code(),BaseEnums.OPERATION_SUCCESS.desc());
        }else{//没有用户/账号密码错误
            return Results.success(BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
        }


    }

    public Result updateConsumer(){
        return Results.success(BaseEnums.OPERATION_FAILURE.code(),BaseEnums.OPERATION_FAILURE.desc());
    }

    @CrossOrigin
    @GetMapping(value = "/info")
    public Map info() {
        HashMap<String, Object> responseInfo = new HashMap<>();
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("roles", "admin");
        responseData.put("name", "Super admin");
        responseData.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        responseInfo.put("code", 20000);
        responseInfo.put("msg", "登录成功");
        responseInfo.put("data", responseData);
        return responseInfo;
    }

    @RequestMapping(value = "/test")
    public Map test() {
        System.out.println("port=" +   "  " +  "  " );
//        return ResultUtil.success("version-1.0.1");
//        return ResultUtil.Success("version-1.0.2","");//修改了访问跟目录：61
        HashMap<String, Object> responseInfo = new HashMap<>();
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("version", "1.0.1");
        responseInfo.put("code", 20000);
        responseInfo.put("msg", "登录成功");
        responseInfo.put("data", responseData);
        return responseInfo;
    }

}

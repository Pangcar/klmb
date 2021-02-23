package com.main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/luckdraw")
@RestController
public class UserController {

    @RequestMapping(value = "/getUserName", method = RequestMethod.GET)
    public String getUserByGet(@RequestParam(value = "username") String name) {

        return "hello " + name;
    }


    @RequestMapping(value = "/getUserByPost", method = RequestMethod.POST)
    public String getUserByPost(@RequestParam(value = "username") String name) {

        return "hello " + name + " by post";
    }
}


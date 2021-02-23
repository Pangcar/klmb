package com.main;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
class DemoCxyApplicationTests {

    @RequestMapping("/hello")
    public String contextLoads() {

        return "hello";
    }


}

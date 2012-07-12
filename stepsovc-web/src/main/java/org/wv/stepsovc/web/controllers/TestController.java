package org.wv.stepsovc.web.controllers;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class TestController {


    private Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping(value = "/test")
    @ResponseBody
    public String loadHelloWorld() {
        logger.info("***********Test PAGE- Log  configured in Info  Mode*************** ");
        return " Testing - StepOVC Running @" + new Date();
    }
}

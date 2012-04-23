package org.wv.stepsovc.web.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class TestController {

    @RequestMapping(value = "/test")
    @ResponseBody
    public String loadHelloWorld() {
        return " Testing - StepOVC Running @" + new Date();
    }
}

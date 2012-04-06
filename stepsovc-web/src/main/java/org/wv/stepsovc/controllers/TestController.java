package org.wv.stepsovc.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class TestController {

    @RequestMapping(value = "/test")
    @ResponseBody
    public String loadHelloWorld() {
        return " Test 2 - StepOVC Running @" + new Date();
    }
}

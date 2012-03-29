package org.wv.stepsovc.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    @RequestMapping(value="/test")
    @ResponseBody
    public String loadHelloWorld(){
        return "StepOVC Running";
    }
}

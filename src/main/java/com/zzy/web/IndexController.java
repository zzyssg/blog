package com.zzy.web;

import com.zzy.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){

        return "index";
    }

    @GetMapping("/blog")
    public String blog(){

        return "index";
    }


}

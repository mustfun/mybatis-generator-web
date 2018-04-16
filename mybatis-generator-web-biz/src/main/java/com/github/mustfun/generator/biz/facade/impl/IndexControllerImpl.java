package com.github.mustfun.generator.biz.facade.impl;

import com.github.mustfun.generator.biz.facade.IndexController;
import com.github.mustfun.generator.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/5/4
 * @since 1.0
 */
@Controller
@RequestMapping("/")
public class IndexControllerImpl implements IndexController {


    @Override
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index() {
        return "core/index";
    }


    @RequestMapping(value = "/dbList",method = RequestMethod.GET)
    public String dbList() {
        return "core/dbList";
    }

    @RequestMapping(value = "/modal",method = RequestMethod.GET)
    public String modal() {
        return "core/ui-modals";
    }


}

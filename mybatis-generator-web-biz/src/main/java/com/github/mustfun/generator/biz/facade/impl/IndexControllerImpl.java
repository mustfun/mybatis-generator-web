package com.github.mustfun.generator.biz.facade.impl;

import com.alibaba.fastjson.JSON;
import com.github.mustfun.generator.biz.facade.IndexController;
import com.github.mustfun.generator.model.constants.FileConstants;
import com.github.mustfun.generator.model.po.DbConfigPo;
import com.github.mustfun.generator.service.CityService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xnio.IoUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/5/4
 * @since 1.0
 */
@Controller
@RequestMapping("/")
public class IndexControllerImpl implements IndexController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexControllerImpl.class);

    @Override
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index() {
        return "core/index";
    }


    @RequestMapping(value = "/dbList",method = RequestMethod.GET)
    public String dbList(Model model) {
        try {
            List<String> strings = IOUtils.readLines(new FileInputStream(FileConstants.TEMP_DB_CONFIG_DB), "UTF-8");
            List<DbConfigPo> list = new ArrayList<>();
            for (String string : strings) {
                DbConfigPo dbConfigPos = JSON.parseObject(string, DbConfigPo.class);
                list.add(dbConfigPos);
            }
            model.addAttribute("dbConfigList", list);
        } catch (IOException e) {
            LOG.error("{}",e);
        }
        return "core/dbList";
    }

    @RequestMapping(value = "/tableList",method = RequestMethod.GET)
    public String tableList(Model model,@RequestParam("key") String key) {
        LOG.info(key);
        DbConfigPo dbConfigPo = JSON.parseObject(key, DbConfigPo.class);
        return "core/tableList";
    }

    @RequestMapping(value = "/modal",method = RequestMethod.GET)
    public String modal() {
        return "core/ui-modals";
    }


}

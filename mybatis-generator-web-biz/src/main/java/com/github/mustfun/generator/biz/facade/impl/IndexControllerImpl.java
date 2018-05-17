package com.github.mustfun.generator.biz.facade.impl;

import com.github.mustfun.generator.biz.facade.IndexController;
import com.github.mustfun.generator.model.enums.DbTypeEnums;
import com.github.mustfun.generator.model.enums.VmTypeEnums;
import com.github.mustfun.generator.model.po.DbSourcePo;
import com.github.mustfun.generator.model.po.LocalTable;
import com.github.mustfun.generator.model.po.Template;
import com.github.mustfun.generator.service.DbSourceService;
import com.github.mustfun.generator.service.ExtApiService;
import com.github.mustfun.generator.service.TemplateService;
import com.github.mustfun.generator.support.handler.ConnectionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
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

    @Autowired
    private ExtApiService extApiService;

    @Autowired
    private DbSourceService dbService;

    @Autowired
    private TemplateService templateService;

    @Override
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index() {
        return "core/index";
    }


    @RequestMapping(value = "/dbList",method = RequestMethod.GET)
    public String dbList(Model model) {
        //List<String> strings = IOUtils.readLines(new FileInputStream(FileConstants.TEMP_DB_CONFIG_DB), "UTF-8");
        //从数据库中读取
        List<DbSourcePo> dbSourcePos = dbService.queryList();
        for (DbSourcePo dbSourcePo : dbSourcePos) {
            extApiService.initDb(dbSourcePo);
        }
        model.addAttribute("dbConfigList", dbSourcePos);
        return "core/dbList";
    }

    @RequestMapping(value = "/templateList",method = RequestMethod.GET)
    public String templateList(Model model) {
        //从数据库中读取
        List<Template> templates = templateService.queryList();
        model.addAttribute("templateList", templates);
        model.addAttribute("vmTypeList", VmTypeEnums.values());
        model.addAttribute("dbTypeList", DbTypeEnums.values());
        return "core/templateList";
    }

    @RequestMapping(value = "/tableList",method = RequestMethod.GET)
    public String tableList(Model model,@RequestParam("key") Integer key) {
        LOG.info("拿到的数据库ID是{}",key);
        //尝试存进数据库中
        if (key==null){
            throw new RuntimeException("参数不合法");
        }
        DbSourcePo dbConfigPo = dbService.getOne(key);
        Connection connection = ConnectionHolder.getConnection(dbConfigPo.getDbAddress()+dbConfigPo.getDbName());
        List<LocalTable> tables = extApiService.getTables(connection);
        model.addAttribute("tables", tables);
        model.addAttribute("key", dbConfigPo.getDbAddress()+dbConfigPo.getDbName());
        return "core/tableList";
    }

    @RequestMapping(value = "/modal",method = RequestMethod.GET)
    public String modal() {
        return "core/ui-modals";
    }


}

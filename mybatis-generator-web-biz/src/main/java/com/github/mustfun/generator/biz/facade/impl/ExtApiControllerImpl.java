package com.github.mustfun.generator.biz.facade.impl;

import com.github.mustfun.generator.model.po.DbConfigPo;
import com.github.mustfun.generator.model.po.DbSourcePo;
import com.github.mustfun.generator.model.po.Template;
import com.github.mustfun.generator.service.ExtApiService;
import com.github.mustfun.generator.support.result.BaseResult;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/5/4
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
public class ExtApiControllerImpl {

    private static final Logger LOG = LoggerFactory.getLogger(ExtApiControllerImpl.class);
    @Autowired
    private ExtApiService extApiService;


    @RequestMapping(value = "save_db_config",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<Long> saveDbConfig(DbSourcePo dbSourcePo) {
        return extApiService.saveDbConfig(dbSourcePo);
    }

    @RequestMapping(value = "save_template",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<Long> saveTemplate(Template dbSourcePo) {
        return extApiService.saveTemplate(dbSourcePo);
    }

    @RequestMapping(value = "delete_template/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<Boolean> deleteTemplate(@PathVariable("id") Integer dbSourcePo) {
        return extApiService.deleteTemplate(dbSourcePo);
    }

    @RequestMapping(value = "update_template/{id}",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<Boolean> updateTemplate(Template template) {
        return extApiService.updateTemplate(template);
    }


    @RequestMapping(value = "generate_code",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void generateCode(@RequestParam("tableName") List<String> tableNames,@RequestParam("packageName") String packageName,
                               @RequestParam("address")String address,@RequestParam("vmList")List<Integer> vmList,
                             HttpServletResponse response) {
        byte[] data = extApiService.generateCode(tableNames,packageName, address,vmList);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"generator-web.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        try {
            IOUtils.write(data, response.getOutputStream());
        } catch (IOException e) {
            LOG.error("下载代码异常,{}",e);
        }
    }

}

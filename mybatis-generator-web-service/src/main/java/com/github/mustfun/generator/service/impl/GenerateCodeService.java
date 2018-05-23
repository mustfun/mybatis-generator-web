package com.github.mustfun.generator.service.impl;

import com.github.mustfun.generator.model.enums.VmTypeEnums;
import com.github.mustfun.generator.model.po.LocalColumn;
import com.github.mustfun.generator.model.po.LocalTable;
import com.github.mustfun.generator.support.handler.SpringContextHolder;
import com.github.mustfun.generator.support.util.DateUtils;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/4/13
 * @since 1.0
 */

public class GenerateCodeService {

    private static final Logger LOG = LoggerFactory.getLogger(GenerateCodeService.class);

    private static ConcurrentHashMap<Integer, Integer> templateGenerateTimeMap = new ConcurrentHashMap<>(10);

    /**
     * 生成代码
     */
    public static void generatorCode(LocalTable table,
                                     List<LocalColumn> columns, ZipOutputStream zip, String packageName, List<Integer> vmList) {
        //配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;
        //表名转换成Java类名
        String className = tableToJava(table.getTableName(), config.getString("tablePrefix"));
        table.setClassName(className);
        table.setClassLittleName(StringUtils.uncapitalize(className));

        //列信息
        List<LocalColumn> columnsList = new ArrayList<>();
        for(LocalColumn column : columns){

            //列名转换成Java属性名
            String attrName = columnToJava(column.getColumnName());
            column.setAttrName(attrName);
            column.setAttrLittleName(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(column.getDataType().toUpperCase(), "String" );
            column.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal" )) {
                hasBigDecimal = true;
            }
            //BIGINT处理一下
            if (column.getDataType().toUpperCase().equalsIgnoreCase("BITINT UNSIGNED")){
                column.setDataType("BIGINT");
            }
            columnsList.add(column);
        }
        table.setColumnList(columnsList);

        //没主键，则第一个字段为主键
        if (table.getPk() == null) {
            table.setPk(table.getColumnList().get(0));
        }

        //设置velocity资源加载器
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
        engine.setProperty(Velocity.RESOURCE_LOADER, "string");
        engine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
        engine.addProperty("string.resource.loader.repository.static", "false");
        //  engine.addProperty("string.resource.loader.modificationCheckInterval", "1");
        engine.init();
        StringResourceRepository repo = (StringResourceRepository) engine.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);

        String mainPath = config.getString("mainPath" );
        mainPath = StringUtils.isBlank(mainPath) ? "com.generator" : mainPath;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", table.getTableName());
        map.put("comment", table.getComment());
        map.put("pk", table.getPk());
        map.put("className", table.getClassName());
        map.put("classLittleName", table.getClassLittleName());
        map.put("pathName", table.getClassLittleName().toLowerCase());
        map.put("columns", table.getColumnList());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("mainPath", mainPath);
        map.put("package", packageName);
        map.put("author", config.getString("author" ));
        map.put("email", config.getString("email" ));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        for (Integer templateId : vmList) {
            //取出模板
            TemplateServiceImpl templateService = SpringContextHolder.getBean(TemplateServiceImpl.class);
            com.github.mustfun.generator.model.po.Template template = templateService.getOne(templateId);

            if(!checkNeedGenerate(template.getVmType())){
                continue;
            }

            //渲染模板
            try (StringWriter sw = new StringWriter()) {

                repo.putStringResource(template.getTepName()+"_"+template.getId(), template.getTepContent());
                Template tpl = engine.getTemplate(template.getTepName()+"_"+template.getId(),"UTF-8");
                tpl.merge(context, sw);

                    if (packageName==null||StringUtils.isEmpty(packageName)){
                        packageName = config.getString("package");
                    }
                    //添加到zip
                    zip.putNextEntry(new ZipEntry(Objects.requireNonNull(getFileName(template.getVmType(), table.getClassName(), packageName))));
                    IOUtils.write(sw.toString(), zip, "UTF-8");
                    zip.closeEntry();

            } catch (IOException e) {
                LOG.error("渲染模板发生异常{}",e);
                throw new RuntimeException("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }
    }

    private  static boolean checkNeedGenerate(Integer template) {
        if (template.equals(VmTypeEnums.RESULT.getCode())){
            Integer integer = templateGenerateTimeMap.get(template);
            if (integer==null){
                templateGenerateTimeMap.put(template,1);
            }else if(integer>0){
                return false;
            }
        }
        return true;
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "" );
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator/generator-config.properties" );
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(Integer template, String className, String packageName) {
        String packagePath = "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }
        if (template.equals(VmTypeEnums.RESULT.getCode())) {
            return packagePath +"model" + File.separator + "Result.java";
        }

        if (template.equals(VmTypeEnums.MODEL_PO.getCode())) {
            return packagePath +"model"+ File.separator + "po" + File.separator + className + "Po.java";
        }

        if (template.equals(VmTypeEnums.MODEL_BO.getCode())) {
            return packagePath +"model"+ File.separator + "bo" + File.separator + className + "Bo.java";
        }

        if (template.equals(VmTypeEnums.MODEL_REQ.getCode())) {
            return packagePath +"model"+ File.separator + "req" + File.separator + className + "Req.java";
        }

        if (template.equals(VmTypeEnums.MODEL_RESP.getCode())) {
            return packagePath +"model"+ File.separator + "resp" + File.separator + className + "Resp.java";
        }


        if (template.equals(VmTypeEnums.DAO.getCode())) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        if (template.equals(VmTypeEnums.SERVICE.getCode())) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.equals(VmTypeEnums.SERVICE_IMPL.getCode())) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.equals(VmTypeEnums.CONTROLLER.getCode())) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.equals(VmTypeEnums.MAPPER.getCode())) {
            return "mybatis" +File.separator + "mappers" + File.separator + className + "Dao.xml";
        }

        return null;
    }
}

package com.github.mustfun.generator.service.impl;

import com.github.mustfun.generator.model.po.LocalColumn;
import com.github.mustfun.generator.model.po.LocalTable;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
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

    public static List<String> getTemplates(){
        List<String> templates = new ArrayList<>();
        templates.add("temp/Po.java.vm");
        templates.add("temp/Dao.java.vm");
        templates.add("temp/Dao.xml.vm");
        templates.add("temp/Service.java.vm");
        templates.add("temp/ServiceImpl.java.vm");
        templates.add("temp/Controller.java.vm");
        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(LocalTable table,
                                     List<LocalColumn> columns, ZipOutputStream zip) {
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
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
        Velocity.init(prop);
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
        map.put("package", config.getString("package" ));
        map.put("moduleName", config.getString("moduleName" ));
        map.put("author", config.getString("author" ));
        map.put("email", config.getString("email" ));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            try (StringWriter sw = new StringWriter()) {
                Template tpl = Velocity.getTemplate(template, "UTF-8");
                tpl.merge(context, sw);


                    //添加到zip
                    zip.putNextEntry(new ZipEntry(getFileName(template, table.getClassName(), config.getString("package"), config.getString("moduleName"))));
                    IOUtils.write(sw.toString(), zip, "UTF-8");
                    zip.closeEntry();

            } catch (IOException e) {
                LOG.error("渲染模板发生异常{}",e);
                throw new RuntimeException("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }
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
    public static String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains("Po.java.vm" )) {
            return packagePath + "po" + File.separator + className + "Po.java";
        }

        if (template.contains("Dao.java.vm" )) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        if (template.contains("Repository.java.vm" )) {
            return packagePath + "repository" + File.separator + className + "Repository.java";
        }

        if (template.contains("Service.java.vm" )) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm" )) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm" )) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Dao.xml.vm" )) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Dao.xml";
        }

        return null;
    }
}

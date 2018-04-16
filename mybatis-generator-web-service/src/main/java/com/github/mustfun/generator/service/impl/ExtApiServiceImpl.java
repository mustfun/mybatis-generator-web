package com.github.mustfun.generator.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.mustfun.generator.model.constants.FileConstants;
import com.github.mustfun.generator.model.po.DbConfigPo;
import com.github.mustfun.generator.model.po.LocalTable;
import com.github.mustfun.generator.service.ExtApiService;
import com.github.mustfun.generator.support.handler.ConnectionHolder;
import com.github.mustfun.generator.support.result.BaseResult;
import com.github.mustfun.generator.support.util.DbUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/4/13
 * @since 1.0
 */
@Service
public class ExtApiServiceImpl implements ExtApiService {

    private static final Logger LOG = LoggerFactory.getLogger(ExtApiServiceImpl.class);


    @Override
    public BaseResult<Long> saveDbConfig(DbConfigPo configPo) {
        BaseResult<Long> baseResult = new BaseResult<>();
        DbUtil dbUtil = new DbUtil(configPo.getAddress(), configPo.getDbName(), configPo.getUserName(), configPo.getPassword());
        Connection connection = dbUtil.getConnection();
        if (connection==null){
            baseResult.setStatus(-2);
            baseResult.setMessage("数据库连接失败");
            return baseResult;
        }
        List<String> columnNameList = new ArrayList<>();
        getTables(connection);
        try {
            DatabaseMetaData metaData = connection.getMetaData();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionHolder.addConnection(configPo.getAddress(),connection);
        //保存到文件中
        saveToLocalFile(configPo);
        baseResult.setStatus(1);
        baseResult.setMessage("数据库添加成功");
        return baseResult;
    }

    /**
     * 保存到文件中
     * @param configPo
     */
    private void saveToLocalFile(DbConfigPo configPo) {
        try {
            String s = JSON.toJSONString(configPo);
            List<String> list = new ArrayList<>();
            list.add(s);
            try (OutputStream os = new FileOutputStream(FileConstants.TEMP_DB_CONFIG_DB, true)) {
                IOUtils.writeLines(list, null, os, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<LocalTable> getTables(Connection connection) {
        DatabaseMetaData dbMetData;
        List<LocalTable> localTables = new ArrayList<>();
        try {
            dbMetData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbMetData.getTables(null, null, "%", types);
            while (rs.next()) {
                LocalTable localTable = new LocalTable();
                String tableName = rs.getString("TABLE_NAME");
                LOG.info(tableName);
                localTable.setName(tableName);
                getColumns(dbMetData,tableName);
                localTables.add(localTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return localTables;
    }

    /**
     * 创建连接
     * @param configPo
     */
    @Override
    public void initDb(DbConfigPo configPo) {
        if (ConnectionHolder.getConnection(configPo.getAddress())!=null){
            return ;
        }
        DbUtil dbUtil = new DbUtil(configPo.getAddress(), configPo.getDbName(), configPo.getUserName(), configPo.getPassword());
        Connection connection = dbUtil.getConnection();
        if (connection==null){
            return ;
        }
        ConnectionHolder.addConnection(configPo.getAddress(),connection);
    }

    @Override
    public BaseResult<Long> generateCode(String tableNames) {
        BaseResult<Long> baseResult = new BaseResult<>();
        String[] split = tableNames.trim().split(",");
        for (String s : split) {
            LOG.info("需要生成代码的表{}",s);
        }
        return baseResult;
    }

    private void getColumns(DatabaseMetaData meta, String tableName) throws SQLException {
        ResultSet survey = meta.getColumns(null, null, tableName, null);
        while (survey.next()) {
            String columnName = survey.getString("COLUMN_NAME");
            LOG.info("column name=" + columnName);
            String columnType = survey.getString("TYPE_NAME");
            LOG.info("type:" + columnType);
            int size = survey.getInt("COLUMN_SIZE");
            LOG.info("size:" + size);
            int nullable = survey.getInt("NULLABLE");
            if (nullable == DatabaseMetaData.columnNullable) {
                LOG.info("nullable true");
            } else {
                LOG.info("nullable false");
            }
            int position = survey.getInt("ORDINAL_POSITION");
            LOG.info("position:" + position);
        }
    }
}

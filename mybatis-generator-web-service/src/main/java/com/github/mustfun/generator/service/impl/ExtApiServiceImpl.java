package com.github.mustfun.generator.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.mustfun.generator.model.constants.FileConstants;
import com.github.mustfun.generator.model.po.DbConfigPo;
import com.github.mustfun.generator.model.po.LocalColumn;
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
    public BaseResult<Long> generateCode(String tableNames, String address) {
        BaseResult<Long> baseResult = new BaseResult<>();
        String[] split = tableNames.trim().split(",");
        try {
            Connection connection = ConnectionHolder.getConnection(address);
            for (String s : split) {
                LOG.info("需要生成代码的表{}",s);
                LocalTable columns = getColumns(connection.getMetaData(), s);
                //生成代码啦，替换模板
                generateCodeUseTemplate(columns);
            }
        } catch (SQLException e) {
            LOG.error("生成表发生异常{}",e);
        }
        return baseResult;
    }

    /**
     * 用模板生成代码
     * @param columns
     */
    private void generateCodeUseTemplate(LocalTable columns) {

    }

    private LocalTable getColumns(DatabaseMetaData meta, String tableName) throws SQLException {
        LocalTable localTable = new LocalTable();
        List<LocalColumn> localColumns = new ArrayList<>();
        ResultSet survey = meta.getColumns(null, null, tableName, null);
        while (survey.next()) {
            LocalColumn localColumn = new LocalColumn();
            String columnName = survey.getString("COLUMN_NAME");
            localColumn.setName(columnName);
            String columnType = survey.getString("TYPE_NAME");
            localColumn.setType(columnType);
            int size = survey.getInt("COLUMN_SIZE");
            localColumn.setSize(size);
            int nullable = survey.getInt("NULLABLE");
            if (nullable == DatabaseMetaData.columnNullable) {
                localColumn.setNullable(true);
            } else {
                localColumn.setNullable(false);
            }
            int position = survey.getInt("ORDINAL_POSITION");
            localColumn.setPosition(position);
            localColumns.add(localColumn);
        }
        localTable.setName(tableName);
        localTable.setColumnList(localColumns);
        return localTable;
    }
}

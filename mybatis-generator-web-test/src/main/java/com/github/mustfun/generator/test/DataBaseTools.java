package com.github.mustfun.generator.test;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/22
 * @since 1.0
 */
public class DataBaseTools {

    //囧，也可以直接使用命令行
    // Java -cp druid-0.2.23.jar com.alibaba.druid.filter.config.ConfigTools xxxxxx
    @Test
    public void encodePwd(){
        try {
            String root = ConfigTools.encrypt("root");
            System.out.println(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

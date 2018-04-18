package com.kashuo.kcp;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhuenjun on 20170821
 */
public class RPCCodeGeneration {

    private static String rpcServiceOutPath = "C:/Legend/Jianhua/kcp/kcp-api/src/main/java/com/kashuo/kcp/api/service";


    private static String rpcServiceImplOutPath = "C:/Legend/Jianhua/kcp/kcp-rpc-server/src/main/java/com/kashuo/kcp/rpc";

    private static String serviceImplOutPath = "C:/Legend/Jianhua/kcp/kcp-core/src/main/java/com/kashuo/kcp/core/service";

    private static String daoOutPath = "C:/Legend/Jianhua/kcp/kcp-dao/src/main/java/com/kashuo/kcp/dao";

    private static String daoMainOutPath = "C:/Legend/Jianhua/kcp/kcp-api/src/main/java/com/kashuo/kcp/api/domain";

    private static String author = "Peng Jianhua";

    private static String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    public static void main(String[] args) {

//        build(BasBank.class);
    }

    public static void build(Class c) {
        String packName = c.getPackage().getName();
        String property = System.getProperty("java.class.path");

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        ve.init();

        //    buildTpl("codeTpl/dao.vm", c, daoOutPath, ve, "Mapper.java");
        buildTpl("codeTpl/domain.vm", c, daoMainOutPath, ve, "Api.java");
        buildTpl("codeTpl/Service.vm", c, rpcServiceOutPath, ve, "Service.java");
        buildTpl("codeTpl/ServiceRpc.vm", c, rpcServiceImplOutPath, ve, "Rpc.java");
        buildTpl("codeTpl/ServiceImpl.vm", c, serviceImplOutPath, ve, "Service.java");
    }

    private static void buildTpl(String tpl, Class c, String path, VelocityEngine ve, String out) {
        String rootPath = "";

        Template t = ve.getTemplate(tpl);
        VelocityContext ctx = new VelocityContext();
        ctx.put("className", c.getSimpleName());
        //首字母小写
        String firstCodeLowerCaseClassName = c.getSimpleName().substring(0, 1).toLowerCase() + c.getSimpleName().substring(1);
        ctx.put("firstCodeLowerCaseClassName", firstCodeLowerCaseClassName);
        ctx.put("author", author);
        ctx.put("time", time);

        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);
        File file;
        if (tpl.equals("codeTpl/Service.vm")) {
            file = new File(path + "/I" + c.getSimpleName() + out);
        } else {
            file = new File(path + "/" + c.getSimpleName() + out);
        }

        if (file.exists()) {
            System.out.println("文件已存在");
        } else {
            try {
                FileUtils.writeStringToFile(file, sw.toString(), "UTF-8");
                System.out.println("..生成成功:->" + c.getSimpleName() + "->" + path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

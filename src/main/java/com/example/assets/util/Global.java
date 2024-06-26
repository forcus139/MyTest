//package com.example.assets.util;
//
///**
// * TODO
// *
// * @author GengTianMing
// * @since 2023/03/07 08:30
// **/
//
//
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.ckfinder.connector.ServletContextFactory;
//import com.google.common.collect.Maps;
//import com.thinkgem.jeesite.common.utils.PropertiesLoader;
//import org.springframework.core.io.DefaultResourceLoader;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
///**
// *
// * @ClassName: Global
// * @Description: TODO
// * @author cb
// * @date 2018年5月16日 上午8:59:24
// */
//public class Global {
//
//    private Global() {}
//
//    /**
//     * 当前对象实例
//     */
//    private static Global global = null;
//
//    /**
//     * 当前对象实例
//     */
//    public static final String TOKEN = "61e32fa9cff8517699bc4ffbcf6daea9c03f55f7e665faf847f02e5399cb3d94";
//
//    /**
//     * 静态工厂方法 获取当前对象实例 多线程安全单例模式(使用双重同步锁)
//     */
//
//    public static synchronized Global getInstance() {
//
//        if (global == null) {
//            synchronized (Global.class) {
//                if (global == null)
//                    global = new Global();
//            }
//        }
//        return global;
//    }
//
//    /**
//     * 保存全局属性值
//     */
//    private static Map<String, String> map = Maps.newHashMap();
//
//    /**
//     * 属性文件加载对象
//     */
//    private static PropertiesLoader loader = new PropertiesLoader("example.properties");
//
//    /**
//     * 显示/隐藏
//     */
//    public static final String SHOW = "1";
//    public static final String HIDE = "0";
//
//    /**
//     * 是/否
//     */
//    public static final String YES = "1";
//    public static final String NO = "0";
//
//    /**
//     * 对/错
//     */
//    public static final String TRUE = "true";
//    public static final String FALSE = "false";
//
//    /**
//     * 上传文件基础虚拟路径
//     */
//    public static final String USERFILES_BASE_URL = "/userfiles/";
//
//    /**
//     * 获取配置
//     *
//     * @see ${fns:getConfig('adminPath')}
//     */
//    public static String getConfig(String key) {
//        String value = map.get(key);
//        if (value == null) {
//            value = loader.getProperty(key);
//            map.put(key, value != null ? value : StringUtils.EMPTY);
//        }
//        return value;
//    }
//    /**
//     * 获取邮箱用户名
//     */
//    public static String getEmailName(){
//        return getConfig("username");
//    }
//    /**
//     * 获取邮箱密码
//     */
//    public static String getEmailPsw(){
//        return getConfig("password");
//    }
//    /**
//     * 获取管理端根路径
//     */
//    public static String getAdminPath() {
//        return getConfig("adminPath");
//    }
//
//    /**
//     * 获取前端根路径
//     */
//    public static String getFrontPath() {
//        return getConfig("frontPath");
//    }
//
//    /**
//     * 获取URL后缀
//     */
//    public static String getUrlSuffix() {
//        return getConfig("urlSuffix");
//    }
//
//    /**
//     * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
//     */
//    public static Boolean isDemoMode() {
//        String dm = getConfig("demoMode");
//        return "true".equals(dm) || "1".equals(dm);
//    }
//
//    /**
//     * 在修改系统用户和角色时是否同步到Activiti
//     */
//    public static Boolean isSynActivitiIndetity() {
//        String dm = getConfig("activiti.isSynActivitiIndetity");
//        return "true".equals(dm) || "1".equals(dm);
//    }
//
//    /**
//     * 页面获取常量
//     *
//     * @see ${fns:getConst('YES')}
//     */
//    public static Object getConst(String field) {
//        try {
//            return Global.class.getField(field).get(null);
//        } catch (Exception e) {
//            // 异常代表无配置，这里什么也不做
//        }
//        return null;
//    }
//
//    /**
//     * 获取上传文件的根目录
//     *
//     * @return
//     */
//    public static String getUserfilesBaseDir() {
//        String dir = getConfig("userfiles.basedir");
//        if (StringUtils.isBlank(dir)) {
//            try {
//                dir = ServletContextFactory.getServletContext().getRealPath("/");
//            } catch (Exception e) {
//                return "";
//            }
//        }
//        if (!dir.endsWith("/")) {
//            dir += "/";
//        }
//        // System.out.println("userfiles.basedir: " + dir);
//        return dir;
//    }
//
//    /**
//     * 获取工程路径
//     *
//     * @return
//     */
//    public static String getProjectPath() {
//        // 如果配置了工程路径，则直接返回，否则自动获取。
//        String projectPath = Global.getConfig("projectPath");
//        if (StringUtils.isNotBlank(projectPath)) {
//            return projectPath;
//        }
//        try {
//            File file = new DefaultResourceLoader().getResource("").getFile();
//            if (file != null) {
//                while (true) {
//                    File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
//                    if (f == null || f.exists()) {
//                        break;
//                    }
//                    if (file.getParentFile() != null) {
//                        file = file.getParentFile();
//                    } else {
//                        break;
//                    }
//                }
//                projectPath = file.toString();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return projectPath;
//    }
//
//}
//

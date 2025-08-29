package com.jinhongs.eternity.genernity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.model.ClassAnnotationAttributes;

import java.sql.Types;
import java.util.HashMap;
import java.util.Properties;

// 仅作为mybatis-plus代码生成使用，无其他作用
public class Main {

    private static String getUrlFromProperties() {
        try {
            Properties props = new Properties();
            props.load(Main.class.getClassLoader().getResourceAsStream("application-dev.properties"));
            return props.getProperty("dev.admin.datasource.url");
        } catch (Exception e) {
            return "root"; // 默认值
        }
    }
    private static String getUsernameFromProperties() {
        try {
            Properties props = new Properties();
            props.load(Main.class.getClassLoader().getResourceAsStream("application-dev.properties"));
            return props.getProperty("dev.admin.datasource.username");
        } catch (Exception e) {
            return "root"; // 默认值
        }
    }
    private static String getPasswordFromProperties() {
        try {
            Properties props = new Properties();
            props.load(Main.class.getClassLoader().getResourceAsStream("application-dev.properties"));
            return props.getProperty("dev.admin.datasource.password");
        } catch (Exception e) {
            return "root"; // 默认值
        }
    }

    // 一次性生成全部代码，需要单独生成model或mapper代码时，可以再写个新方法
    public static void allGenerator(){
        // 项目根路径
        String projectPath = System.getProperty("user.dir");

        FastAutoGenerator.create(
                        getUrlFromProperties(),
                        getUsernameFromProperties(),
                        getPasswordFromProperties()
                )
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("YangJinHong8888")
                            // 指定各模块的代码输出路径
                            .outputDir(projectPath) // 默认输出路径(/eternity-server)
                            .disableOpenDir()       // 禁止自动打开输出目录
                            .enableSpringdoc();       // 开启 openApi3 注解
                })
                // 数据库配置
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);
                }))
                // 包配置
                .packageConfig(builder -> builder.parent("com.jinhongs")                                      // 设置父包名
                        .entity("eternity.model.entity")                            // Entity 包名（生成到 eternity-model 模块）
                        .serviceImpl("eternity.dao.mysql.repository")               // ServiceImpl 包名（生成到 eternity-service 模块）
                        .mapper("eternity.dao.mysql.mapper")                        // Mapper 包名（生成到 eternity-dao 模块）
                        .xml("eternity.dao.mysql.mapper.xml")                       // Mapper xml包名（生成到 eternity-dao 模块）
                        .controller("eternity.admin.web.controller")                // Controller 包名（生成到 eternity-web 模块） 目前不需要生成Controller
                        .pathInfo(new HashMap<>() {{
                            // Entity → model模块
                            put(OutputFile.entity,
                                    System.getProperty("user.dir") + "/eternity-model/src/main/java/com/jinhongs/eternity/model/entity");
                            // Mapper接口 → dao模块
                            put(OutputFile.mapper,
                                    System.getProperty("user.dir") + "/eternity-dao/src/main/java/com/jinhongs/eternity/dao/mysql/mapper");
                            // XML文件 → dao模块资源目录
                            put(OutputFile.xml,
                                    System.getProperty("user.dir") + "/eternity-dao/src/main/java/com/jinhongs/eternity/dao/mysql//mapper/xml");
                            // 解耦 IService 模块，不再推荐使用（避免业务层混淆乱用），serviceImpl通过模板改造迁移至 CrudRepository 类
                            // repository 负责dao与service完全解耦service内的各种QueryWrapper迁移到repository，service只处理业务逻辑
                            put(OutputFile.serviceImpl,
                                    System.getProperty("user.dir") + "/eternity-dao/src/main/java/com/jinhongs/eternity/dao/mysql/repository");
                            // Controller → web模块
                            put(OutputFile.controller,
                                    System.getProperty("user.dir") + "eternity-web/eternity-admin-web/src/main/java/com/jinhongs/eternity/admin/web/controller");
                        }}))
                // 策略配置
                .strategyConfig(builder -> builder.addInclude("user_info", "user_auth", "role", "permission", "user_role", "role_permission", "articles"
                                , "tags", "article_tags", "comments"
                                , "article_likes", "article_favorites", "categories", "article_categories")  // 需生成的表名
                        // Entity 策略配置
                        .entityBuilder()
                        .enableSerialAnnotation()
                        .enableFileOverride()
                        .enableLombok(new ClassAnnotationAttributes("@Data","lombok.Data")) // 启用 Lombok 注解
                        .enableTableFieldAnnotation() // 启用字段注解
                        // 表字段自动填充
                        .addTableFills(new Column("create_time", FieldFill.INSERT))
                        .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))
                        .javaTemplate("/templates/entity.java")
                        .build()
                        // 控制器配置
                        .mapperBuilder()
                        .enableFileOverride()
                        .mapperTemplate("/templates/mapper.java")
                        .mapperXmlTemplate("/templates/mapper.xml")
                        .build()
                        .serviceBuilder()                           // 解耦 IService 模块，不再推荐使用（避免业务层混淆乱用），迁移至 CrudRepository 类
                        .superServiceImplClass(CrudRepository.class)
                        .formatServiceImplFileName("%sRepository")
                        .enableFileOverride()
                        .serviceTemplate("/templates/service.java")
                        .disableService()                           // 不在单独使用生成Service 迁移至 CrudRepository
                        .build()
                        .controllerBuilder()
                        .enableFileOverride()
                        .enableRestStyle()
                        .template("/templates/controller.java")
                        .disable())
                // 使用默认 Velocity 引擎模板生成代码
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
    public static void main(String[] args) {
        allGenerator();
    }
}

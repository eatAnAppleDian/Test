package com.orange.buildcode;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.orange.entity.BaseEntity;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xlz
 * @date 2022/2/27 20:07
 */
@SpringBootTest
public class MPGeneratorTest {

    public static void main(String[] args) {
        test01();
    }


    public static void test01() {
        // 设置要自动生成的表名
        List<String> tables = new ArrayList<>();
        // Collections.addAll(tables,"edu_teacher","acl_user","acl_role","acl_user_role");
        // 数据库连接参数
        String url = "jdbc:mysql:///edusys?serverTimezone=GMT%2B8&amp&useSSL=false&useUnicode=true&characterEncoding=utf-8&allowPublicKeyRetrieval=true";

        // 配置数据源
        final DataSourceConfig.Builder dataSourceConfig =
                new DataSourceConfig.Builder(url, "root", "root")
                        // 配置数据库类型转换器
                        .typeConvert(new MySqlTypeConvertCustom());

        // 开始配置自动生成策略
        FastAutoGenerator
                .create(dataSourceConfig) // 绑定数据源配置
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("orange")  //作者
                            //输出路径(写到java目录)
                            //System.getProperty("user.dir")当前项目的根目录
                            .outputDir(System.getProperty("user.dir") + "\\edu-service\\src\\main\\java")
                            //.enableSwagger()  //开启swagger
                            .disableOpenDir() // 禁止打开输出目录
                            .commentDate("yyyy-MM-dd")
                            .fileOverride();  //开启覆盖之前生成的文件
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("com.orange")  // 包父目录
                            //.moduleName("practice") // 模块包名
                            .entity("bean") // 实体包名
                            .service("service") // 服务层包名
                            .serviceImpl("service.impl") // 服务层实现包名
                            .controller("controller")
                            .mapper("mapper")
                            .xml("mapper.xml")
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
                                    System.getProperty("user.dir") + "\\edu-service\\src\\main\\resources\\com\\orange\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude() //配置要自动生成那些表，这里使用了list先存起来
                            .addTablePrefix("t_", "acl_", "crm_", "edu_", "ucenter_") // 配置要过滤的表前缀，这里配置后会自动去除指定的前缀，可以设置多个
                            // 配置服务层策略
                            .serviceBuilder()
                            .formatServiceFileName("%sService") // 设置服务层接口的后缀名   %s为占位符
                            .formatServiceImplFileName("%sServiceImpl") // 设置实现类后缀
                            // 配置实体类
                            .entityBuilder()
                            .superClass(BaseEntity.class) // 配置父类
                            // 填写BaseEntity中的公共字段
                            .addSuperEntityColumns("id", "gmt_create", "gmt_modified")
                            .enableLombok()  // 开启lombok注解
                            .logicDeleteColumnName("is_deleted") // 设置逻辑删除字段名
                            .enableRemoveIsPrefix()    // 开启boolean类型移除前缀
                            .idType(IdType.ASSIGN_ID)  // 设置主键id为雪花算法
                            .enableTableFieldAnnotation() // 自动将表的comment注释添加到实体类属性上
                            // 配置控制器类
                            .controllerBuilder()
                            .formatFileName("%sController") // 类名称后缀
                            .enableRestStyle() // 开启restFul风格注解
                            .enableHyphenStyle()  // url中驼峰转连字符
                            // mapper配置
                            .mapperBuilder()
                            .enableBaseResultMap()  //生成通用的resultMap
                            .superClass(BaseMapper.class) // 设置父类
                            .formatMapperFileName("%sMapper") // 类名称后缀
                            //.enableMapperAnnotation() // 开启注解设置,加@Mapper
                            .formatXmlFileName("%sMapper"); // mapper.xml文件名称，这里会自动添加.xml后缀
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                // 这里可以自定义模板
                //.templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}

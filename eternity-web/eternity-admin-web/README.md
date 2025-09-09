# Eternity 前端接口

## 项目结构
```
my-project（父POM）
├── eternity-common                  # 基础工具
├── eternity-model                   # 依赖 common
├── eternity-dao                     # 依赖 domain
├── eternity-service                 # 依赖 repository
│   ├── dto                          # DTO（数据传输对象）
└── eternity-web                     # 依赖 service
│   ├── eternity-admin-web           # 管理端入口
│   │   ├── dto                      # DTO（数据传输对象）
│   │   └── vo                       # VO（视图展示对象）
│   └── eternity-view-web            # 用户端入口
│   │   ├── dto                      # DTO（数据传输对象）
│   │   └── vo                       # VO（视图展示对象）
```

## 技术选型

| 技术              | 描述         |
|-----------------|------------|
| Java            | 编程语言       |
| Spring Boot     | Web应用框架    |
| Spring Security | 认证和授权框架    |
| Maven           | 项目管理工具     |
| MyBatis-plus    | ORM框架      |
| Lombok          | 简化Java代码工具 |
| Swagger         | API文档工具    |
| Slf4J           | 日志框架       |
| MySQL           | 数据库        |
| Redis           | 缓存数据库      |

## 开发

### 本地运行
1. 加载pom依赖
2. 创建数据库
3. 填写profiles/application-dev.properties内所需参数信息，并配置application.yml激活正确配置文件
```
例如
spring:
   profiles:
       active: dev
```
4. 打开SpringBoot启动类，启动项目
```
eternity-web/eternity-admin-web/src/main/java/com/jinhongs/eternity/admin/web/EternityAdminWebApplication.java
```

## 测试
本项目使用Swagger创建测试接口，并导入ApiFox测试工具进行测试
## 部署
本项目使用maven构建，打包时执行父Pom的maven命令
```
maven clean 
maven package
即可打包成对应项目的jar包，然后自行部署
```


## 贡献代码


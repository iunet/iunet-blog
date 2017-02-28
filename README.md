## iunet-blog个人博客网站
#### 所用技术:
 - spring
 - springMVC: MVC框架
 - tomcat: web容器
 - mybatis: ORM框架
 - bootstrap: css/html框架
 - oracle:数据库
 - maven：包/项目管理工具
 - webpack：打包工具

[github地址](https://github.com/iunet/iunet-blog)

####数据库配置
- 数据库脚本位于\iunet-blog\dbscript\Oracle 目录下

####配置文件修改
- 数据库配置文件  
  iunet-blog\iunet-web\src\main\resources\jdbc.properties  
- 应用配置文件   
  iunet-blog\iunet-web\src\main\resources\app.properties  
  **发送邮件配置 需要开通SMTP功能获取授权码**  
  mail.host=smtp.163.com  
  mail.username=*** //用户名  
  mail.password=*** //授权码（非密码）  
  **如果修改应用上下文根 请修改**  
  webContext=**** 

####部署
- **第一步：**在目录**iunet-blog/iunet-static** 运行cmd命令: **npm install** 待运行成功后，继续使用命令 **npm run dist**
- **第二步：**在目录**iunet-blog/** 运行cmd命令: **mvn clean package** ，运行成功后在项目目录会下生成  
  /target/**iunet-static.war** 和 /target/**iunet-web.war** 两个文件。
- **第三步：**将两个文件放到tomcat\webapps下，运行tomcat，在浏览器中输入[http://127.0.0.1:8080/iunet-web](http://127.0.0.1:8080/iunet-web)


[MIT](http://opensource.org/licenses/MIT)
Copyright (c) 2016-2017 xieyan
---

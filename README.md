服务器是IIS环境，暂不提供demo, sorry

---

[github地址](https://github.com/iunet/iunet-web)
## iunet-web个人博客网站
#### 所用技术:
 - spring
 - springMVC: MVC框架
 - tomcat: web容器
 - mybatis: ORM框架
 - bootstrap: css/html框架
 - oracle:数据库
 - maven：包/项目管理工具

####前端由 [iunet-static 生成](https://github.com/iunet/iunet-static)
- /iunet-web/target/iunet-web/app.js

####数据库配置
- 数据库脚本位于\iunet-web\dbscript\Oracle 目录下

####配置文件修改
- 数据库配置文件  
  \iunet-web\src\main\resources\config.properties  
- 应用配置文件   
  \iunet-web\src\main\resources\app.properties  
  **发送邮件配置 需要开通SMTP功能获取授权码**  
  mail.host=smtp.163.com  
  mail.username=*** //用户名  
  mail.password=*** //授权码（非密码）  
  **如果修改应用上下文根 请修改**  
  webContext=**** 

####部署

- 下载源码后使用命令行**cd iunet-web**进入项目目录运行: **mvn clean package** 待运行成功后在项目目录会下生成  
  /target/iunet-web.war文件，将文件放到tomcat\webapps下，运行tomcat即可

---
####预览
![登录](https://iunet.github.io/conf/iunet-web/login/login.png)
![图形验证码](https://iunet.github.io/conf/iunet-web/login/identifying_code.png)
![注册](https://iunet.github.io/conf/iunet-web/login/register.png)
![激活邮件](https://iunet.github.io/conf/iunet-web/login/active_mail.png)
![重置密码](https://iunet.github.io/conf/iunet-web/login/reset_password.png)
![重置密码验证码邮件](https://iunet.github.io/conf/iunet-web/login/reset_password_mail.png)


**以上是完成部分 业余时间会继续开发 不足之处还请指正 谢谢**  


[MIT](http://opensource.org/licenses/MIT)
Copyright (c) 2016-2017 xieyan
---
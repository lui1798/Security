# Security
SpringSecurity Dome
## 初始SQL
```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` varchar(25) COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
```
## 实现功能  
- 注册
- 登录
- 修改密码
- 登录验证码
- 记住我  
  第一次使用需要打开代码中注释掉的创建"记住我"表的语句
- CSRF
- 退出登录
- 权限模块 
## 链接
- 登录：http://127.0.0.1:9001/login.html
- 无限权限页面 http://127.0.0.1:9001/test/hello
## 用户名和密码 lei/123
## csrf  
用到CSRFController、csrf_token.html、csrfTest.html这几个文件，用来请求登录和获取csrf_token  
还加入了thymeleaf-extras-springsecurity4、thymeleaf模版引擎。
要测试CSRF是要记得打开pom文件中的thymeleaf
- CSRF测试：http://127.0.0.1:9001/public/toupdate

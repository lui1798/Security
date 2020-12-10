# Security
SpringSecurity Dome
## 实现功能  
- 注册
- 登录
- 修改密码
- 登录验证码
- 记住我
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

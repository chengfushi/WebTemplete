# SpringBoot Web应用开发模板

这是一个基于SpringBoot和Vue3的全栈Web应用开发模板，提供了完整的用户管理系统和前后端分离架构，帮助开发者快速搭建高质量的Web应用。

## 项目特点

- **前后端分离架构**：后端基于SpringBoot，前端基于Vue3+TypeScript+Vite
- **完整的用户系统**：包含注册、登录、注销、权限控制等功能
- **数据库支持**：集成MySQL和MyBatisFlex，简化数据库操作
- **Redis支持**：使用Redis进行缓存和Session管理
- **统一响应处理**：规范的API响应格式和全局异常处理
- **权限控制**：基于注解的权限校验系统
- **API文档**：集成Knife4j和SpringDoc，自动生成API文档
- **前端组件库**：使用Ant Design Vue构建美观的用户界面
- **状态管理**：使用Pinia进行前端状态管理

## 技术栈

### 后端

- SpringBoot 3.x
- MyBatisFlex (增强的ORM框架)
- MySQL
- Redis
- Spring Session
- Knife4j & SpringDoc (API文档)

### 前端

- Vue 3
- TypeScript
- Vite
- Ant Design Vue
- Pinia (状态管理)
- Vue Router

## 项目结构

### 后端结构

```
src/main/java/com/chengfu/springbootinit/
├── annotation        # 自定义注解
├── aop              # 面向切面编程
├── common           # 通用类
├── config           # 配置类
├── constant         # 常量
├── controller       # 控制器
├── exception        # 异常处理
├── generator        # 代码生成器
├── mapper           # 数据访问层
├── model            # 数据模型
│   ├── dto          # 数据传输对象
│   ├── entity       # 实体类
│   ├── enums        # 枚举类
│   └── vo           # 视图对象
├── service          # 服务层
│   └── impl         # 服务实现
└── SpringbootinitApplication.java  # 应用入口
```

### 前端结构

```
vue-front-init/
├── public           # 静态资源
├── src              # 源代码
│   ├── api          # API接口
│   ├── assets       # 资源文件
│   ├── components   # 公共组件
│   ├── layouts      # 布局组件
│   ├── pages        # 页面
│   ├── router       # 路由配置
│   ├── stores       # 状态管理
│   ├── App.vue      # 根组件
│   ├── main.ts      # 入口文件
│   └── request.ts   # 请求配置
└── ...              # 其他配置文件
```

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- Node.js 16+

### 后端启动

1. 克隆项目到本地

```bash
git clone https://github.com/yourusername/springbootinit.git
cd springbootinit
```

2. 创建MySQL数据库

```sql
CREATE DATABASE IF NOT EXISTS springboot_demo;
```

3. 执行SQL脚本

```bash
mysql -u root -p springboot_demo < sql/create_table.sql
```

4. 修改配置文件

编辑 `src/main/resources/application.yml`，配置数据库和Redis连接信息：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/springboot_demo
    username: your_username
    password: your_password
  
  data:
    redis:
      host: localhost
      port: 6379
      database: 0
      password: your_redis_password
```

5. 启动后端服务

```bash
mvn spring-boot:run
```

或使用IDE直接运行 `SpringbootinitApplication.java`

### 前端启动

1. 进入前端目录

```bash
cd vue-front-init
```

2. 安装依赖

```bash
npm install
```

3. 启动开发服务器

```bash
npm run dev
```

4. 构建生产版本

```bash
npm run build
```

## 核心功能

### 用户管理

- 用户注册
- 用户登录
- 用户信息修改
- 用户注销
- 用户列表查询（管理员）
- 用户删除（管理员）

### 权限控制

使用 `@AuthCheck` 注解进行权限校验：

```java
@PostMapping("/delete")
@AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
    // 只有管理员可以访问
}
```

## API接口时序图

### 用户注册流程

```mermaid
sequenceDiagram
    participant 客户端
    participant UserController
    participant UserService
    participant 数据库
    
    客户端->>UserController: POST /api/user/register
    Note right of 客户端: {userAccount, userPassword, checkPassword}
    
    UserController->>UserController: 参数校验
    UserController->>UserService: userRegister(userAccount, userPassword, checkPassword)
    
    UserService->>UserService: 业务校验(账号长度、密码长度等)
    UserService->>UserService: 账号是否已存在
    UserService->>UserService: 密码加密
    UserService->>数据库: 保存用户信息
    数据库-->>UserService: 返回用户ID
    
    UserService-->>UserController: 返回用户ID
    UserController-->>客户端: 返回注册结果
```

### 用户登录流程

```mermaid
sequenceDiagram
    participant 客户端
    participant UserController
    participant UserService
    participant 数据库
    participant Session
    
    客户端->>UserController: POST /api/user/login
    Note right of 客户端: {userAccount, userPassword}
    
    UserController->>UserService: userLogin(userAccount, userPassword, request)
    
    UserService->>数据库: 查询用户信息
    数据库-->>UserService: 返回用户数据
    
    UserService->>UserService: 校验密码
    UserService->>Session: 记录用户登录态
    UserService->>UserService: 用户信息脱敏
    
    UserService-->>UserController: 返回脱敏后的用户信息
    UserController-->>客户端: 返回登录结果和用户信息
```

### 获取当前登录用户

```mermaid
sequenceDiagram
    participant 客户端
    participant UserController
    participant UserService
    participant Session
    
    客户端->>UserController: GET /api/user/get/login
    
    UserController->>UserService: getLoginUser(request)
    UserService->>Session: 获取登录态
    Session-->>UserService: 返回Session中的用户信息
    
    UserService-->>UserController: 返回脱敏后的用户信息
    UserController-->>客户端: 返回当前登录用户信息
```

### 用户注销流程

```mermaid
sequenceDiagram
    participant 客户端
    participant UserController
    participant UserService
    participant Session
    
    客户端->>UserController: POST /api/user/logout
    
    UserController->>UserService: userLogout(request)
    UserService->>Session: 清除登录态
    Session-->>UserService: 返回操作结果
    
    UserService-->>UserController: 返回注销结果
    UserController-->>客户端: 返回操作成功信息
```

### 用户管理（管理员）

```mermaid
sequenceDiagram
    participant 客户端
    participant UserController
    participant 权限拦截器
    participant UserService
    participant 数据库
    
    客户端->>UserController: POST /api/user/list/page/vo
    Note right of 客户端: 需要管理员权限
    
    UserController->>权限拦截器: @AuthCheck(mustRole = "admin")
    权限拦截器->>UserService: 检查用户角色
    UserService-->>权限拦截器: 权限验证结果
    
    alt 有权限
        权限拦截器->>UserController: 允许访问
        UserController->>UserService: 查询用户列表
        UserService->>数据库: 分页查询
        数据库-->>UserService: 返回用户数据
        UserService->>UserService: 数据脱敏
        UserService-->>UserController: 返回脱敏后的用户列表
        UserController-->>客户端: 返回用户列表数据
    else 无权限
        权限拦截器-->>客户端: 返回无权限错误
    end
```

## 前端调用示例

### 用户登录

```typescript
import { userLogin } from '@/api/userController';
import { useLoginUserStore } from '@/stores/loginUser';

const loginUserStore = useLoginUserStore();

// 登录请求
const handleLogin = async () => {
  try {
    const res = await userLogin({
      userAccount: 'your_account',
      userPassword: 'your_password'
    });
    
    if (res.data.code === 0 && res.data.data) {
      // 更新登录用户信息
      await loginUserStore.fetchLoginUser();
      // 登录成功后跳转
      router.push('/');
    }
  } catch (error) {
    console.error('登录失败:', error);
  }
};
```

### 获取当前用户

```typescript
import { getLoginUser } from '@/api/userController';

const fetchCurrentUser = async () => {
  const res = await getLoginUser();
  if (res.data.code === 0 && res.data.data) {
    return res.data.data;
  }
  return null;
};
```

## 自定义开发

### 添加新的实体和接口

1. 创建实体类

```java
@TableName("your_table")
public class YourEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    // 其他字段
    
    // getter 和 setter
}
```

2. 创建Mapper接口

```java
public interface YourEntityMapper extends BaseMapper<YourEntity> {
}
```

3. 创建Service接口和实现

```java
public interface YourEntityService extends IService<YourEntity> {
    // 自定义方法
}

@Service
public class YourEntityServiceImpl extends ServiceImpl<YourEntityMapper, YourEntity> implements YourEntityService {
    // 实现自定义方法
}
```

4. 创建Controller

```java
@RestController
@RequestMapping("/your-entity")
public class YourEntityController {
    @Resource
    private YourEntityService yourEntityService;
    
    // 实现API接口
}
```

### 添加权限控制

使用 `@AuthCheck` 注解控制接口访问权限：

```java
@PostMapping("/admin-operation")
@AuthCheck(mustRole = "admin")
public BaseResponse<Boolean> adminOperation(@RequestBody YourRequest request) {
    // 只有管理员可以访问的操作
}
```

## 常见问题

### 1. 跨域问题

本项目已通过 `CorsConfig` 配置解决跨域问题，如需修改，请编辑 `CorsConfig.java` 文件。

### 2. 会话管理

项目使用Redis存储会话信息，会话有效期为30天，可在 `application.yml` 中修改：

```yaml
spring:
  session:
    store-type: redis
    timeout: 2592000  # 30天，单位秒
```

### 3. 密码加密

用户密码使用加密存储，可在 `UserServiceImpl.java` 中查看加密实现。

## 贡献指南

欢迎提交Issue和Pull Request，一起完善这个项目！

## 许可证

[MIT License](LICENSE)
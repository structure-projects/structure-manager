# structure-manager
介于阿里巴巴开发规范中提到的manager层，structure-manager对mapper 到 service 部分进行了封装,功能和mybatis-plus有些类似 只不过支持了jpa、mybatis、mybatis-plus、和tk-mapper
1. 规范了manager层
2. service 代码基本一致如果技术更换的话可以无缝衔接
3. 对mybatis-plus 封装了CreateTime 和 UpdateTime 和逻辑删除功能
4. 封装了 mybatis-plus 和 tk-mapper 公共 mapper
## 引用POM ##
```xml
     <dependency>
        <groupId>cn.structured</groupId>
        <artifactId>structure-manager-starter</artifactId>
        <version>${last.version}</version>
    </dependency>
```
## 使用manager 接口## 
```java
/**
 * <p>
 * 用户Manager
 * </p>
 *
 * @author chuck
 * @version 1.0.1
 * @since 2020/12/30 21:05
 */
public interface IUserManager extends IBaseManager<User> {
}
```
## 使用manager 实现## 
manager 默认有四种实现 目前支持最好的是mybatis-plus和tk-mapper,jpa和mybatis原生态有很多功能没有实现需要重写方法
- PlusManagerImpl 完美兼容 mybatis 
- TkManagerImpl 完全兼容 tkMapper
- DefaultManagerImpl 默认对mybatis的实现很多功能没有需要重写，需要配合逆向功能才能使用基础的增删改查，当然你也可以重写mapper
- JpaManagerImpl 对 jpa 兼容极差建议慎用，可能很多地方都需要重写
### PlusManagerImpl 实现示例 ###
```java
/**
 * <p>
 * 用户Manager实现
 * </p>
 *
 * @author chuck
 * @version 1.0.1
 * @since 2020/12/30 21:05
 */
@Service
public class UserManagerImpl extends PlusManagerImpl<UserMapper, User> implements IUserManager  {

}
```
### manager 的使用 ###
```java
package cn.structure.example.mybatisplus.service.impl;

import cn.structure.example.mybatisplus.manager.IUserManager;
import cn.structure.example.mybatisplus.pojo.po.User;
import cn.structure.example.mybatisplus.mapper.UserMapper;
import cn.structure.example.mybatisplus.service.IUserService;
import cn.structure.starter.manager.common.ReqPage;
import cn.structure.starter.manager.common.ResPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author chuck
 * @since 2020-12-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserManager iUserManager;

    @Override
    public int update(User user) {
        return iUserManager.updateById(user);
    }

    @Override
    public int insert(User user) {
        return iUserManager.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = iUserManager.getOne(new User() {
            {
                setUsername(username);
            }
        });
        return user;
    }

    @Override
    public List<User> listUserPage(String username, int pageSize, int offset) {
        User user = new User() {{
            setUsername(username);
        }};
        return listUserPage(user,pageSize,offset);
    }

    @Override
    public List<User> listUserPage(User user, int pageSize, int offset) {
        ReqPage<User> objectReqPage = new ReqPage<>();
        objectReqPage.setPage(offset);
        objectReqPage.setRows(pageSize);
        objectReqPage.setParameter(user);
        ResPage<User> page = iUserManager.page(objectReqPage);
        return page.getList();
    }
}
```
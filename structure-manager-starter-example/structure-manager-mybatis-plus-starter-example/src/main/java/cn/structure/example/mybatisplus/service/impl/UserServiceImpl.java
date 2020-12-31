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

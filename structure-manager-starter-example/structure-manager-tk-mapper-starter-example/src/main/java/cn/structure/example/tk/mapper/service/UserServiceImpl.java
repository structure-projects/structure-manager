package cn.structure.example.tk.mapper.service;

import cn.structure.example.tk.mapper.manager.IUserManager;
import cn.structure.example.tk.mapper.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户service实现
 * </p>
 *
 * @author chuck
 * @version 1.0.1
 * @since 2020/12/27 16:53
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserManager iUserManager;

    @Override
    public User getUserById(Long id) {
        log.info("UserServiceImpl------> getUserById -------> id ={}",id);
        User user = iUserManager.getById(id);
        log.info("UserServiceImpl------> getUserById ------->{}",((user==null) ? "this user is null" : user.getUsername()));
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        log.info("UserServiceImpl------> getUserByUsername -------> username ={}",username);
        User user = iUserManager.getOne(new User(){{
            setUsername(username);
        }});
        log.info("UserServiceImpl------> getUserById ------->{}",((user==null) ? "this user is null" : user.getUsername()));
        return user;
    }

    @Override
    public List<User> listUserPage(String username, int pageSize, int offset) {
        log.info("UserServiceImpl------> listUserPage -------> username ={},pageSize ={},offset = {}",username,pageSize,offset);
        PageHelper.startPage(pageSize,offset);
        List<User> userList = iUserManager.list(new User(){{
            setUsername(username);
        }});
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        log.info("UserServiceImpl------> listUserPage -------> pageInfoSize = {}",pageInfo.getSize());
        return userList;
    }

    @Override
    public int insertUser(User user) {
        log.info("UserServiceImpl----->insertUser-----> username ={}",user.getUsername());
        int row = iUserManager.save(user);
        log.info("UserServiceImpl----->insertUser-----> id ={}",user.getId());
        return row;
    }

    @Override
    public int updateUserById(User user) {
        log.info("UserServiceImpl----->updateUserById-----> id ={}",user.getId());
        int i = iUserManager.updateById(user);
        log.info("UserServiceImpl----->updateUserById-----> rows = {}",i);
        return i;
    }

    @Override
    public int deleteById(Long id) {
        log.info("UserServiceImpl----->deleteById-----> id = {}",id);
        int delete = iUserManager.deleteById(id);
        log.info("UserServiceImpl----->deleteById-----> rows = {}",delete);
        return delete;
    }
}

package cn.structure.example.mybatisplus.manager;

import cn.structure.example.mybatisplus.mapper.UserMapper;
import cn.structure.example.mybatisplus.pojo.po.User;
import cn.structure.starter.manager.core.Impl.PlusManagerImpl;
import org.springframework.stereotype.Service;

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

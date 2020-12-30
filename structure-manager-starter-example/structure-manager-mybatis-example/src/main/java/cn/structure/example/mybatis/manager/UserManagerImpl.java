package cn.structure.example.mybatis.manager;

import cn.structure.example.mybatis.entity.User;
import cn.structure.example.mybatis.mapper.IUserMapper;
import cn.structure.example.mybatis.mapper.UserMapper;
import cn.structure.starter.manager.core.Impl.DefaultManagerImpl;
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
public class UserManagerImpl extends DefaultManagerImpl<UserMapper, User> implements IUserManager  {

}

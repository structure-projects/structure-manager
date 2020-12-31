package cn.structure.example.tk.mapper.manager;

import cn.structure.example.tk.mapper.dao.UserMapper;
import cn.structure.example.tk.mapper.model.User;
import cn.structure.starter.manager.core.Impl.TkManagerImpl;
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
public class UserManagerImpl extends TkManagerImpl<UserMapper, User> implements IUserManager  {

}

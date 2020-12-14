package cn.smbms.service.impl;

import cn.smbms.mapper.UserMapper;
import cn.smbms.pojo.User;
import cn.smbms.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author knn
 * @create 2020-11-20 20:58
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User finUserByUsernameAndPassword(User user) {
        return userMapper.finUserByUsernameAndPassword(user);
    }

    @Override
    public List<User> query() {
        return userMapper.query();
    }

    @Override
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public boolean delete(Integer userId) {
        int i = userMapper.delete(userId);
        return i >= 0;
    }

    @Override
    public void add(User user) {
        user.setCreationDate(new Date());
        userMapper.add(user);
    }

    @Override
    public boolean exist(String userCode) {
        User user = userMapper.exist(userCode);
        return user != null;
    }

    @Override
    public User finUserByPassword(String userCode,String oldPassword) {
        return userMapper.findUserByPassword(userCode,oldPassword);
    }

    @Override
    public boolean updatePasswordByUserCode(String userCode, String newpassword) {
        int i = userMapper.updatePasswordByUserCode(userCode, newpassword);
        return i > 0;
    }

    @Override
    public List<User> search(String queryUserCodeStr, String queryUserNameStr, Integer queryUserRoleId) {
        return userMapper.search(queryUserCodeStr, queryUserNameStr, queryUserRoleId);
    }
}

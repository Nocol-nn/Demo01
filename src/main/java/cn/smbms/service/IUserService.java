package cn.smbms.service;

import cn.smbms.pojo.User;

import java.text.ParseException;
import java.util.List;

/**
 * @author knn
 * @create 2020-11-20 20:58
 */
public interface IUserService {
    /**
     * 根据用户名和密码查询用户
     *
     * @param user
     * @return
     */
    User finUserByUsernameAndPassword(User user);

    /**
     * 查询所有用户
     *
     * @return
     */
    List<User> query();

    /**
     * 根据id查询用户
     *
     * @param userId
     * @return
     */
    User getUserById(Integer userId);

    /**
     * 更新用户
     *
     * @param user
     */
    void update(User user);

    /**
     * 根据id删除用户
     *
     * @param userId
     * @return
     */
    boolean delete(Integer userId);

    /**
     * 添加用户
     *
     * @param user
     */
    void add(User user);

    /**
     * 根据userCode判断用户是否存在
     *
     * @param userCode
     * @return
     */
    boolean exist(String userCode);

    /**
     * 根据密码查询用户
     *
     * @param oldPassword
     * @return
     */
    User finUserByPassword(String userCode,String oldPassword);

    /**
     * 根据UserCode更新用户密码
     *
     * @param newpassword
     */
    boolean updatePasswordByUserCode(String userCode, String newpassword);

    /**
     * 查询
     * @param queryUserNameStr
     * @param queryUserCodeStr
     * @param queryUserRoleId
     * @return
     */
    List<User> search(String queryUserNameStr, String queryUserCodeStr, Integer queryUserRoleId);
}

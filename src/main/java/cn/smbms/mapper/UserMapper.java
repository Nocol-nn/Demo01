package cn.smbms.mapper;

import cn.smbms.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-20 20:58
 */
public interface UserMapper {
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
    int delete(Integer userId);

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
    User exist(String userCode);

    /**
     * 根据密码查询用户
     *
     * @param oldPassword
     * @return
     */
    User findUserByPassword(@Param("userCode") String userCode, @Param("oldPassword") String oldPassword);

    /**
     * 根据UserCode更新用户密码
     *
     * @param userCode
     * @param newpassword
     */
    int updatePasswordByUserCode(@Param("userCode") String userCode, @Param("newpassword") String newpassword);

    /**
     * 查询
     *
     * @param queryUserCodeStr
     * @param queryUserNameStr
     * @return
     */
    List<User> search(@Param("queryUserCodeStr") String queryUserCodeStr, @Param("queryUserNameStr") String queryUserNameStr, @Param("queryUserRoleId") Integer queryUserRoleId);
}

package cn.smbms.mapper;

import cn.smbms.pojo.Role;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-21 23:13
 */
public interface RoleMapper {

    /**
     * 获取所有的角色
     * @return
     */
    List<Role> getAllRole();
}

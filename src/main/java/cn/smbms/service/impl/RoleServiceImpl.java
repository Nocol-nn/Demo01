package cn.smbms.service.impl;

import cn.smbms.mapper.RoleMapper;
import cn.smbms.pojo.Role;
import cn.smbms.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-21 23:12
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getAllRole() {
        return roleMapper.getAllRole();
    }
}

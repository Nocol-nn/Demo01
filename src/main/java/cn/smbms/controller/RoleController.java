package cn.smbms.controller;

import cn.smbms.pojo.Role;
import cn.smbms.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-21 23:13
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/getAllRole")
    @ResponseBody
    public List<Role> getAllRole() {
        List<Role> roleList = roleService.getAllRole();
        return roleList;
    }
}

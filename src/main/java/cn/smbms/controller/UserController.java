package cn.smbms.controller;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.IRoleService;
import cn.smbms.service.IUserService;
import cn.smbms.service.impl.UserServiceImpl;
import cn.smbms.tools.Constants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author knn
 * @create 2020-11-20 20:50
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login.jsp";

    }

    @RequestMapping("/login")
    public String login(String userCode, String userPassword, Model model, HttpSession session) {
        User user = new User();
        user.setUserCode(userCode);
        user.setUserPassword(userPassword);

        User dbUser = userService.finUserByUsernameAndPassword(user);
        if (dbUser == null) {
            model.addAttribute("error", "用户名或密码错误");
            return "forward:/login.jsp";
        } else {
            session.setAttribute(Constants.USER_SESSION, dbUser);
            return "redirect:/jsp/frame.jsp";
        }
    }

    @RequestMapping("/query")
    public ModelAndView query() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> userList = userService.query();
        modelAndView.addObject("userList", userList);
        modelAndView.setViewName("userlist");
        return modelAndView;
    }

    @RequestMapping("/view/{userId}")
    public ModelAndView view(@PathVariable("userId") Integer userId) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUserById(userId);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("userview");
        return modelAndView;
    }

    @RequestMapping("/modifyUI/{userId}")
    public ModelAndView modifyUI(@PathVariable("userId") Integer userId) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUserById(userId);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("usermodify");
        return modelAndView;
    }

    @RequestMapping("/update")
    public String update(User user, MultipartFile pictureFile) throws IOException {
        //更新前的头像图片

        Integer userId = user.getId();

        User oldUser = userService.getUserById(userId);
        System.out.println("oldUser------" + oldUser);
        System.out.println("user---------" + user);
        System.out.println("pictureFile--" + pictureFile.getOriginalFilename());
        String newFileName = null;
        // 用户选择更新头像
        if (pictureFile.getOriginalFilename() != null && pictureFile.getOriginalFilename().length() > 0) {
            String originalFilename = pictureFile.getOriginalFilename();
            String filename = originalFilename.substring(originalFilename.lastIndexOf("."));
            newFileName = UUID.randomUUID().toString().replace("-", "") + filename;
            pictureFile.transferTo(new File("E:\\upload\\" + newFileName));
            user.setUserPic(newFileName);
            File oldUserPic = new File("E:\\upload\\" + oldUser.getUserPic());
            if (oldUserPic.exists()) {
                oldUserPic.delete();
            }
        } else {
            user.setUserPic(oldUser.getUserPic());
        }

        userService.update(user);
        System.out.println("update=====" + userService.getUserById(userId));
        return "redirect:/user/search";
    }

    @RequestMapping("/pwdmodify")
    public ModelAndView pwdmodify(String newpassword, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        User userSession = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        if (userSession != null && !StringUtils.isNullOrEmpty(newpassword)) {
            boolean flag = userService.updatePasswordByUserCode(userSession.getUserCode(), newpassword);
            if (flag) {
                //session注销
                modelAndView.addObject(Constants.SYS_MESSAGE, "修改密码成功,请退出并使用新密码重新登录！");
                request.getSession().removeAttribute(Constants.USER_SESSION);
            } else {
                modelAndView.addObject(Constants.SYS_MESSAGE, "修改密码失败！");
            }
        } else {
            modelAndView.addObject(Constants.SYS_MESSAGE, "修改密码失败！");
        }
        modelAndView.setViewName("pwdmodify");
        return modelAndView;
    }

    @RequestMapping("/getUserByPassword/{oldPassword}")
    @ResponseBody
    public Map<String, String> getUserByPassword(@PathVariable("oldPassword") String oldPassword, HttpServletRequest request) {
        Map<String, String> resultMap = new HashMap<>();
        User userSession = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        System.out.println("session中的用户密码：" + userSession);

        User user = userService.finUserByPassword(userSession.getUserCode(), oldPassword);
        System.out.println("查询出的user" + user);
        if (userSession == null) {
            //session过期
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(userSession.getUserPassword())) {
            //旧密码输入为空
            resultMap.put("result", "error");
        } else {
            if (user != null && userSession.getUserPassword().equals(user.getUserPassword())) {
                resultMap.put("result", "true");
            } else {
                //旧密码输入不正确
                resultMap.put("result", "false");
            }
        }
        return resultMap;
    }

    @RequestMapping("/add")
    public String add(User user, HttpServletRequest request, MultipartFile pictureFile) throws IOException {
        User userSession = (User) request.getSession().getAttribute(Constants.USER_SESSION);

        System.out.println("add" + user);

        String newFileName = null;
        if (pictureFile.getOriginalFilename() != null && pictureFile.getOriginalFilename().length() > 0) {
            String originalFilename = pictureFile.getOriginalFilename();
            String filename = originalFilename.substring(originalFilename.lastIndexOf("."));
            newFileName = UUID.randomUUID().toString().replace("-", "") + filename;
            pictureFile.transferTo(new File("E:\\upload\\" + newFileName));
        }
        user.setCreatedBy(userSession.getId());
        user.setUserPic(newFileName);

        System.out.println(user);


        userService.add(user);
        return "redirect:/user/search";
    }

    @GetMapping("/exist/{userCode}")
    @ResponseBody
    public Map<String, String> exist(@PathVariable("userCode") String userCode) {
        Map<String, String> map = new HashMap<>();
        boolean flag = userService.exist(userCode);

        System.out.println(flag);

        if (flag) {
            map.put("userCode", "exist");
        } else {
            map.put("userCode", "notexist");
        }
        return map;
    }

    @DeleteMapping("/delete/{userId}")
    @ResponseBody
    public Map<String, String> delete(@PathVariable("userId") Integer userId) {
        Map<String, String> map = new HashMap<>();
        boolean flag = userService.delete(userId);
        if (flag) {
            //删除成功
            map.put("delResult", "true");
        } else {
            //删除失败
            map.put("delResult", "false");
        }
        return map;
    }

    @RequestMapping("/search")
    public ModelAndView search(String queryUserCode, String queryUserName, Integer queryUserRole, @RequestParam(defaultValue = "1") Integer pageIndex) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("queryUserCode===" + queryUserCode);
        System.out.println("queryUserName===" + queryUserName);
        System.out.println("queryUserRoleId===" + queryUserRole);
        //根据id查询角色的名字
        User queryUser = userService.getUserById(queryUserRole);
        String queryUserCodeStr = null;
        if (queryUserCode != null) {
            queryUserCodeStr = "%" + queryUserCode + "%";
        }
        String queryUserNameStr = null;
        if (queryUserName != null) {
            queryUserNameStr = "%" + queryUserName + "%";
        }
        System.out.println(pageIndex);
        //设置分页的相关参数 当前页+每页显示的条数
        PageHelper.startPage(pageIndex, 3);
        List<User> userListPage = userService.search(queryUserCodeStr, queryUserNameStr, queryUserRole);
        PageInfo<User> pageInfo = new PageInfo<User>(userListPage);
        List<User> pageInfoList = pageInfo.getList();
        System.out.println("pageInfoList==" + pageInfoList);
        List<Role> roleList = roleService.getAllRole();
        modelAndView.addObject("queryUser", queryUser);
        modelAndView.addObject("roleList", roleList);
        modelAndView.addObject("pageInfo", pageInfo);
        modelAndView.addObject("queryUserCode", queryUserCode);
        modelAndView.addObject("queryUserName", queryUserName);
        modelAndView.addObject("queryUserRole", queryUserRole);
        modelAndView.setViewName("userlist");
        return modelAndView;
    }

}

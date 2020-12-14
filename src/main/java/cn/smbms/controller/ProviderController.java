package cn.smbms.controller;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.IProviderService;
import cn.smbms.tools.Constants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author knn
 * @create 2020-11-21 14:15
 */
@Controller
@RequestMapping("/provider")
public class ProviderController {

    @Autowired
    private IProviderService providerService;


    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Map<String, String> delete(@PathVariable("id") Integer id) {
        boolean flag = providerService.delete(id);
        Map<String, String> map = new HashMap<>();
        if (flag) {
            //删除成功
            map.put("delResult", "true");
        } else {
            //删除失败
            map.put("delResult", "false");
        }
        return map;
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id) {
//        0 已移除 ；1未移除
        int isRemove = 0;
        providerService.updateIsRemoveById(id, isRemove);
        return "redirect:/provider/search";
    }

    @GetMapping("/isExists")
    @ResponseBody
    public Map<String, String> isExists(String proName) throws UnsupportedEncodingException {

        String decode = URLDecoder.decode(proName, "UTF-8");
        System.out.println(decode);

        Map<String, String> map = new HashMap<>();
        boolean flag = providerService.getProvideByName(decode);
        if (flag) {
            //存在
            map.put("delResult", "false");
        } else {
            //不存在
            map.put("delResult", "true");
        }
        return map;
    }

    @RequestMapping("/modify")
    public String modify(Provider provider) {
        providerService.modify(provider);
        return "redirect:/provider/search";
    }

    @RequestMapping("/modify/{providerId}")
    public ModelAndView modify(@PathVariable("providerId") Integer providerId) {
        ModelAndView modelAndView = new ModelAndView();
        Provider provider = providerService.getProvideById(providerId);
        modelAndView.addObject("provider", provider);
        modelAndView.setViewName("providermodify");
        return modelAndView;
    }


    @RequestMapping("/view/{providerId}")
    public ModelAndView view(@PathVariable("providerId") Integer providerId) {
        ModelAndView modelAndView = new ModelAndView();
        Provider provider = providerService.getProvideById(providerId);
        modelAndView.addObject("provider", provider);
        modelAndView.setViewName("providerview");
        return modelAndView;
    }

    @RequestMapping("/removeUI")
    public ModelAndView removeUI() {
        ModelAndView modelAndView = new ModelAndView();
        List<Provider> providerList = providerService.getAllProName();
        modelAndView.addObject("providerList", providerList);
        modelAndView.setViewName("providerremove");
        return modelAndView;
    }

    @RequestMapping("/getAllProName")
    @ResponseBody
    public List<Provider> getAllProName() {
        List<Provider> providerList = providerService.getAllProName();
        return providerList;
    }

    @RequestMapping("/query")
    public ModelAndView query() {
        ModelAndView modelAndView = new ModelAndView();
        List<Provider> providerList = providerService.getAllProName();
        modelAndView.addObject("providerList", providerList);
        modelAndView.setViewName("providerlist");
        return modelAndView;
    }

    @RequestMapping("/add")
    public String add(Provider provider, HttpServletRequest request) {
        User userSession = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        System.out.println("----------add-----");
        System.out.println(provider);
        provider.setCreatedBy(userSession.getId());
        provider.setCreationDate(new Date());
        providerService.add(provider);
        return "redirect:/provider/search";
    }

    @RequestMapping("/search")
    public ModelAndView search(String queryProCode, String queryProName, @RequestParam(defaultValue = "1") Integer pageIndex) {
        ModelAndView modelAndView = new ModelAndView();

        System.out.println("queryProCode===" + queryProCode);
        System.out.println("queryProName===" + queryProName);

        String queryProCodeStr = null;
        if (queryProCode != null) {
            queryProCodeStr = "%" + queryProCode + "%";
        }
        String queryProNameStr = null;
        if (queryProName != null) {
            queryProNameStr = "%" + queryProName + "%";
        }

        System.out.println(pageIndex);
        //设置分页的相关参数 当前页+每页显示的条数
        PageHelper.startPage(pageIndex, 3);

        List<Provider> providerList = providerService.search(queryProCodeStr, queryProNameStr);

        System.out.println("providerList==" + providerList);
        PageInfo<Provider> pageInfo = new PageInfo<Provider>(providerList);


        System.out.println("====================");
        List<Provider> pageInfoList = pageInfo.getList();
        System.out.println("pageInfoList==" + pageInfoList);

        modelAndView.addObject("providerList", providerList);
        modelAndView.addObject("pageInfo", pageInfo);
        modelAndView.addObject("queryProCode", queryProCode);
        modelAndView.addObject("queryProName", queryProName);
        modelAndView.setViewName("providerlist");
        return modelAndView;
    }


}

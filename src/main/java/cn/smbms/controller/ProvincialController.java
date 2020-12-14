package cn.smbms.controller;

import cn.smbms.pojo.Provincial;
import cn.smbms.service.IProvincialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-25 10:26
 */
@Controller
@RequestMapping("/provincial")
public class ProvincialController {

    @Autowired
    private IProvincialService provincialService;

    @RequestMapping("/getAllProvincial")
    @ResponseBody
    public List<Provincial> getAllProvincial() {
        return provincialService.getAllProvincial();
    }
}

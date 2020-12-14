package com.it.test;

import cn.smbms.mapper.ProvincialMapper;
import cn.smbms.pojo.Provincial;
import cn.smbms.service.IProvincialService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-25 10:30
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dao.xml", "classpath:applicationContext-service.xml",
        "classpath:spring-mvc.xml"})
public class ProvincialTest {
    @Autowired
    private IProvincialService provincialService;
    @Autowired
    private ProvincialMapper provincialMapper;

    @Test
    public void getAllProvincial() {
        List<Provincial> allProvincial = provincialService.getAllProvincial();
        for (Provincial provincial : allProvincial) {
            System.out.println(provincial);
        }
    }

    @Test
    public void getProvincialCodeById() {
        Provincial provincial = provincialMapper.getProvincialCodeById(1);

        System.out.println(provincial);

    }
}

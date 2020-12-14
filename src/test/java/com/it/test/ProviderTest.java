package com.it.test;

import cn.smbms.pojo.Provider;
import cn.smbms.service.IProviderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-21 14:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dao.xml", "classpath:applicationContext-service.xml",
        "classpath:spring-mvc.xml"})
public class ProviderTest {

    @Autowired
    private IProviderService providerService;


    @Test
    public void getAllProName() {
        List<Provider> allProName = providerService.getAllProName();
        for (Provider provider : allProName) {
            System.out.println(provider);
        }
    }

    @Test
    public void modify() {
        Provider provider = new Provider();
        provider.setId(1);
        provider.setProName("北京三木堂商贸有限公司1");
        providerService.modify(provider);
    }

    @Test
    public void search() {
        List<Provider> search = providerService.search(null, "%深圳市%");
        for (Provider provider : search) {
            System.out.println(provider);
        }

    }

    @Test
    public void getProvideByName() {
        boolean provideByName = providerService.getProvideByName("石家庄帅益食品贸易有限公司");
        System.out.println(provideByName);


    }
}

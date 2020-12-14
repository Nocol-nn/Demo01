package com.it.test;

import cn.smbms.pojo.Bill;
import cn.smbms.service.IBillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * @author knn
 * @create 2020-11-21 13:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dao.xml", "classpath:applicationContext-service.xml",
        "classpath:spring-mvc.xml"})
public class BillTest {

    @Autowired
    private IBillService billService;

    @Test
    public void query(){

        List<Bill> query = billService.query();
        for (Bill bill : query) {
            System.out.println(bill);
        }
    }

    @Test
    public void delete(){
        billService.delete(18);
    }


    @Test
    public void search() {

//        List<Bill> search = billService.search(null,null,null,1);
//        for (Bill bill : search) {
//            System.out.println(bill);
//        }
    }

    @Test
    public void likeBill() {

        List<String> list = billService.likeBill("洗");
        for (String s : list) {
            System.out.println(s);
        }
    }

    @Test
    public void test1(){
        System.out.println(new SimpleDateFormat("yyyy").format(new Date()));
    }
}

package com.it.test;

import cn.smbms.service.IBillService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author knn
 * @create 2020-11-25 20:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dao.xml", "classpath:applicationContext-service.xml",
        "classpath:spring-mvc.xml"})
public class FileTest {

    @Autowired
    private IBillService billService;

//    @Test
//    public void download() {
//            response.setContentType("application/vnd.ms-excel");
//            response.setCharacterEncoding("utf-8");
//            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
//            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
//            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
//            // 这里需要设置不关闭流
//            List<Bill> billListData = billService.query();
//            EasyExcel.write(response.getOutputStream(), BillFileData.class).autoCloseStream(Boolean.FALSE).sheet("模板")
//                    .doWrite(billListData);
//
//    }
}

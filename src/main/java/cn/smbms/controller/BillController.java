package cn.smbms.controller;

import cn.smbms.listener.WebBillListener;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.IBillService;
import cn.smbms.service.IProviderService;
import cn.smbms.tools.Constants;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Name;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author knn
 * @create 2020-11-21 11:16
 */
@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private IBillService billService;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private WebBillListener webBillListener;

    @RequestMapping("/upload")
    public String readExcel(MultipartFile uploadFile) {
        try {
            //工作簿
            ExcelReaderBuilder readWorkBook = EasyExcel.read(uploadFile.getInputStream(), Bill.class, webBillListener);
            //工作表
            readWorkBook.sheet().doRead();
            return "redirect:/bill/search";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/error.jsp";
        }
    }


    /**
     * 下载
     *
     * @throws IOException
     */
    @RequestMapping("/download")
    public void download(HttpServletResponse response,
                         String downloadQueryProductName,
                         Integer downloadQueryProviderId,
                         Integer downloadQueryIsPayment,
                         Integer downloadTotalPageCount,
                         @RequestParam(defaultValue = "1") Integer downloadPageIndex) throws IOException {

        System.out.println("---downloadQueryProductName===" + downloadQueryProductName);
        System.out.println("---downloadQueryProviderId===" + downloadQueryProviderId);
        System.out.println("---downloadPageIndex===" + downloadPageIndex);
        System.out.println("---downloadTotalPageCount===" + downloadTotalPageCount);
        System.out.println("---downloadQueryIsPayment===" + downloadQueryIsPayment);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 防止中文乱码
        String fileName = URLEncoder.encode("测试下载", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName + ".xlsx");
        //响应的输入流
        ServletOutputStream outputStream = response.getOutputStream();
        // workbook
        ExcelWriterBuilder writeWorkBook = EasyExcel.write(outputStream, Bill.class);
        // sheet
        ExcelWriterSheetBuilder sheet = writeWorkBook.sheet();
//        sheet.sheetName("目录1");
        Map<String, Object> searchMap = billService.search(downloadQueryProductName, downloadQueryProviderId, downloadQueryIsPayment, downloadPageIndex);

        System.out.println("searchMap==========" + searchMap);
        String bookName = (String) searchMap.get("bookName");
        sheet.sheetName(bookName);
        PageInfo<Bill> pageInfo = (PageInfo<Bill>) searchMap.get("pageInfo");

        System.out.println("pageInfo=============" + pageInfo);

        List<Bill> pageInfoList = pageInfo.getList();
        sheet.doWrite(pageInfoList);
//        sheet.doWrite(billService.query());
    }

    @DeleteMapping("/delete/{billId}")
    @ResponseBody
    public Map<String, String> delete(@PathVariable("billId") Integer billId) {
        Map<String, String> map = new HashMap<>();
        System.out.println(billId);

        boolean flag = billService.delete(billId);
        if (flag) {
            //删除成功
            map.put("delResult", "true");
        } else {
            //删除失败
            map.put("delResult", "false");
        }
        return map;
        //return "redirect:/bill/query";
    }

    @RequestMapping("/modify")
    public String modify(Bill bill) {
        billService.modify(bill);
        return "redirect:/bill/search";
    }

    @RequestMapping("/modifyUI/{billId}")
    public ModelAndView modifyUI(@PathVariable("billId") Integer billId) {
        ModelAndView modelAndView = new ModelAndView();
        Bill bill = billService.view(billId);
        modelAndView.addObject("bill", bill);
        modelAndView.setViewName("billmodify");
        return modelAndView;
    }

    @RequestMapping("/view/{billId}")
    public ModelAndView view(@PathVariable("billId") Integer billId) {
        ModelAndView modelAndView = new ModelAndView();
        Bill bill = billService.view(billId);
        modelAndView.addObject("bill", bill);
        modelAndView.setViewName("billview");
        return modelAndView;
    }

    @RequestMapping("/query")
    public ModelAndView query() {
        ModelAndView modelAndView = new ModelAndView();
        //设置分页的相关参数 当前页+每页显示的条数
        PageHelper.startPage(1, 3);
        List<Bill> billList = billService.query();
        PageInfo<Bill> pageInfo = new PageInfo<Bill>(billList);
        List<Provider> providerList = providerService.getAllProName();
        System.out.println("当前页" + pageInfo.getPageNum());
        System.out.println("每页显示的条数" + pageInfo.getPageSize());
        System.out.println("总条数" + pageInfo.getTotal());
        System.out.println("总页数" + pageInfo.getPages());
        System.out.println("上一页" + pageInfo.getPrePage());
        System.out.println("下一页" + pageInfo.getNextPage());
        System.out.println("是否是第一页" + pageInfo.isIsFirstPage());

        modelAndView.addObject("pageInfo", pageInfo);
        modelAndView.addObject("providerList", providerList);
        modelAndView.setViewName("billlist");
        return modelAndView;
    }

    @RequestMapping("/add")
    public String add(Bill bill, HttpServletRequest request) {

        User userSession = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        bill.setCreatedBy(userSession.getId());
        bill.setCreationDate(new Date());
        billService.add(bill);
        return "redirect:/bill/search";
    }

    @RequestMapping("/likeBill")
    @ResponseBody
    public List<String> likeBill(String queryProductName) throws UnsupportedEncodingException {
        String decode = URLDecoder.decode(queryProductName, "UTF-8");
        List<String> likeBillList = billService.likeBill(decode);
        return likeBillList;
    }


    @RequestMapping("/search")
    public ModelAndView search(String queryProductName, Integer queryProviderId, Integer
            queryIsPayment, @RequestParam(defaultValue = "1") Integer pageIndex) {
        System.out.println("---queryProductName===" + queryProductName);
        System.out.println("---queryProviderId===" + queryProviderId);
        System.out.println("---queryIsPayment===" + queryIsPayment);
        System.out.println("---pageIndex===" + pageIndex);


        ModelAndView modelAndView = new ModelAndView();
        //设置分页的相关参数 当前页+每页显示的条数
//        PageHelper.startPage(pageIndex, 3);

        Map<String, Object> searchMap = billService.search(queryProductName, queryProviderId, queryIsPayment, pageIndex);
        Set<Map.Entry<String, Object>> entrySet = searchMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            System.out.println(entry);
            modelAndView.addObject(entry.getKey(), entry.getValue());
        }
        modelAndView.setViewName("billlist");
        return modelAndView;
    }

    /*@RequestMapping("/search")
    public ModelAndView search(String queryProductName, Integer queryProviderId, Integer queryIsPayment, @RequestParam(defaultValue = "1") Integer pageIndex) {
        System.out.println("---queryProductName===" + queryProductName);
        System.out.println("---queryProviderId===" + queryProviderId);
        System.out.println("---queryIsPayment===" + queryIsPayment);
        System.out.println("---pageIndex===" + pageIndex);


        ModelAndView modelAndView = new ModelAndView();
        //根据id查询供应商的名字
        Provider provide = providerService.getProvideById(queryProviderId);
        String queryProductNameStr = null;
        if (queryProductName != null) {
            queryProductNameStr = "%" + queryProductName + "%";
        }
        String provideName = null;
        if (provide != null) {
            provideName = provide.getProName();
        }
        //设置分页的相关参数 当前页+每页显示的条数
        PageHelper.startPage(pageIndex, 3);

        List<Bill> billListPage = billService.search(queryProductNameStr, provideName, queryIsPayment);
        PageInfo<Bill> pageInfo = new PageInfo<Bill>(billListPage);
        List<Bill> pageInfoList = pageInfo.getList();
        List<Provider> providerList = providerService.getAllProName();
        modelAndView.addObject("providerList", providerList);
        modelAndView.addObject("pageInfo", pageInfo);
        modelAndView.addObject("queryProductName", queryProductName);
        modelAndView.addObject("provide", provide);
        modelAndView.addObject("queryIsPayment", queryIsPayment);
        modelAndView.setViewName("billlist");
        return modelAndView;
    }*/
}

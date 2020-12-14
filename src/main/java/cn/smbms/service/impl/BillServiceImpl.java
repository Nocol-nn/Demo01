package cn.smbms.service.impl;

import cn.smbms.mapper.BillMapper;
import cn.smbms.mapper.ProviderMapper;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.service.IBillService;
import cn.smbms.service.IProviderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author knn
 * @create 2020-11-21 11:17
 */
@Service
public class BillServiceImpl implements IBillService {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public List<Bill> query() {
        System.out.println(billMapper.query());
        return billMapper.query();
    }

    @Override
    public Bill view(Integer billId) {
        return billMapper.view(billId);
    }

    @Override
    public void modify(Bill bill) {
        billMapper.modify(bill);
    }

    @Override
    public Boolean delete(Integer billId) {
        int i = billMapper.delete(billId);
        return i >= 0;
    }

    @Override
    public void add(Bill bill) {
        billMapper.add(bill);

        //获得新增的订单ID
        Integer billId = bill.getId();
        String billIdStr = "";
        if (billId <= 9) {
            billIdStr = "00" + billId;
        } else if (billId <= 99) {
            billIdStr = "0" + billId;
        } else {
            billIdStr = "" + billId;
        }
        bill.setBillCode("BILL" + new SimpleDateFormat("yyyy").format(new Date()) + "_" + billIdStr);
        billMapper.updateBillCodeById(bill);

        System.out.println("导入的数据");
        System.out.println(bill);
    }

    /*@Override
    public List<Bill> search(String queryProductName, String queryProvideName, Integer queryIsPayment) {
        return billMapper.search(queryProductName, queryProvideName, queryIsPayment);
    }*/

    @Override
    public Map<String, Object> search(String queryProductName, Integer queryProviderId, Integer queryIsPayment, Integer pageIndex) {

        Map<String, Object> map = new HashMap<>();
        //根据id查询供应商的名字
        Provider provide = providerMapper.getProvideById(queryProviderId);
        String queryProductNameStr = null;
        if (queryProductName != null) {
            queryProductNameStr = "%" + queryProductName + "%";
        }
        String provideName = null;
        if (provide != null) {
            provideName = provide.getProName();
        }
        // 查询所有的供应商
        List<Provider> providerList = providerMapper.getAllProName();


        System.out.println("供应商" + providerList);


        //设置分页的相关参数 当前页+每页显示的条数
        PageHelper.startPage(pageIndex, 3);
        List<Bill> billListPage = billMapper.search(queryProductNameStr, provideName, queryIsPayment);
        PageInfo<Bill> pageInfo = new PageInfo<Bill>(billListPage);
        map.put("providerList", providerList);
        map.put("pageInfo", pageInfo);
        map.put("queryProductName", queryProductName);
        map.put("provide", provide);
        map.put("queryIsPayment", queryIsPayment);

        StringBuilder builder = new StringBuilder();
        if (queryProductName != null) {
            builder.append(queryProductName);
        }
        if (provide != null) {
            builder.append(provide.getProName());
        }

        if (queryIsPayment != null) {
            if (queryIsPayment == 1) {
                builder.append("未支付");
            } else if (queryIsPayment == 2) {
                builder.append("已支付");
            }
        }
        if ("".equals(builder.toString())) {
            builder.append("目录1");
        }
        System.out.println("builder====" + builder.toString());
        map.put("bookName", builder.toString());
        System.out.println(map);
        return map;
    }

    @Override
    public List<String> likeBill(String queryProductName) {
        return billMapper.likeBill("%" + queryProductName + "%");
    }
}

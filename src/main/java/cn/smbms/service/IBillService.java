package cn.smbms.service;

import cn.smbms.pojo.Bill;

import java.util.List;
import java.util.Map;

/**
 * @author knn
 * @create 2020-11-21 11:17
 */
public interface IBillService {

    /**
     * 查询所有的订单
     *
     * @return
     */
    List<Bill> query();

    /**
     * 根据id查看订单详情
     *
     * @param billId
     * @return
     */
    Bill view(Integer billId);

    /**
     * 根据id查看订单
     *
     * @param bill
     * @return
     */
    void modify(Bill bill);

    /**
     * 根据id删除订单
     *
     * @param billId
     */
    Boolean delete(Integer billId);

    /**
     * 添加订单
     *
     * @param bill
     */
    void add(Bill bill);

    /**
     * 查询
     *
     * @param queryProductName
     * @param queryProvideName
     * @return
     */
    Map<String, Object> search(String queryProductName, Integer queryProvideName, Integer queryIsPayment, Integer pageIndex);

    /**
     * 联想查询
     *
     * @param
     * @return
     */
    List<String> likeBill(String queryProductName);
}

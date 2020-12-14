package cn.smbms.mapper;

import cn.smbms.pojo.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-21 11:18
 */
public interface BillMapper {

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
     * 根据id修改订单
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
    Integer delete(Integer billId);

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
    List<Bill> search(@Param("queryProductName") String queryProductName, @Param("queryProvideName") String queryProvideName, @Param("queryIsPayment") Integer queryIsPayment);

    /**
     * 根据ID修改订单编号
     * @param bill
     */
    void updateBillCodeById(Bill bill);

    /**
     * 联想查询
     * @param queryProductName
     * @return
     */
    List<String> likeBill(String queryProductName);
}

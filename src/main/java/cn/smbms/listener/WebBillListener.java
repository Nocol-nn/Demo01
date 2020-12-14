package cn.smbms.listener;

import cn.smbms.pojo.Bill;
import cn.smbms.service.IBillService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author knn
 * @create 2020-11-26 8:50
 */
@Component
@Scope("prototype")  // 作者要求每次读取都要使用新的Listener
public class WebBillListener extends AnalysisEventListener<Bill> {

    @Autowired
    private IBillService billService;

    List<Bill> bills = new ArrayList<Bill>();
    private final int BATCH_SAVE_NUM = 3;
    private int count = 0;

    @Override
    public void invoke(Bill bill, AnalysisContext analysisContext) {
        bills.add(bill);
        if (++count % BATCH_SAVE_NUM == 0) {
            for (Bill uploadBill : bills) {
                billService.add(uploadBill);
            }
            bills.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

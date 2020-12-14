package cn.smbms.service.impl;

import cn.smbms.mapper.ProviderMapper;
import cn.smbms.mapper.ProvincialMapper;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Provincial;
import cn.smbms.service.IProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-21 14:16
 */
@Service
public class ProviderServiceImpl implements IProviderService {

    @Autowired
    private ProviderMapper providerMapper;
    @Autowired
    private ProvincialMapper provincialMapper;

    @Override
    public List<Provider> getAllProName() {
        return providerMapper.getAllProName();
    }

    @Override
    public Provider getProvideById(Integer providerId) {
        return providerMapper.getProvideById(providerId);
    }

    @Override
    public void modify(Provider provider) {
        providerMapper.modify(provider);
    }

    @Override
    public boolean delete(Integer id) {

        int i = providerMapper.delete(id);
        return i >= 0;
    }

    @Override
    public void add(Provider provider) {
        // 通过provincialId查询省份编码
        Provincial provincial = provincialMapper.getProvincialCodeById(provider.getProvincialId());
        provider.setIsRemove(1);//0 已移除 ；1未移除
        providerMapper.add(provider);

        Integer providerId = provider.getId();
        String providerIdStr = "";
        if (providerId <= 9) {
            providerIdStr = "00" + providerId;
        } else if (providerId <= 99) {
            providerIdStr = "0" + providerId;
        } else {
            providerIdStr = "" + providerId;
        }
        String provincialCode = provincial.getProvincialCode();

        System.out.println(provincialCode);
        provider.setProCode(provincialCode + "_GYS" + providerIdStr);

        //修改供应商编号
        providerMapper.updateProCodeById(provider);
    }

    @Override
    public List<Provider> search(String queryProCode, String queryProName) {
        return providerMapper.search(queryProCode, queryProName);
    }

    @Override
    public boolean getProvideByName(String proName) {
        Provider provide = providerMapper.getProvideByName(proName);
        System.out.println(provide);
        return provide != null;
    }

    @Override
    public void updateIsRemoveById(Integer id, int isRemove) {
        providerMapper.updateIsRemoveById(id, isRemove);
    }

}

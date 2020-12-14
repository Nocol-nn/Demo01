package cn.smbms.service.impl;

import cn.smbms.mapper.ProvincialMapper;
import cn.smbms.pojo.Provincial;
import cn.smbms.service.IProviderService;
import cn.smbms.service.IProvincialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-25 10:24
 */
@Service
public class ProvincialServiceImpl implements IProvincialService {

    @Autowired
    private ProvincialMapper provincialMapper;


    @Override
    public List<Provincial> getAllProvincial() {
        return provincialMapper.getAllProvincial();
    }
}

package cn.smbms.service;

import cn.smbms.pojo.Provincial;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-25 10:24
 */
public interface IProvincialService {
    /**
     * 获得所有的省份
     * @return
     */
    List<Provincial> getAllProvincial();
}

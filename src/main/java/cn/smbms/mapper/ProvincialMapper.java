package cn.smbms.mapper;

import cn.smbms.pojo.Provincial;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-25 10:29
 */
public interface ProvincialMapper {

    /**
     * 获得所有的省份
     *
     * @return
     */
    List<Provincial> getAllProvincial();


    /**
     * 通过provincialId查询省份编码
     *
     * @return
     */
    Provincial getProvincialCodeById(Integer id);
}

package cn.smbms.mapper;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author knn
 * @create 2020-11-21 14:17
 */
public interface ProviderMapper {

    /**
     * 查询所有的供应商信息
     *
     * @return
     */
    List<Provider> getAllProName();

    /**
     * 根据id查询供应商信息
     *
     * @param providerId
     * @return
     */
    Provider getProvideById(Integer providerId);

    /**
     * 修改供应商信息
     *
     * @param provider
     */
    void modify(Provider provider);

    /**
     * 根据id删除供应商
     *
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 添加供应商
     *
     * @param provider
     */
    void add(Provider provider);

    /**
     * 查询
     *
     * @param queryProCode
     * @param queryProName
     * @return
     */
    List<Provider> search(@Param("queryProCode") String queryProCode, @Param("queryProName") String queryProName);

    /**
     * 修改供应商编号
     *
     * @param provider
     */
    void updateProCodeById(Provider provider);

    /**
     * 根据名称判断供应商是否存在
     *
     * @param proName
     * @return
     */
    Provider getProvideByName(String proName);

    /**
     * 根据ID修改移除信息
     *
     * @param id
     * @param isRemove
     */
    void updateIsRemoveById(@Param("id") Integer id, @Param("isRemove") int isRemove);
}

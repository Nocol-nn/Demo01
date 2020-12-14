package cn.smbms.pojo;

/**
 * @author knn
 * @create 2020-11-25 10:22
 */
public class Provincial {
    private int id;
    private String provincialName;
    private String provincialCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvincialName() {
        return provincialName;
    }

    public void setProvincialName(String provincialName) {
        this.provincialName = provincialName;
    }

    public String getProvincialCode() {
        return provincialCode;
    }

    public void setProvincialCode(String provincialCode) {
        this.provincialCode = provincialCode;
    }

    @Override
    public String toString() {
        return "Provincial{" +
                "id=" + id +
                ", provincialName='" + provincialName + '\'' +
                ", provincialCode='" + provincialCode + '\'' +
                '}';
    }
}

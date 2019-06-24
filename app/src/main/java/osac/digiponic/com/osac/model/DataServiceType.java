package osac.digiponic.com.osac.model;

public class DataServiceType {

    String id, type, name, desc;

    public DataServiceType() {
    }

    public DataServiceType(String id, String type, String name, String desc) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

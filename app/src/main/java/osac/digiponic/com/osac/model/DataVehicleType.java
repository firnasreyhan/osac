package osac.digiponic.com.osac.model;

public class DataVehicleType {

    String id, types, name, desc;

    public DataVehicleType(String id, String types, String name, String desc) {
        this.id = id;
        this.types = types;
        this.name = name;
        this.desc = desc;
    }

    public DataVehicleType() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
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

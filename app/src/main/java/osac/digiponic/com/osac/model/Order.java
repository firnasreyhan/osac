package osac.digiponic.com.osac.model;

public class Order {

    public static final String TABLE_NAME = "transaction_table";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_VEHICLE_TYPE = "vehicle_type";
    public static final String COLUMN_SERVICE_TYPE = "services_type";
    public static final String COLUMN_IMAGE = "image";

    private int id;
    private String name;
    private String price;
    private String vehicle_type;
    private String service_type;
    private String image;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_PRICE + " TEXT, " +
            COLUMN_VEHICLE_TYPE + " TEXT, " +
            COLUMN_SERVICE_TYPE + " TEXT, " +
            COLUMN_IMAGE + " TEXT)";

    public Order() {
    }


    public Order(int id, String name, String price, String vehicle_type, String service_type, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.vehicle_type = vehicle_type;
        this.service_type = service_type;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
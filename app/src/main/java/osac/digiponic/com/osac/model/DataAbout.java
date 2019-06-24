package osac.digiponic.com.osac.model;

public class DataAbout {

    public static final String TABLE_NAME = "about_table";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAMESHORT = "nameShort";
    public static final String COLUMN_NAMELONG = "nameLong";
    public static final String COLUMN_PHONENUMBER = "phone_number";
    public static final String COLUMN_ADDRESS = "address";

    private String id;
    private String nameShort = "OSAC";
    private String nameLong = "One Stop Auto Care";
    private String phone_number;
    private String address;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_NAMESHORT + " TEXT UNIQUE, " +
            COLUMN_NAMELONG + " TEXT, " +
            COLUMN_PHONENUMBER + " TEXT, " +
            COLUMN_ADDRESS + " TEXT)";

    public DataAbout() {
    }

    public DataAbout(String id, String nameShort, String nameLong, String phone_number, String address) {
        this.id = id;
        this.nameShort = nameShort;
        this.nameLong = nameLong;
        this.phone_number = phone_number;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public String getNameLong() {
        return nameLong;
    }

    public void setNameLong(String nameLong) {
        this.nameLong = nameLong;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

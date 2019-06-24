package osac.digiponic.com.osac.model;

public class DataBluetoothDevice {

    public static final String TABLE_NAME = "device_table";

    public static final String COLUMN_MAC_ADDRESS = "mac_address";
    public static final String COLUMN_NAME = "name";

    private String macAddress, name;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (" + COLUMN_MAC_ADDRESS + " TEXT PRIMARY KEY, " +
            COLUMN_NAME + " TEXT)";

    public DataBluetoothDevice() {
    }

    public DataBluetoothDevice(String macAddress, String name) {
        this.macAddress = macAddress;
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

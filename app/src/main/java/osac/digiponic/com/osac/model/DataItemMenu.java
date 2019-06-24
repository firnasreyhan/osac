package osac.digiponic.com.osac.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataItemMenu {

    @SerializedName("_itemID")
    @Expose
    private String _itemID;
    @SerializedName("_itemName")
    @Expose
    private String _itemName;
    @SerializedName("_itemPrice")
    @Expose
    private String _itemPrice;
    @SerializedName("_itemVehicleType")
    @Expose
    private String _itemVehicleType;
    @SerializedName("_itemVehicleTypeID")
    @Expose
    private String _itemVehicleTypeID;
    @SerializedName("_itemType")
    @Expose
    private String _itemType;
    @SerializedName("_itemTypeID")
    @Expose
    private String _itemTypeID;
    @SerializedName("_itemImage")
    @Expose
    private String _itemImage;
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public DataItemMenu(String _itemID, String _itemName, String _itemPrice, String _itemVehicleType, String _itemType, String _itemImage) {
        this._itemID = _itemID;
        this._itemName = _itemName;
        this._itemPrice = _itemPrice;
        this._itemVehicleType = _itemVehicleType;
        this._itemType = _itemType;
        this._itemImage = _itemImage;
    }

    public DataItemMenu(String _itemID, String _itemName, String _itemPrice, String _itemVehicleType, String _itemVehicleTypeID, String _itemType, String _itemTypeID, String _itemImage) {
        this._itemID = _itemID;
        this._itemName = _itemName;
        this._itemPrice = _itemPrice;
        this._itemVehicleType = _itemVehicleType;
        this._itemVehicleTypeID = _itemVehicleTypeID;
        this._itemType = _itemType;
        this._itemTypeID = _itemTypeID;
        this._itemImage = _itemImage;
    }

    public String get_itemName() {
        return _itemName;
    }

    public void set_itemName(String _itemName) {
        this._itemName = _itemName;
    }

    public int get_itemPrice() {
        return Integer.parseInt(_itemPrice);
    }

    public void set_itemPrice(String _itemPrice) {
        this._itemPrice = _itemPrice;
    }

    public String get_itemID() {
        return _itemID;
    }

    public void set_itemID(String _itemID) {
        this._itemID = _itemID;
    }

    public String get_itemVehicleType() {
        return _itemVehicleType;
    }

    public void set_itemVehicleType(String _itemVehicleType) {
        this._itemVehicleType = _itemVehicleType;
    }

    public String get_itemType() {
        return _itemType;
    }

    public void set_itemType(String _itemType) {
        this._itemType = _itemType;
    }

    public String get_itemImage() {
        return _itemImage;
    }

    public void set_itemImage(String _itemImage) {
        this._itemImage = _itemImage;
    }
}

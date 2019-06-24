package osac.digiponic.com.osac.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataBrand {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("kode")
    @Expose
    private String kode;
    @SerializedName("kode_tipe")
    @Expose
    private String kode_tipe;
    @SerializedName("gambar")
    @Expose
    private String gambar;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;

    public DataBrand(String id, String kode, String kode_tipe, String gambar, String keterangan) {
        this.id = id;
        this.kode = kode;
        this.kode_tipe = kode_tipe;
        this.gambar = gambar;
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getKode_tipe() {
        return kode_tipe;
    }

    public void setKode_tipe(String kode_tipe) {
        this.kode_tipe = kode_tipe;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}

package osac.digiponic.com.osac.model;

public class DataVehicle {

    private String id, kode_general,
            kode, id_jenis_kendaraan, keterangan,
            gambar;

    public DataVehicle(String id, String kode_general, String kode, String id_jenis_kendaraan, String keterangan, String gambar) {
        this.id = id;
        this.kode_general = kode_general;
        this.kode = kode;
        this.id_jenis_kendaraan = id_jenis_kendaraan;
        this.keterangan = keterangan;
        this.gambar = gambar;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode_general() {
        return kode_general;
    }

    public void setKode_general(String kode_general) {
        this.kode_general = kode_general;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getId_jenis_kendaraan() {
        return id_jenis_kendaraan;
    }

    public void setId_jenis_kendaraan(String id_jenis_kendaraan) {
        this.id_jenis_kendaraan = id_jenis_kendaraan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}

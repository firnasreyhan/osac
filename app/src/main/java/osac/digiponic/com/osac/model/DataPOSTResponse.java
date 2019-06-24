package osac.digiponic.com.osac.model;

import java.util.Date;

public class DataPOSTResponse {

    private String waktu;
    private String kode_transaksi;
    private String nomor_polisi;
    private String jenis_kendaraan;
    private String merek_kendaraan;
    private String nama_kendaraan;
    private String subtotal;
    private String diskon_tipe;
    private String diskon;
    private String total;
    private String status;
    private String metode_pembayaran;
    private String nominal_bayar;

    public DataPOSTResponse(String waktu, String kode_transaksi, String nomor_polisi, String jenis_kendaraan, String merek_kendaraan, String nama_kendaraan, String subtotal, String diskon_tipe, String diskon, String total, String status, String metode_pembayaran, String nominal_bayar) {
        this.waktu = waktu;
        this.kode_transaksi = kode_transaksi;
        this.nomor_polisi = nomor_polisi;
        this.jenis_kendaraan = jenis_kendaraan;
        this.merek_kendaraan = merek_kendaraan;
        this.nama_kendaraan = nama_kendaraan;
        this.subtotal = subtotal;
        this.diskon_tipe = diskon_tipe;
        this.diskon = diskon;
        this.total = total;
        this.status = status;
        this.metode_pembayaran = metode_pembayaran;
        this.nominal_bayar = nominal_bayar;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getKode_transaksi() {
        return kode_transaksi;
    }

    public String getNomor_polisi() {
        return nomor_polisi;
    }

    public String getJenis_kendaraan() {
        return jenis_kendaraan;
    }

    public String getMerek_kendaraan() {
        return merek_kendaraan;
    }

    public String getNama_kendaraan() {
        return nama_kendaraan;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public String getDiskon_tipe() {
        return diskon_tipe;
    }

    public String getDiskon() {
        return diskon;
    }

    public String getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String getMetode_pembayaran() {
        return metode_pembayaran;
    }

    public String getNominal_bayar() {
        return nominal_bayar;
    }
}

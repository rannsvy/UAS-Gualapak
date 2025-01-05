package model;

public class ElektronikRumahTangga extends Produk implements Garansi {
    private int konsumsiDaya;
    private int durasiGaransi; 

    public ElektronikRumahTangga(String id, String nama, int harga, String merek, int konsumsiDaya, int durasiGaransi) {
        super(id, nama, harga, merek);
        this.konsumsiDaya = konsumsiDaya;
        this.durasiGaransi = durasiGaransi;
    }

    public int getKonsumsiDaya() {
        return konsumsiDaya;
    }

    public void setKonsumsiDaya(int konsumsiDaya) {
        this.konsumsiDaya = konsumsiDaya;
    }

    @Override
    public int getDurasiGaransi() {
        return durasiGaransi;
    }

    public void setDurasiGaransi(int durasiGaransi) {
        this.durasiGaransi = durasiGaransi;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf("        | %-20s | %-14s | %-16s\n", " ", " ", "Konsumsi Daya: " + konsumsiDaya + "W");
        System.out.printf("        | %-20s | %-14s | %-16s\n", " ", " ", "Garansi: " + durasiGaransi + " bulan");
    }

    public int hitungDiskon() {
        return (getHarga() * 90)/100; 
    }
}

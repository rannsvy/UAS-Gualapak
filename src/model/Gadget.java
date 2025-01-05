package model;

public class Gadget extends Produk implements Garansi {
    private String sistemOperasi;
    private int durasiGaransi; 
    private int ram;  
    private int penyimpanan; 

    public Gadget(String id, String nama, int harga, String merek, String sistemOperasi, int durasiGaransi, int ram, int penyimpanan) {
        super(id, nama, harga, merek);
        this.sistemOperasi = sistemOperasi;
        this.durasiGaransi = durasiGaransi;
        this.ram = ram;
        this.penyimpanan = penyimpanan;
    }

    public String getSistemOperasi() {
        return sistemOperasi;
    }

    public void setSistemOperasi(String sistemOperasi) {
        this.sistemOperasi = sistemOperasi;
    }

    @Override
    public int getDurasiGaransi() {
        return durasiGaransi;
    }

    public void setDurasiGaransi(int durasiGaransi) {
        this.durasiGaransi = durasiGaransi;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getPenyimpanan() {
        return penyimpanan;
    }

    public void setPenyimpanan(int penyimpanan) {
        this.penyimpanan = penyimpanan;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf("        | %-20s | %-14s | %-16s\n", " ", " ", "Sistem Operasi: " + sistemOperasi);
        System.out.printf("        | %-20s | %-14s | %-16s\n", " ", " ", "Garansi: " + durasiGaransi + " bulan");
        System.out.printf("        | %-20s | %-14s | %-16s\n", " ", " ", "RAM: " + ram + " GB");
        System.out.printf("        | %-20s | %-14s | %-16s\n", " ", " ", "Penyimpanan: " + penyimpanan + " GB");
    }

    public int hitungDiskon() {
        return (getHarga() * 85) / 100; 
    }
    
}

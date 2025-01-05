package model;

import java.util.Random;

public class Pesanan {
    private String idPesanan;
    private String username;
    private String produk;
    private int jumlah;
    private double totalBelanja;
    private String metodePembayaran;
    private String alamat;
    private String status;

    private static final Random random = new Random();

    public Pesanan(String username, String produk, int jumlah, double totalBelanja, String metodePembayaran, String alamat) {
        this.idPesanan = generateUniqueId(); 
        this.username = username;
        this.produk = produk;
        this.jumlah = jumlah;
        this.totalBelanja = totalBelanja;
        this.metodePembayaran = metodePembayaran;
        this.alamat = alamat;
        this.status = "Pesanan Sedang Diproses"; 
    }

    public Pesanan(String idPesanan, String username, String produk, int jumlah, double totalBelanja, String metodePembayaran, String alamat) {
        this.idPesanan = idPesanan;
        this.username = username;
        this.produk = produk;
        this.jumlah = jumlah;
        this.totalBelanja = totalBelanja;
        this.metodePembayaran = metodePembayaran;
        this.alamat = alamat;
        this.status = "Pesanan Sedang Diproses"; 
    }

    private String generateUniqueId() {
        int randomNumber = random.nextInt(9000) + 1000; 
        return "ORD-" + randomNumber; 
    }

    public String getIdPesanan() {
        return idPesanan;
    }

    public String getUsername() { return username; }
    public String getProduk() { return produk; }
    public int getJumlah() { return jumlah; }
    public double getTotalBelanja() { return totalBelanja; }
    public String getMetodePembayaran() { return metodePembayaran; }
    public String getAlamat() { return alamat; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
    return String.format(
        "ID Pesanan: %s, Username: %s, Produk: %s, Jumlah: %d, Total: %.2f, Metode Pembayaran: %s, Alamat: %s, Status: %s",
        idPesanan, username, produk, jumlah, totalBelanja, metodePembayaran, alamat, status
    );
}


}

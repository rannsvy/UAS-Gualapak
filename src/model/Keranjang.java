package model;

public class Keranjang {
    private Produk produk;
    private int jumlah;

    public Keranjang(Produk produk, int jumlah) {
        this.produk = produk;
        this.jumlah = jumlah;
    }

    public Produk getProduk() {
        return produk;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void tambahJumlah(int jumlah) {
        this.jumlah += jumlah;
    }

    public double getTotalHarga() {
        return produk.getHarga() * jumlah;
    }
}

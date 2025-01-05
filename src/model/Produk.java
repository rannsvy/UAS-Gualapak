package model;

import java.util.Random;

public class Produk {
    private String id;
    private String nama;
    private int harga;
    private String merek;

    public Produk(String id, String nama, int harga, String merek) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.merek = merek;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public void tampilkanInfo() {
        System.out.printf("%-7s | %-20s | Rp%-12d | %-16s \n", 
            id, nama, harga, merek);
    }

    public static int hitungOngkir(int jarak, int pilihanPengiriman) {
        int ongkir = 0;
        switch (pilihanPengiriman) {
            case 1: 
                ongkir = jarak * 2000; 
                break;
            case 2: 
                ongkir = jarak * 2500; 
                break;
            case 3: 
                ongkir = jarak * 1800; 
                break;
            default:
                System.out.println("Pilihan tidak valid, ongkir dihitung dengan J&T");
                ongkir = jarak * 2000;
                break;
        }
        return ongkir;
    }
    
    public static String generateID(int tipeProduk) {
        Random rand = new Random();
        String prefix = "P-" + tipeProduk;
        int randomNumber = rand.nextInt(900) + 100;
        return prefix + String.format("%03d", randomNumber);
    }

    public static int hitungDiskonPembayaran(int total, String metodePembayaran) {
        return switch (metodePembayaran) {
            case "Kartu Kredit BCA" -> (total*10)/100;
            case "Kartu Kredit CIMB Niaga" -> (total * 12)/100;
            case "Mega Gold Card" -> (total * 15)/100;
            default -> 0;
        };
    }
}


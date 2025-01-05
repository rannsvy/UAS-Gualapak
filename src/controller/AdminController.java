package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Admin;
import model.ElektronikRumahTangga;
import model.Gadget;
import model.Pesanan;
import model.Produk;
import view.AdminView;

public class AdminController {
    private final Admin admin;
    private final AdminView adminView;

    public AdminController(Admin admin, AdminView adminView) {
        this.admin = admin;
        this.adminView = adminView;
    }

    public void tampilkanMenuAdmin(Scanner scanner) {
        boolean adminBerjalan = true;
        while (adminBerjalan) {
            adminView.tampilkanMenuAdmin();
            int pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1 -> menuTambahProduk(admin, scanner);
                case 2 -> {
                    System.out.print("| Masukkan ID produk yang ingin dihapus: ");
                    String id = scanner.next();
                    admin.hapusProduk(id);
                }
                case 3 -> {
                    System.out.print("| Masukkan ID produk yang ingin diubah harganya: ");
                    String id = scanner.next();
                    System.out.print("| Masukkan harga baru: ");
                    int harga = scanner.nextInt();
                    admin.ubahHargaProduk(id, harga);
                }
                case 4 -> adminView.lihatDaftarProduk(admin.getDaftarProduk());
                case 5 -> adminView.lihatPesanan(admin.getDaftarPesanan());
                case 6 -> prosesPesanan(admin.getDaftarPesanan(), scanner);
                case 7 -> adminBerjalan = false;
                default -> adminView.tampilkanPesan("Pilihan tidak valid!");
            }
        }
    }

    public void menuTambahProduk(Admin admin, Scanner scanner) {
        adminView.MenuTambahProduk();
        int tipe = scanner.nextInt();
        scanner.nextLine();

        String id = Produk.generateID(tipe);
        System.out.print("| Masukkan nama produk\t\t: ");
        String nama = scanner.nextLine();
        System.out.print("| Masukkan harga produk\t\t: ");
        int harga = scanner.nextInt();
        scanner.nextLine();
        System.out.print("| Masukkan merek produk\t\t: ");
        String merek = scanner.nextLine();

        switch (tipe) {
            case 1 -> {
                Produk produk = new Produk(id, nama, harga, merek);
                admin.tambahProduk(produk);
                admin.simpanProdukKeFile(produk);
            }
            case 2 -> {
                System.out.print("| Masukkan daya (W)\t\t: ");
                int daya = scanner.nextInt();
                System.out.print("| Masukkan garansi (bulan)\t: ");
                int garansi = scanner.nextInt();
                scanner.nextLine();
                ElektronikRumahTangga eProduk = new ElektronikRumahTangga(id, nama, harga, merek, daya, garansi);
                admin.tambahProduk(eProduk);
                admin.simpanProdukKeFile(eProduk);
            }
            case 3 -> {
                System.out.print("| Masukkan sistem operasi\t: ");
                String os = scanner.next();
                System.out.print("| Masukkan RAM (GB)\t\t: ");
                int ram = scanner.nextInt();
                System.out.print("| Masukkan storage (GB)\t\t: ");
                int storage = scanner.nextInt();
                System.out.print("| Masukkan garansi (bulan)\t: ");
                int garansi = scanner.nextInt();
                scanner.nextLine();
                Gadget gProduk = new Gadget(id, nama, harga, merek, os, garansi, ram, storage);
                admin.tambahProduk(gProduk);
                admin.simpanProdukKeFile(gProduk);
            }
            default -> System.out.println("Pilihan tidak valid!");
        }
    }

    public void prosesPesanan(List<Pesanan> daftarPesanan, Scanner scanner) {
        admin.muatPesananDariFile();
        if (daftarPesanan.isEmpty()) {
            System.out.println("Tidak ada pesanan yang dapat diproses.");
            return;
        }
    
        List<Pesanan> pesananDapatDiproses = new ArrayList<>();
        for (Pesanan pesanan : daftarPesanan) {
            if (pesanan.getStatus().equalsIgnoreCase("Pesanan Sedang Diproses")) {
                pesananDapatDiproses.add(pesanan);
            }
        }
    
        if (pesananDapatDiproses.isEmpty()) {
            System.out.println("Tidak ada pesanan dengan status 'Pesanan Sedang Diproses'.");
            return;
        }

        adminView.printPesananTable(pesananDapatDiproses);
        
        boolean prosesBerjalan = true;
    
        while (prosesBerjalan) {
            System.out.print("Masukkan ID pesanan yang ingin diproses (atau ketik 'batal' untuk kembali ke menu): ");
            String idPesanan = scanner.nextLine();
    
            if (idPesanan.equalsIgnoreCase("batal")) {
                System.out.println("Kembali ke menu utama.");
                return;
            }
    
            Pesanan pesananDitemukan = null;
            for (Pesanan pesanan : pesananDapatDiproses) {
                if (pesanan.getIdPesanan().equals(idPesanan)) {
                    pesananDitemukan = pesanan;
                    break;
                }
            }
    
            if (pesananDitemukan == null) {
                System.out.println(" ");
                continue; 
            }

            System.out.println("1. Set Status: Dalam Pengiriman");
            System.out.println("2. Tolak Pesanan");
            System.out.print("Pilih aksi: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); 
    
            if (pilihan == 1) {
                pesananDitemukan.setStatus("Dalam Pengiriman");
                System.out.println("Pesanan berhasil diubah menjadi 'Dalam Pengiriman'.");
            } else if (pilihan == 2) {
                System.out.print("Masukkan alasan penolakan: ");
                String alasan = scanner.nextLine();
                pesananDitemukan.setStatus("Pesanan Ditolak: " + alasan);
                System.out.println("Pesanan berhasil ditolak.");
            } else {
                System.out.println("Pilihan tidak valid! Silakan ulangi.");
                continue; 
            }
    
            admin.perbaruiFilePesanan(); 
            prosesBerjalan = false; 
        }
    }
}
package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Admin;
import model.Pengguna;
import model.Pesanan;
import model.Produk;
import view.AdminView;
import view.PenggunaView;

public class PenggunaController {
    private final Pengguna pengguna;
    private final PenggunaView penggunaView;
    private final AdminView adminView;

    public PenggunaController(Pengguna pengguna, PenggunaView penggunaView, AdminView adminView) {
        this.pengguna = pengguna;
        this.penggunaView = penggunaView;
        this.adminView = adminView;
    }

    public void tampilkanMenuPengguna(Admin admin, String username, Scanner scanner) {
        boolean penggunaBerjalan = true;
        while (penggunaBerjalan) {
            penggunaView.tampilkanMenuPengguna(); 
            int pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1 -> {
                    System.out.println("| Daftar Produk:");
                    adminView.lihatDaftarProduk(admin.getDaftarProduk());

                    boolean menambahProduk = true;
                    while (menambahProduk) {
                        System.out.print("| Masukkan ID produk untuk ditambahkan ke keranjang (atau ketik 'exit' untuk keluar): ");
                        String id = scanner.next();

                        if (id.equalsIgnoreCase("exit")) {
                            menambahProduk = false;
                            break;
                        }

                        System.out.print("| Masukkan jumlah produk: ");
                        int jumlah = scanner.nextInt();

                        boolean produkDitemukan = false;
                        for (Produk produk : admin.getDaftarProduk()) {
                            if (produk.getId().equals(id)) {
                                pengguna.tambahKeKeranjang(username, produk, jumlah);
                                produkDitemukan = true;
                                break;
                            }
                        }

                        if (!produkDitemukan) {
                            penggunaView.tampilkanPesan("| ID produk tidak ditemukan!");
                            System.out.println("|--------------------------------------------------\n");
                        }
                    }
                }
                case 2 -> penggunaView.lihatKeranjang(username);
                case 3 -> pengguna.checkout(username, admin);
                case 4 -> pengguna.lihatRiwayat(username);
                case 5 -> penggunaView.lihatPesananPengguna(username, admin);
                case 6 -> konfirmasiPesananSelesai(username, admin);
                case 7 -> penggunaBerjalan = false;
                default -> penggunaView.tampilkanPesan("Pilihan tidak valid!");
            }
        }
    }

    public void konfirmasiPesananSelesai(String username, Admin admin) {
        List<Pesanan> pesananDalamPengiriman = new ArrayList<>();
        for (Pesanan pesanan : admin.getDaftarPesanan()) {
            if (pesanan.getUsername().equals(username) && pesanan.getStatus().equals("Dalam Pengiriman")) {
                pesananDalamPengiriman.add(pesanan);
            }
        }
    
        System.out.println("+------------------------------------------------------------- Daftar Pesanan Anda Dalam Pengiriman -----------------------------------------------------------+");

        if (pesananDalamPengiriman.isEmpty()) {
            System.out.println("| Tidak ada pesanan dengan status 'Dalam Pengiriman'.");
            System.out.println("+-----------------------------------------------+");
            return;
        }
        adminView.printPesananTable(pesananDalamPengiriman);
    
        Scanner scanner = new Scanner(System.in);
        System.out.print("| Masukkan ID pesanan yang ingin dikonfirmasi selesai: ");
        String idPesanan = scanner.nextLine();
    
        for (Pesanan pesanan : pesananDalamPengiriman) {
            if (pesanan.getIdPesanan().equals(idPesanan)) {
                pesanan.setStatus("Sudah Terkirim");
                admin.perbaruiFilePesanan();
                System.out.println("| Pesanan berhasil dikonfirmasi sebagai 'Sudah Terkirim'.");
                System.out.println("|-------------------------------------------------------\n");
                return;
            }
        }
        System.out.println("| Pesanan dengan ID tersebut tidak ditemukan.");
        System.out.println("|--------------------------------------------------\n");
    }
    
}

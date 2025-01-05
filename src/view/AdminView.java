package view;

import java.util.List;
import model.Admin;
import model.Pesanan;
import model.Produk;

public class AdminView {
    private final Admin admin;

    public AdminView(Admin admin) {
        this.admin = admin;
    }

    public void lihatDaftarProduk(List<Produk> daftarProduk) {
        admin.bacaProdukDariFile();
        if (daftarProduk.isEmpty()) {
            System.out.println("Tidak ada produk saat ini.");
        } else {
            System.out.println("----------------------------------------------------------------------------");
            System.out.printf("%-7s | %-20s | %-14s | %-16s\n", "ID", "Nama", "Harga", "Merek");
            System.out.println("----------------------------------------------------------------------------");

            for (Produk produk : daftarProduk) {
                produk.tampilkanInfo();
            }
            System.out.println("----------------------------------------------------------------------------");
        }
    }

    public void printPesananTable(List<Pesanan> pesananList) {
        if (pesananList.isEmpty()) {
            System.out.println("Tidak ada data pesanan untuk ditampilkan.");
            return;
        }
    
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.printf("| %-10s | %-10s | %-20s | %-6s | %-15s | %-26s | %-25s | %-23s |\n",
                "ID Pesanan", "Username", "Produk", "Jumlah", "Total", "Metode Pembayaran", "Alamat", "Status");
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------------------------------------------+");
    
        for (Pesanan pesanan : pesananList) {
            System.out.printf("| %-10s | %-10s | %-20s | %-6d | %-15f | %-26s | %-25s | %-23s |\n",
                    pesanan.getIdPesanan(),
                    pesanan.getUsername(),
                    pesanan.getProduk(),
                    pesanan.getJumlah(),
                    pesanan.getTotalBelanja(),
                    pesanan.getMetodePembayaran(),
                    pesanan.getAlamat(),
                    pesanan.getStatus());
        }
    
        
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------------------------------------------+");
    }

    public void lihatPesanan(List<Pesanan> daftarPesanan) {
        admin.muatPesananDariFile();
        if (daftarPesanan.isEmpty()) {
            System.out.println("Tidak ada pesanan yang menunggu diproses.");
            return;
        }

        printPesananTable(daftarPesanan);
    }

    public void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }

    /**
    * @deprecated Method ini sudah tidak digunakan lagi.
    * Harap gunakan method {@link #tampilkanMenuAdminBaru()}.
    */
    @Deprecated
    public void tampilkanMenuAdmin() {
        System.out.println("\n+------------------- Menu Admin -------------------+");
        System.out.println("| 1. Tambah Produk                                 |");
        System.out.println("| 2. Hapus Produk                                  |");
        System.out.println("| 3. Ubah Harga Produk                             |");
        System.out.println("| 4. Lihat Daftar Produk                           |");
        System.out.println("| 5. Lihat Pesanan                                 |");
        System.out.println("| 6. Proses Pesanan                                |");
        System.out.println("| 7. Keluar                                        |");
        System.out.println("+--------------------------------------------------+");
        System.out.print("| Pilih: ");
    }

    public void tampilkanMenuAdminBaru() {
        System.out.println("\n+------------------- Menu Admin -------------------|");
        System.out.println("| 1. Tambah Produk                                 |");
        System.out.println("| 2. Hapus Produk                                  |");
        System.out.println("| 3. Ubah Harga Produk                             |");
        System.out.println("| 4. Lihat Daftar Produk                           |");
        System.out.println("| 5. Lihat Pesanan                                 |");
        System.out.println("| 6. Proses Pesanan                                |");
        System.out.println("| 7. Keluar                                        |");
        System.out.println("+--------------------------------------------------+");
        System.out.print("| Pilih: ");
    }

    public void MenuTambahProduk() {
        System.out.println("+-------------- Tambah Produk -------------+");
        System.out.println("| 1. Produk Umum                           |");
        System.out.println("| 2. Elektronik Rumah Tangga               |");
        System.out.println("| 3. Gadget                                |");
        System.out.println("+------------------------------------------+");
        System.out.print("| Pilih tipe produk\t\t: ");
    }
}

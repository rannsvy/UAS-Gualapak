package view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Admin;
import model.Keranjang;
import model.Pengguna;
import model.Pesanan;


public class PenggunaView {
    private static final DecimalFormat df = new DecimalFormat("#,###.00");
    private Pengguna pengguna;
    private AdminView adminView;

    public PenggunaView(Pengguna pengguna, AdminView adminView) {
        this.pengguna = pengguna;
        this.adminView = adminView;
    }

    /**
    * @deprecated Method ini sudah tidak digunakan lagi.
    * Harap gunakan method {@link #tampilkanMenuPenggunaBaru()}.
    */
    @Deprecated
    public void tampilkanMenuPengguna() {
        System.out.println("+----------------- Menu Pengguna -----------------+");
        System.out.println("| 1. Lihat dan Tambah ke Keranjang                |");
        System.out.println("| 2. Lihat Keranjang                              |");
        System.out.println("| 3. Checkout                                     |");
        System.out.println("| 4. Lihat Riwayat Pembelian                      |");
        System.out.println("| 5. Lihat Pesanan                                |");
        System.out.println("| 6. Konfirmasi Pesanan Selesai                   |");
        System.out.println("| 7. Keluar                                       |");
        System.out.println("+-------------------------------------------------+");
        System.out.print("| Pilih: ");
    }
    
    public void tampilkanMenuPenggunaBaru() {
        System.out.println("+----------------- Menu Pengguna -----------------+");
        System.out.println("| 1. Lihat dan Tambah ke Keranjang                |");
        System.out.println("| 2. Lihat Keranjang                              |");
        System.out.println("| 3. Checkout                                     |");
        System.out.println("| 4. Lihat Riwayat Pembelian                      |");
        System.out.println("| 5. Lihat Pesanan                                |");
        System.out.println("| 6. Konfirmasi Pesanan Selesai                   |");
        System.out.println("| 7. Keluar                                       |");
        System.out.println("+-------------------------------------------------+");
        System.out.print("| Pilih: ");
    }

    public void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }

    public void lihatKeranjang(String username) {
        ArrayList<Keranjang> keranjang = pengguna.getKeranjang(username);
        if (keranjang.isEmpty()) {
            System.out.println("Keranjang kosong!");
        } else {
            System.out.printf("%-7s | %-20s | %-10s | %-5s | %-15s\n",
                    "ID", "Nama", "Harga Satuan", "Jumlah", "Subtotal");
            System.out.println("-------------------------------------------------------------------");

            for (Keranjang item : keranjang) {
                int hargaSatuan = item.getProduk().getHarga();
                int subtotal = hargaSatuan * item.getJumlah();

                System.out.printf("%-7s | %-20s | %-10s | %-5d | %-15s\n",
                        item.getProduk().getId(), item.getProduk().getNama(),
                        df.format(hargaSatuan), item.getJumlah(), df.format(subtotal));
            }
            System.out.println("------------------------------------------------------------------");
        }
    }

    public String pilihMetodePembayaran(Scanner scanner) {
        System.out.println("\nMetode Pembayaran:");
        System.out.println("1. COD");
        System.out.println("2. Transfer Bank");
        System.out.println("3. Kartu Kredit BCA");
        System.out.println("4. Kartu Kredit CIMB Niaga");
        System.out.println("5. Mega Gold Card");
        System.out.print("Pilih metode pembayaran (1-5): ");
        int pilihanPembayaran = scanner.nextInt();
        scanner.nextLine();
    
        return switch (pilihanPembayaran) {
            case 1 -> "COD";
            case 2 -> "Transfer Bank";
            case 3 -> "Kartu Kredit BCA";
            case 4 -> "Kartu Kredit CIMB Niaga";
            case 5 -> "Mega Gold Card";
            default -> {
                System.out.println("Pilihan tidak valid. Menggunakan metode pembayaran default (COD).");
                yield "COD";
            }
        };
    }

    public void tampilkanStruk(String username, String alamat, String telepon, int totalBelanja,
        int totalDiskonKategori, int ongkir, int totalSetelahDiskonKategori, String metodePembayaran,
        int diskonPembayaran, int totalSetelahDiskonPembayaran, DecimalFormat df) {

        System.out.println("\n=================== Struk Checkout =======================");
        System.out.println("| Nama          : " + username);
        System.out.println("| Alamat        : " + alamat);
        System.out.println("| Nomor Telepon : " + telepon);
        System.out.printf("| Total Belanja                     : %s\n", df.format(totalBelanja));
        System.out.printf("| Diskon Kategori                   : %s\n", df.format(totalDiskonKategori));
        System.out.printf("| Total Setelah Diskon              : %s\n", df.format(totalSetelahDiskonKategori));

        if (diskonPembayaran > 0) {
            System.out.printf("| Diskon Pembayaran (%s)            : %s\n", metodePembayaran, df.format(diskonPembayaran));
            System.out.printf("| Total Setelah Diskon Pembayaran   : %s\n", df.format(totalSetelahDiskonPembayaran - ongkir));
        }

        System.out.printf("| Harga Ongkir                      : %s\n", df.format(ongkir));
        System.out.printf("| Total Keseluruhan                 : %s\n", df.format(totalSetelahDiskonPembayaran));
        System.out.println("=============================================================");
    }

    public void lihatPesananPengguna(String username, Admin admin) {
    List<Pesanan> pesananPengguna = new ArrayList<>();
    for (Pesanan pesanan : admin.getDaftarPesanan()) {
        if (pesanan.getUsername().equals(username) && !pesanan.getStatus().equals("Sudah Terkirim")) {
            pesananPengguna.add(pesanan);
        }
    }
    System.out.println("+------------------------------------------------------------------- Daftar Pesanan Anda ----------------------------------------------------------------------+");
    if (pesananPengguna.isEmpty()) {
        System.out.println("Tidak ada pesanan yang sedang diproses atau dalam pengiriman.");
    } else {
        adminView.printPesananTable(pesananPengguna);
    }
}

}


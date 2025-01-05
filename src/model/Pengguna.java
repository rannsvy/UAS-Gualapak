package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import view.PenggunaView;

public class Pengguna {
    private Map<String, ArrayList<Keranjang>> keranjangMap;
    private static final DecimalFormat df = new DecimalFormat("#,###.00");
    private PenggunaView penggunaView;

    public Pengguna(PenggunaView penggunaView) {
        this.keranjangMap = new HashMap<>();
        this.penggunaView = penggunaView;
    }

    public void setPenggunaView(PenggunaView penggunaView) {
        this.penggunaView = penggunaView;
    }

    public void tambahKeKeranjang(String username, Produk produk, int jumlah) {
        keranjangMap.putIfAbsent(username, new ArrayList<>());
        ArrayList<Keranjang> keranjang = keranjangMap.get(username);

        boolean produkDitemukan = false;
        for (Keranjang item : keranjang) {
            if (item.getProduk().getId().equals(produk.getId())) {
                item.tambahJumlah(jumlah);
                produkDitemukan = true;
                break;
            }
        }

        if (!produkDitemukan) {
            keranjang.add(new Keranjang(produk, jumlah));
        }

        System.out.println("| Produk berhasil ditambahkan ke keranjang!");
    }

    public ArrayList<Keranjang> getKeranjang(String username) {
        return keranjangMap.getOrDefault(username, new ArrayList<>());
    }
    

    public void checkout(String username, Admin admin) {
        ArrayList<Keranjang> keranjang = keranjangMap.getOrDefault(username, new ArrayList<>());
        if (keranjang.isEmpty()) {
            System.out.println("| Keranjang kosong!");
            return;
        }
    
        Scanner scanner = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("Rp#,###.00");
    
        System.out.println("\n======================= Keranjang Anda ==========================");
        for (Keranjang item : keranjang) {
            System.out.printf("| ID: %s | Nama: %s | Jumlah: %d | Subtotal: %s\n",
                    item.getProduk().getId(), item.getProduk().getNama(), item.getJumlah(),
                    df.format(item.getProduk().getHarga() * item.getJumlah()));
        }
    
        System.out.println("\n| Masukkan ID produk yang ingin di-checkout (pisahkan dengan koma): ");
        String[] idProdukDipilih = scanner.nextLine().split(",");
    
        List<Keranjang> produkDipilih = new ArrayList<>();
        List<Keranjang> produkTidakDipilih = new ArrayList<>(keranjang);
    
        for (String id : idProdukDipilih) {
            id = id.trim();
            for (Keranjang item : keranjang) {
                if (item.getProduk().getId().equals(id)) {
                    produkDipilih.add(item);
                    produkTidakDipilih.remove(item);
                    break;
                }
            }
        }
    
        if (produkDipilih.isEmpty()) {
            System.out.println("| Tidak ada produk yang dipilih untuk checkout.");
            return;
        }
    
        int totalBelanja = 0;
        int totalDiskonKategori = 0;
    
        for (Keranjang item : produkDipilih) {
            int subtotal = item.getProduk().getHarga() * item.getJumlah();
            int diskon = hitungDiskon(item.getProduk(), item.getJumlah());
            totalBelanja += subtotal;
            totalDiskonKategori += diskon;
        }
    
        int totalSetelahDiskonKategori = totalBelanja - totalDiskonKategori;
        String metodePembayaran = penggunaView.pilihMetodePembayaran(scanner);
        int diskonPembayaran = Produk.hitungDiskonPembayaran(totalSetelahDiskonKategori, metodePembayaran);
        int totalSetelahDiskonPembayaran = totalSetelahDiskonKategori - diskonPembayaran;
    
        System.out.print("| Masukkan Alamat\t\t: ");
        String alamat = scanner.nextLine();
        System.out.print("| Masukkan Nomor Telepon\t: ");
        String telepon = scanner.nextLine();
    
        int ongkir = pilihPengiriman(alamat, scanner);
        totalSetelahDiskonPembayaran += ongkir;
    
        buatPesanan(username, admin, produkDipilih, metodePembayaran, alamat);
    
        penggunaView.tampilkanStruk(username, alamat, telepon, totalBelanja, totalDiskonKategori, ongkir,
                totalSetelahDiskonKategori, metodePembayaran, diskonPembayaran, totalSetelahDiskonPembayaran, df);
    
        keranjangMap.put(username, new ArrayList<>(produkTidakDipilih));
        simpanStruk(username, produkDipilih, totalBelanja, totalDiskonKategori,
                totalSetelahDiskonKategori, diskonPembayaran, totalSetelahDiskonPembayaran, alamat, telepon);
    }
    
    
    private int hitungDiskon(Produk produk, int jumlah) {
        int hargaSatuan = produk.getHarga();
        int diskon = 0;

        if (produk instanceof ElektronikRumahTangga) {
            diskon = (hargaSatuan * 10)/100; 
        } else if (produk instanceof Gadget) {
            diskon = (hargaSatuan * 15)/100; 
        }

        return diskon * jumlah;
    }

    private int pilihPengiriman(String alamat, Scanner scanner) {
        OngkirHandler ongkirHandler = new OngkirHandler();
        Integer jarak = ongkirHandler.getJarak(alamat);
    
        if (jarak == null) {
            System.out.println("| Alamat tidak dikenali. Ongkos kirim tidak tersedia.");
            return 0;
        }
    
        System.out.println("| Ongkos kirim berdasarkan jarak: " + jarak + " km");
    
        if (alamat.equalsIgnoreCase("Surabaya")) {
            System.out.println("Gratis Ongkir!");
            return 0;
        }
    
        System.out.println("| Pilih jasa pengiriman:");
        System.out.println("| 1. J&T");
        System.out.println("| 2. JNE");
        System.out.println("| 3. SiCepat");
        System.out.print("| Pilih pengiriman (1-3): ");
        int pilihanPengiriman = scanner.nextInt();
        scanner.nextLine(); 
    
        int ongkir = Produk.hitungOngkir(jarak, pilihanPengiriman);
        System.out.println("| Ongkir: " + ongkir);
        return ongkir;
    }
    
    private void buatPesanan(String username, Admin admin, List<Keranjang> produkDipilih,
                         String metodePembayaran, String alamat) {
    for (Keranjang item : produkDipilih) {
        int totalBelanja = item.getJumlah() * (int) item.getProduk().getHarga();

        Pesanan pesanan = new Pesanan(
                username,
                item.getProduk().getNama(),
                item.getJumlah(),
                totalBelanja, 
                metodePembayaran,
                alamat
        );
        admin.tambahPesanan(pesanan); 
    }
    }

    private void simpanStruk(String username, List<Keranjang> keranjang, int totalBelanja, int totalDiskonKategori,
                         int totalSetelahDiskonKategori, int diskonPembayaran, int totalSetelahDiskonPembayaran,
                         String alamat, String telepon) {
    String filename = "data/history.csv"; 

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
        DecimalFormat df = new DecimalFormat("Rp#,###.00");

        writer.write(String.format("Username: %s, Alamat: %s, Telepon: %s\n", username, alamat, telepon));
        writer.write("+------------------+--------+-----------------+-----------------+\n");
        writer.write(String.format("| %-16s | %-6s | %-15s | %-15s |\n", "Nama Produk", "Jumlah", "Harga Satuan", "Subtotal"));
        writer.write("+------------------+--------+-----------------+-----------------+\n");

        for (Keranjang item : keranjang) {
            writer.write(String.format("| %-16s | %-6d | %-14s | %-15s |\n",
                    item.getProduk().getNama(),
                    item.getJumlah(),
                    df.format(item.getProduk().getHarga()),
                    df.format(item.getProduk().getHarga() * item.getJumlah())));
        }

        System.out.println("------------------------------------------------------------\n");
        writer.write("Rincian Total\n");
        writer.write(String.format("Total Belanja\t\t\t: %-15s\n", df.format(totalBelanja)));
        writer.write(String.format("Diskon Kategori\t\t\t: %-15s\n", df.format(totalDiskonKategori)));
        writer.write(String.format("Total Setelah Diskon Kategori\t: %-15s\n", df.format(totalSetelahDiskonKategori)));
        writer.write(String.format("Diskon Pembayaran (%s)\t: %-15s\n", diskonPembayaran > 0 ? "Ya" : "Tidak", df.format(diskonPembayaran)));
        writer.write(String.format("Total Setelah Diskon Pembayaran\t: %-15s\n", df.format(totalSetelahDiskonPembayaran)));
        writer.write("--------------------------------------------------------------\n");

        System.out.println("| Struk berhasil disimpan ke file history.csv.");
        System.out.println("------------------------------------------------------------\n");
    } catch (IOException e) {
        System.out.println("Gagal menyimpan struk: " + e.getMessage());
    }
}

    public void lihatRiwayat(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/history.csv"))) {
            String line;
            boolean found = false;

            System.out.println("======================= Riwayat Belanja =========================");
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Username: " + username)) {
                    found = true;
                    System.out.println(line); 
                    while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                        System.out.println(line);
                    }
                    System.out.println("=================================================================\n");
                }
            }

            if (!found) {
                System.out.println("| Tidak ada riwayat belanja untuk username: " + username);
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca riwayat: " + e.getMessage());
        }
    }
}
    
    



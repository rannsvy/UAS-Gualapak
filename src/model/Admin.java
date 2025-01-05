package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Admin {
    private ArrayList<Produk> daftarProduk;
    private List<Pesanan> daftarPesanan = new ArrayList<>();

    public Admin() {
        this.daftarProduk = new ArrayList<>();
        bacaProdukDariFile();
        muatPesananDariFile();
    }

    public void bacaProdukDariFile() {
        daftarProduk.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/produk.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (data.length < 5) continue; 
    
                String id = data[0].trim();
                String nama = data[1].trim();
                int harga = Integer.parseInt(data[2].trim());
                String merek = data[3].trim();
                String tipe = data[4].trim();
    
                if ("Elektronik Rumah Tangga".equalsIgnoreCase(tipe) && data.length >= 7) {
                    int daya = Integer.parseInt(data[5].trim().replace("W", ""));
                    int garansi = Integer.parseInt(data[6].trim().replace("bulan", ""));
                    ElektronikRumahTangga produk = new ElektronikRumahTangga(id, nama, harga, merek, daya, garansi);
                    daftarProduk.add(produk);
                } else if ("Gadget".equalsIgnoreCase(tipe) && data.length >= 9) {
                    String os = data[5].trim();
                    int ram = Integer.parseInt(data[6].trim().replace("GB", ""));
                    int storage = Integer.parseInt(data[7].trim().replace("GB", ""));
                    int garansi = Integer.parseInt(data[8].trim().replace("bulan", ""));
                    Gadget produk = new Gadget(id, nama, harga, merek, os, garansi, ram, storage);
                    daftarProduk.add(produk);
                } else {
                    Produk produk = new Produk(id, nama, harga, merek);
                    daftarProduk.add(produk);
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca file produk: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Kesalahan format angka: " + e.getMessage());
        }
    }

    public void tambahProduk(Produk produk) {
        daftarProduk.add(produk);
        System.out.println("| Produk berhasil ditambahkan!");
    }

    public void hapusProduk(String id) {
        daftarProduk.removeIf(produk -> produk.getId().equals(id));
        System.out.println("| Produk berhasil dihapus!");
    }

    public void ubahHargaProduk(String id, int hargaBaru) {
        for (Produk produk : daftarProduk) {
            if (produk.getId().equals(id)) {
                produk.setHarga(hargaBaru);
                System.out.println("| Harga produk berhasil diubah!");
                return;
            }
        }
        System.out.println("| Produk tidak ditemukan!");
    }

    public ArrayList<Produk> getDaftarProduk() {
        return daftarProduk;
    }

    public void simpanProdukKeFile(Produk produk) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/produk.csv", true))) {
            if (produk instanceof ElektronikRumahTangga) {
                ElektronikRumahTangga eProduk = (ElektronikRumahTangga) produk;
                writer.write(String.format("%s,%s,%d,%s,Elektronik Rumah Tangga,%dW,%dbulan\n",
                        produk.getId(), produk.getNama(), produk.getHarga(), produk.getMerek(),
                        eProduk.getKonsumsiDaya(), eProduk.getDurasiGaransi()));
            } else if (produk instanceof Gadget) {
                Gadget gProduk = (Gadget) produk;
                writer.write(String.format("%s,%s,%d,%s,Gadget,%s,%dGB,%dGB,%dbulan\n",
                        produk.getId(), produk.getNama(), produk.getHarga(), produk.getMerek(),
                        gProduk.getSistemOperasi(), gProduk.getRam(), gProduk.getPenyimpanan(), gProduk.getDurasiGaransi()));
            } else {
                writer.write(String.format("%s,%s,%d,%s,Umum\n",
                        produk.getId(), produk.getNama(), produk.getHarga(), produk.getMerek()));
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan produk ke file: " + e.getMessage());
        }
    }
    
    public void tambahPesanan(Pesanan pesanan) {
        daftarPesanan.add(pesanan);
        simpanPesananKeFile(pesanan);
    }

    public void simpanPesananKeFile(Pesanan pesanan) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/pesanan.csv", true))) {
            writer.write(String.join(",",
                    pesanan.getIdPesanan(),
                    pesanan.getUsername(),
                    pesanan.getProduk(),
                    String.valueOf(pesanan.getJumlah()),
                    String.valueOf((int) pesanan.getTotalBelanja()), 
                    pesanan.getMetodePembayaran(),
                    pesanan.getAlamat(),
                    pesanan.getStatus()));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Gagal menyimpan pesanan ke file: " + e.getMessage());
        }
    }        

    public void muatPesananDariFile() {
        daftarPesanan.clear();
        File file = new File("data/pesanan.csv");
    
        if (!file.exists()) {
            System.out.println("File pesanan belum tersedia. Data kosong.");
            return;
        }
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); 
    
                if (data.length != 8) { 
                    System.out.println("Baris tidak valid, melewati: " + line);
                    continue;
                }
    
                try {
                    String totalBelanjaStr = data[4].trim().replace(",", ".");
                    int totalBelanja = Integer.parseInt(totalBelanjaStr); 
    
                    Pesanan pesanan = new Pesanan(
                            data[0].trim(), 
                            data[1].trim(),
                            data[2].trim(), 
                            Integer.parseInt(data[3].trim()), 
                            totalBelanja, 
                            data[5].trim(), 
                            data[6].trim()  
                    );
                    pesanan.setStatus(data[7].trim()); 
    
                    if (pesanan.getStatus().equalsIgnoreCase("Pesanan Sedang Diproses") ||
                        pesanan.getStatus().equalsIgnoreCase("Dalam Pengiriman")) {
                        daftarPesanan.add(pesanan);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Kesalahan format angka pada baris: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca file pesanan: " + e.getMessage());
        }
    }

    public List<Pesanan> getDaftarPesanan() {
        return daftarPesanan;
    }

    public void perbaruiFilePesanan() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/pesanan.csv"))) {
            for (Pesanan pesanan : daftarPesanan) {
                writer.write(String.join(",",
                        pesanan.getIdPesanan(),
                        pesanan.getUsername(),
                        pesanan.getProduk(),
                        String.valueOf(pesanan.getJumlah()),
                        String.valueOf((int) pesanan.getTotalBelanja()),
                        pesanan.getMetodePembayaran(),
                        pesanan.getAlamat(),
                        pesanan.getStatus()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Gagal memperbarui file pesanan: " + e.getMessage());
        }
    }
}

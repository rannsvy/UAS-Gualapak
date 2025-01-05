package view;

import controller.AdminController;
import controller.PenggunaController;
import java.util.Scanner;
import model.*;

public class Gualapak {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {
        Admin admin = new Admin();
        Pengguna pengguna = new Pengguna(null);
        Scanner scanner = new Scanner(System.in);

        AdminView adminView = new AdminView(admin);
        PenggunaView penggunaView = new PenggunaView(pengguna, adminView);

        AdminController adminController = new AdminController(admin, adminView);
        PenggunaController penggunaController = new PenggunaController(pengguna, penggunaView, adminView);

        AkunManager akunManager = new AkunManager();
        pengguna.setPenggunaView(penggunaView);

        System.out.println("|==================================================|");
        System.out.println("| Created By Kelompok 1 ---------------------------|");
        System.out.println("| 1. Feb Ghulam M.A           23051204229          |");
        System.out.println("| 2. Fairuz Fauzi Ramadan     23051204233          |");
        System.out.println("| 3. Dwi Amanda Amelia P.     23051204235          |");
        System.out.println("| 4. Ranuh Firham C.G.P       23051204235          |");
        System.out.println("| 5. Alfin Desta Fitrianto    23051204244          |");
        System.out.println("| 6. M. Habib Fathurrochim    23051204247          |");
        System.out.println("|--------------------------------------------------|");
        System.out.println("|-------------- GUALAPAK (Official Store Surabaya) |");
        System.out.println("|==================================================|\n\n");
    


        boolean berjalan = true;
        while (berjalan) {
            System.out.println("|==================================================|");
            System.out.println("|            SELAMAT DATANG DI GUALAPAK            |");
            System.out.println("|==================================================|");
            System.out.println("| 1. Masuk sebagai Admin                           |");
            System.out.println("| 2. Masuk sebagai Pengguna                        |");
            System.out.println("| 3. Daftar Akun                                   |");
            System.out.println("| 4. Keluar                                        |");
            System.out.println("|--------------------------------------------------|");
            System.out.print("| Pilihan\t: ");
            int pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1 -> {
                    System.out.print("| Username\t: ");
                    String username = scanner.next();
                    System.out.print("| Password\t: ");
                    String password = scanner.next();
                    if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                        adminController.tampilkanMenuAdmin(scanner);
                    } else {
                        System.out.println("| Login Admin gagal! Username atau password salah.");
                        System.out.println("|---------------------------------------------------\n");
                    }
                }
                case 2 -> {
                    System.out.print("| Username\t: ");
                    String username = scanner.next();
                    System.out.print("| Password\t: ");
                    String password = scanner.next();
                    if (akunManager.loginAkun(username, password)) {
                        System.out.println("| Selamat Datang " + username + ", Gualapak siap membantu Anda!");
                        System.out.println("|---------------------------------------------------\n\n");
                        penggunaController.tampilkanMenuPengguna(admin, username, scanner);
                    } else {
                        System.out.println("| Login gagal! Username atau password salah.");
                        System.out.println("|---------------------------------------------------\n");
                    }
                }
                case 3 -> {
                    System.out.print("| Masukkan username baru (min. 4 karakter): ");
                    String username = scanner.next();
                    if (username.length() < 4) {
                        System.out.println("| Username terlalu pendek!");
                        break;
                    }
                    System.out.print("| Masukkan password baru (min. 6 karakter): ");
                    String password = scanner.next();
                    if (password.length() < 6) {
                        System.out.println("| Password terlalu pendek!");
                        break;
                    }
                    if (akunManager.daftarAkun(username, password)) {
                        System.out.println("| Akun berhasil didaftarkan!");
                        System.out.println("|---------------------------------------------------\n");
                    } else {
                        System.out.println("| Username sudah digunakan!");
                        System.out.println("|---------------------------------------------------\n");
                    }
                }
                case 4 -> {
                    System.out.println("| Terimakasih telah berbelanja di Gualapak");
                    System.out.println("| Program ini dibuat oleh Kelompok 1 TIG-23");
                    System.out.println("|--------------------------------------------------\n");
                    berjalan = false;}
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
        scanner.close();
    }
}
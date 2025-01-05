package model;

import java.io.*;
import java.util.*;

public class AkunManager {
    private List<User> daftarAkun = new ArrayList<>();
    private static final String FILENAME = "data/users.csv";

    public AkunManager() {
        muatAkun();
    }

    public boolean daftarAkun(String username, String password) {
        for (User akun : daftarAkun) {
            if (akun.getUsername().equals(username)) {
                return false; 
            }
        }
        daftarAkun.add(new User(username, password));
        simpanAkun();
        return true;
    }

    public boolean loginAkun(String username, String password) {
        for (User akun : daftarAkun) {
            if (akun.getUsername().equals(username) && akun.cekPassword(password)) {
                return true;
            }
        }
        return false;
    }

    private void simpanAkun() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (User akun : daftarAkun) {
                writer.write(akun.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan akun.");
        }
    }

    private void muatAkun() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    daftarAkun.add(new User(data[0], data[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File akun tidak ditemukan, membuat data baru.");
        } catch (IOException e) {
            System.out.println("Gagal membaca file akun.");
        }
    }
}


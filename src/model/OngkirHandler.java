package model;

import java.io.*;
import java.util.*;

public class OngkirHandler {
    private Map<String, Integer> lokasiMap = new HashMap<>();

    public OngkirHandler() {
        muatDataLokasi();
    }

    private void muatDataLokasi() {
        String filename = "data/map.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    lokasiMap.put(data[0].trim(), Integer.parseInt(data[1].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca data lokasi: " + e.getMessage());
        }
    }

    public Integer getJarak(String alamat) {
        return lokasiMap.getOrDefault(alamat, null); 
    }
}

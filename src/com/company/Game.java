package com.company;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Game {
    public boolean saveGame(String path, GameProgress gameProgress) {
        boolean result = false;
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gameProgress);
            out.close();
            fileOut.close();
            result = true;
        } catch (IOException i) {
            i.printStackTrace();
        }
        return result;
    }

    public void writeLog(String path, String rec) throws IOException {
        File log = new File(path);
        FileWriter out = new FileWriter(log, true);

        try {
            out.write("\n" + rec);
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean addToZip(ZipOutputStream zous, File file) {
        boolean result = false;
        try {
            FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
            ZipEntry entry = new ZipEntry(file.getName());
            zous.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zous.write(buffer);
            zous.closeEntry();
            fis.close();
            result = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public boolean openZip(String zipPath, String path, String tmpFile) {
        boolean result = false;
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fous = new FileOutputStream(path + "\\" + name);
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fous.write(c);
                }
                fous.flush();
                zis.closeEntry();
                fous.close();
                writeLog(tmpFile, "File: " + name + "  extracted from the archive - " + zipPath);
            }
            result = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public GameProgress openProgress(String path) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
            return gameProgress;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }
}
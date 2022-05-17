package com.company;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipOutputStream;

public class MainSave {

    public static void main(String[] args) throws IOException {
        Game game = new Game();
        String path = "C:\\Games\\savegames";
        String temp = "C:\\Games\\temp\\temp.txt";
        String zip = "C:\\Games\\savegames\\savegames.zip";

        ArrayList<GameProgress> gameProgresses = new ArrayList<>();
        gameProgresses.add(new GameProgress(12, 3, 3, 0.4));
        gameProgresses.add(new GameProgress(7, 24, 4, 0.8));
        gameProgresses.add(new GameProgress(18, 6, 8, 0.2));

        for (int i = 0; i < gameProgresses.size(); i++) {
            if (game.saveGame(path + "\\gameprogress" + (i + 1) + ".ser",
                    gameProgresses.get(i))) {
                game.writeLog(temp, "GameProgress save in : " + path + "\\gameprogress" +
                        (i + 1) + ".ser");
            }
        }

        File dir = new File(path);
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zip));
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(".ser")) {
                if (game.addToZip(zipOut, file)) {
                    game.writeLog(temp, "File: " + file.getAbsolutePath() + " add to the archive : "
                            + zip);
                }
            }
        }
        zipOut.close();

        for (File file : dir.listFiles()
        ) {
            if (!file.getName().endsWith(".zip")) {
                game.writeLog(temp, "File: " + file.getAbsolutePath() + " - delete");
                if (file.delete()) {
                    System.out.println("files delete");
                }
            }
        }
    }

}



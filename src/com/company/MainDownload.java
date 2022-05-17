package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainDownload {
    public static void main(String[] args)  {
        Game game = new Game();
        String path = "C:\\Games\\savegames";
        String tmp = "C:\\Games\\temp\\temp.txt";
        String zip = "C:\\Games\\savegames\\savegames.zip";

        File dir = new File(path);
        List<GameProgress> gameProgresses = new ArrayList<>();

        if (game.openZip(zip, path, tmp)) {
            System.out.println("Files extracted from the archive.");
        }

        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(".ser")) {
                gameProgresses.add(game.openProgress(file.getAbsolutePath()));
            }
        }

        for (GameProgress gameProgress : gameProgresses) {
            System.out.println(gameProgress);
        }
    }

}

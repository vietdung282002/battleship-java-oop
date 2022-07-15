package org.fxapps.battleship.app.score;

import java.io.*;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;

public class leaderboard {
    private static leaderboard lBoard;
    private String filepath;
    private String highScores;

    private ArrayList<Integer> topScores;

    private leaderboard(){
        filepath = new File("D:/dung/HUST/20212/OOP/battleship-apps/battleship-java-oop/battleship-game-master/battleship-app/src/main/resources/score").getAbsolutePath();
        highScores= "score.txt";

        topScores = new ArrayList<Integer>();
    }

    public static leaderboard getInstance(){            
        if(lBoard==null){
            lBoard = new leaderboard();
        }
        return lBoard;
    }

    public void add(int score){
        for(int i=0;i<topScores.size();i++){
            if(score > topScores.get(i)){
                topScores.add(i,score);
                topScores.remove(topScores.size()-1);
                return;
            }
        }
    }

    public void loadScore(){
        try{
            File f =new File(filepath,highScores);
            if(!f.isFile()){
                createSaveData();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            topScores.clear();

            String[] scores = reader.readLine().split("-");

            for(int i=0;i<scores.length;i++){
                topScores.add(Integer.parseInt(scores[i]));
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void saveScores(){
        FileWriter output = null;
        try{
            File f = new File(filepath,highScores);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write(topScores.get(0)+"-"+topScores.get(1)+"-"+topScores.get(2)+"-"+topScores.get(3)+"-"+topScores.get(4));
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createSaveData(){
        FileWriter output=null;

        try{
            File f = new File(filepath,highScores);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("0-0-0-0-0");
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getHighScore(){
        return topScores.get(0);
    }

    public int getScore(int index){
        return topScores.get(index);
    }

    public ArrayList<Integer> getTopScores(){
        return topScores;
    }

    public void setTopScores(ArrayList<Integer> topScores){
        this.topScores=topScores;
    }
}

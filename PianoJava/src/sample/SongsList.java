package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SongsList {
    public static ArrayList<Song> songList;
    private static Random randomGenerator=new Random();


    public SongsList(){
        songList=new ArrayList<>();

        songList.add(new Song("Twinkle twinkle!","Twinkle, twinkle, little star," +
                "\nHow I wonder what you are!Up above the world so high," +
                "\nLike a diamond in the sky.","DO SOL SOL"));

        songList.add(new Song("Jingle Bells!","Jingle bells, jingle bells\n" +
                "Jingle all the way\n" +
                "Oh, what fun it is to ride\n" +
                "In a one horse open sleigh","DO SOL SOL"));

        songList.add(new Song("Mary and a lamb!","Mary had a little lamb\n" +
                "Little lamb, little lamb\n" +
                "Mary had a little lamb\n" +
                "Its fleece was white as snow\n","DO SOL SOL"));

        songList.add(new Song("Bella,ciao!","Una mattina mi sono alzato\n" +
                "O bella ciao, bella ciao, bella ciao, ciao, ciao\n" +
                "Una mattina mi sono azalto\n" +
                "E ho trovato l'invasor","DO RE MI"));
    }

    public static Song randomSong(){

        int index = randomGenerator.nextInt(songList.size());
        Song song= songList.get(index);

        return song;
    }

}

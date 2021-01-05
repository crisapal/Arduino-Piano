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
                "\nLike a diamond in the sky.","DO1 DO1 SOL SOL LA LA SOL FA FA MI MI RE RE DO1 SOL SOL FA FA MI MI RE DO1 DO1 SOL SOL LA LA SOL FA FA MI MI RE RE DO1"));

        songList.add(new Song("Jingle Bells!","Jingle bells, jingle bells\n" +
                "Jingle all the way\n" +
                "Oh, what fun it is to ride\n" +
                "In a one horse open sleigh","MI MI MI MI MI MI MI SOL DO1 RE MI FA FA FA FA FA MI MI MI LA LA SOL RE DO1"));

        songList.add(new Song("Mary and a lamb!","Mary had a little lamb\n" +
                "Little lamb, little lamb\n" +
                "Mary had a little lamb\n" +
                "Its fleece was white as snow\n","MI RE DO1 RE MI MI MI RE RE RE MI MI MI MI RE DO1 RE MI MI MI DO1 RE RE MI RE DO1"));

        songList.add(new Song("Bella,ciao!","Una mattina mi sono alzato\n" +
                "O bella ciao, bella ciao, bella ciao, ciao, ciao\n" +
                "Una mattina mi sono azalto\n" +
                "E ho trovato l'invasor","MI LA SI DO2 LA MI LA SI DO2 LA MI LA SI DO2 SI LA DO2"));
    }

    public static Song randomSong(){

        int index = randomGenerator.nextInt(songList.size());
        Song song= songList.get(index);

        return song;
    }

}

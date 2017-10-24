package impl;

import pmp.pipes.SimplePipe;

import java.io.FileNotFoundException;
import java.io.StreamCorruptedException;

public class Main {

    public static void main(String[] args) throws StreamCorruptedException {

        //Path for the txt-File
        String path = "C:\\Users\\dl_asus\\IdeaProjects\\PmP2017\\PmP2017\\src\\aliceInWonderland.txt";
        Input input = null;


        try {
            input = new Input(path);

            SimplePipe simplePipe1 = new SimplePipe(input);
            simplePipe1.read();

            WordSeperator wordSeperator = new WordSeperator(simplePipe1);

            SimplePipe simplePipe2 = new SimplePipe((pmp.interfaces.Readable) wordSeperator);

            CircularShift circularShift = new CircularShift(simplePipe2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        }
    }

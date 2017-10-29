package impl;

import pmp.pipes.SimplePipe;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws StreamCorruptedException {

        //Input from File
        //Pipe1
        //WordSeparator
        //Pipe2
        //Get FrequentWords
        //CircularShift
        //Pipe3
        //SortAlphabetically
        //Pipe4
        //Output to File
        //Close InputStream --> Wichtig!

        String pathFromBook = "src\\aliceInWonderland.txt";
        String pathFromFrequentWords = "src\\frequentEnglishWords.txt";
        int numberOfWordsToReadFromFrequentWords = 100;
        String pathToOutput = "src\\output.txt";

        Input input = null;
        try {
            input = new Input(pathFromBook);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        SimplePipe<String> simplePipe1 = new SimplePipe<>(input);
        WordSeparator wordSeparator = new WordSeparator(simplePipe1);
        SimplePipe<String[]> simplePipe2 = new SimplePipe<>((pmp.interfaces.Readable<String[]>) wordSeparator);
        FrequentWordProcessor frequentWordProcessor = new FrequentWordProcessor();
        String[] frequentWords = frequentWordProcessor.getFrequentWords(pathFromFrequentWords, numberOfWordsToReadFromFrequentWords);
        CircularShift circularShift = new CircularShift((pmp.interfaces.Readable<String[]>) simplePipe2, frequentWords);
        SimplePipe<String[]> simplePipe3 = new SimplePipe<>((pmp.interfaces.Readable<String[]>) circularShift);
        SortAlphabetically sortAlphabetically = new SortAlphabetically((pmp.interfaces.Readable<String[]>) simplePipe3);
        SimplePipe<String[]> simplePipe4 = new SimplePipe<>((pmp.interfaces.Readable<String[]>) sortAlphabetically);
        Output output = new Output(simplePipe4, pathToOutput);

        //close input
        if (input != null) {
            output.run();
            input.closeStream();
        }
    }
}

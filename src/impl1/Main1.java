package impl1;

import impl.*;
import pmp.pipes.SimplePipe;

import java.io.FileNotFoundException;

public class Main1 {

    public static void main(String[] args) {
        String pathFromBook = "src\\aliceInWonderland.txt";
        String pathFromFrequentWords = "src\\frequentEnglishWords.txt";
        String pathToOutput1 = "src\\output1.txt";
        String pathToOutput2 = "src\\output2.txt";
        int numberOfWordsToReadFromFrequentWords = 100;
        int lineLength = 40;
        LineAlignment desiredLineAlignment = LineAlignment.LEFT;

        //Output1 erstellen
        //Output2 erstellen --> LineOutput
        //Pipe6
        //SortAlphabetically
        //Pipe5
        //CircularShift
        //Pipe4
        //WordSeperator
        //Pipe 3 --> DoublePushPipe
        //LineBuilder
        //Pipe2
        //WordCreator
        //Pipe1
        //Input

        Output output1 = new Output(pathToOutput1);
        LineOutput output2 = new LineOutput(pathToOutput2);
        SimplePipe<String[]> simplePipe6 = new SimplePipe<>(output1);
        SortAlphabetically sortAlphabetically = new SortAlphabetically((pmp.interfaces.Writeable<String[]>) simplePipe6);
        SimplePipe<String[]> simplePipe5 = new SimplePipe<>((pmp.interfaces.Writeable<String[]>) sortAlphabetically);
        FrequentWordProcessor frequentWordProcessor = new FrequentWordProcessor();
        CircularShift circularShift = new CircularShift((pmp.interfaces.Writeable<String[]>) simplePipe5, frequentWordProcessor.getFrequentWords(pathFromFrequentWords, numberOfWordsToReadFromFrequentWords));
        SimplePipe<String[]> simplePipe4 = new SimplePipe<>((pmp.interfaces.Writeable<String[]>) circularShift);
        WordSeparator wordSeparator = new WordSeparator(simplePipe4);

        DoublePushPipe doublePushPipe3 = new DoublePushPipe<>(wordSeparator, output2);

        LineCreater lineCreater = new LineCreater((pmp.interfaces.Writeable<String>) doublePushPipe3, lineLength, desiredLineAlignment);
        SimplePipe<String> simplePipe2 = new SimplePipe<>((pmp.interfaces.Writeable<String>) lineCreater);
        WordCreator wordCreator = new WordCreator(simplePipe2);
        SimplePipe<Character> simplePipe1 = new SimplePipe<>((pmp.interfaces.Writeable<Character>) wordCreator);

        CharacterInput input = null;
        try {
            input = new CharacterInput(simplePipe1, pathFromBook);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (input != null) {
            input.run();
        }
    }
}

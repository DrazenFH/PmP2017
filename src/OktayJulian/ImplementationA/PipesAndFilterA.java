package OktayJulian.ImplementationA;

import pmp.pipes.SimplePipe;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class PipesAndFilterA {
    private static final String INPUT_PATH = "src\\aliceInWonderland.txt";
    private static final String OUTPUT_PATH = "src\\output.txt";
    private static final String WORD_INPUT_PATH = "src\\frequentEnglishWords.txt";
    private int NUMBER_OF_WORDS = 80;

    public static void main(String[] args) {
        Input input = null;
        try {
            input = new Input(INPUT_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int NUMBER_OF_WORDS=100;
//        if(args[3]!=null){
//            NUMBER_OF_WORDS=Integer.parseInt(args[3]);
//        }

        SimplePipe<String> pipe1 = new SimplePipe<>(input);
        WordSplitter splitter= new WordSplitter(pipe1);
        SimplePipe<String[]> pipe2 = new SimplePipe<>((pmp.interfaces.Readable<String[]>) splitter);
        String[] frequentWords = getFrequentWords(WORD_INPUT_PATH,NUMBER_OF_WORDS);
        CircularShiftFilter circularShiftFilter = new CircularShiftFilter((pmp.interfaces.Readable<String[]>) pipe2, frequentWords);
        SimplePipe<String[]> pipe3 = new SimplePipe<>((pmp.interfaces.Readable<String[]>) circularShiftFilter);
        SortFilter sortFilter = new SortFilter((pmp.interfaces.Readable<String[]>) pipe3);
        SimplePipe<String[]> pipe4 = new SimplePipe<>((pmp.interfaces.Readable<String[]>) sortFilter);
        Output output = new Output(pipe4, OUTPUT_PATH);

        if (input != null) {
            output.run();
            input.closeStream();
        }
    }

    private static String[] getFrequentWords(String path,int NUMBER_OF_WORDS){
        List<String> frequentWords = new LinkedList<>();
        try {
            FileInputStream fs = new FileInputStream(path);
            DataInputStream ds = new DataInputStream(fs);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(ds));
            String wordLine;
            buffer.readLine();
            int count = 0;
            while(((wordLine = buffer.readLine()) != null) && count < NUMBER_OF_WORDS) {
                String word = wordLine.split("\t")[1];
                frequentWords.add(word);
                count++;
            }
            ds.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] words = new String[frequentWords.size()];
        return frequentWords.toArray(words);
    }
}
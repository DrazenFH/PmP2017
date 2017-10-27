package OktayJulian.ImplementationB;

import OktayJulian.ImplementationA.CircularShiftFilter;
import OktayJulian.ImplementationA.Output;
import OktayJulian.ImplementationA.SortFilter;
import OktayJulian.ImplementationA.WordSplitter;
import OktayJulian.pmp.pipes.DoubleExitPushPipe;
import OktayJulian.pmp.pipes.SimplePipe;

import javax.sound.sampled.Line;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class PipesAndFilterB {
   // private static final String INPUT_PATH = "Files/aliceInWonderland.txt";
    //private static final String WORD_INPUT_PATH = "Files/frequentEnglishWords.txt";
    //private static final int NUMBER_OF_WORDS = 100;
    //private static final String OUTPUT_PATH_1 = "Files/output_1.txt";
    //private static final String OUTPUT_PATH_2 = "Files/output_2.txt";
   // private static final int LINE_LENGTH = 80;
    //private LineAlignment LINE_ALIGNMENT = LineAlignment.RIGHT;

    public static void main(String[] args) {
        Output output_1 = new Output(args[2]);
        LineToFileOutput output_2 = new LineToFileOutput(args[3]);
        SimplePipe<String[]> pipe6_1 = new SimplePipe<>(output_1);
        SortFilter sortFilter = new SortFilter((pmp.interfaces.Writeable<String[]>) pipe6_1);
        SimplePipe<String[]> pipe5_1 = new SimplePipe<>((pmp.interfaces.Writeable<String[]>) sortFilter);
        int NUMBER_OF_WORDS=100;
        if(args[4]!=null){
            NUMBER_OF_WORDS=Integer.parseInt(args[4]);
        }

        CircularShiftFilter circularShiftFilter = new CircularShiftFilter((pmp.interfaces.Writeable<String[]>) pipe5_1, getFrequentWords(args[1], NUMBER_OF_WORDS));
        SimplePipe<String[]> pipe4_1 = new SimplePipe<>((pmp.interfaces.Writeable<String[]>) circularShiftFilter);
        WordSplitter wordSplitter = new WordSplitter(pipe4_1);
        DoubleExitPushPipe<String> pipe3 = new DoubleExitPushPipe<>(wordSplitter, output_2);
        int LINE_LENGTH=80;
        if(args[5]!=null){
            LINE_LENGTH=Integer.parseInt(args[5]);
        }

        LineAlignment LINE_ALIGNMENT=LineAlignment.RIGHT;
        if(args[6]!=null){
            switch (args[6].toUpperCase()){
                case "RIGHT":
                    LINE_ALIGNMENT=LineAlignment.RIGHT;
                    break;
                case "LEFT":
                    LINE_ALIGNMENT=LineAlignment.LEFT;
                    break;
                case "CENTER":
                    LINE_ALIGNMENT=LineAlignment.CENTER;
                    break;
            }
        }
        LineBuilder lineBuilder = new LineBuilder((pmp.interfaces.Writeable<String>) pipe3, LINE_LENGTH, LINE_ALIGNMENT);
        SimplePipe<String> pipe2 = new SimplePipe<>((pmp.interfaces.Writeable<String>) lineBuilder);
        WordBuilder wordBuilder = new WordBuilder(pipe2);
        SimplePipe<Character> pipe1 = new SimplePipe<>((pmp.interfaces.Writeable<Character>) wordBuilder);

        FileToCharacterInput input = null;
        try {
            input = new FileToCharacterInput(pipe1, args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /*output_1.run();
        output_2.run();*/
        if (input != null) {
            input.run();
        }
    }

    private static String[] getFrequentWords(String path, int NUMBER_OF_WORDS){
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


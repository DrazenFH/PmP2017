package OktayJulian.ImplementationA;

import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CircularShiftFilter extends DataTransformationFilter2<String[], String[]> {
    private String[] frequentWords;
    private int lineCount = 0;

    public CircularShiftFilter(Readable<String[]> input, String[] frequentWords) throws InvalidParameterException {
        super(input);
        this.frequentWords = frequentWords;
        for(int index = 0; index < frequentWords.length; index++) {
            this.frequentWords[index] = this.frequentWords[index].toLowerCase();
        }
    }

    public CircularShiftFilter(Writeable<String[]> output, String[] frequentWords) throws InvalidParameterException {
        super(output);
        this.frequentWords = frequentWords;
        for(int index = 0; index < frequentWords.length; index++) {
            this.frequentWords[index] = this.frequentWords[index].toLowerCase();
        }
    }

    @Override
    protected String[] process(String[] words) {
        if(words.length > 0 && words[0].equals("\0")){
            return words;
        }
        this.lineCount++;
        if(String.join("", words).trim().length() <= 0) {
            return new String[0];
        }
        List<String> outputStrings = new LinkedList<>();
        for(int index = 0; index < words.length; index++){
            if(!Arrays.asList(this.frequentWords).contains(words[0].toLowerCase())) {
                String line = String.join(" ", words) + " : " + this.lineCount;
                outputStrings.add(line);
            }
            String firstWord = words[0];
            System.arraycopy(words, 1, words, 0, words.length - 1);
            words[words.length - 1] = firstWord;
        }
        String[] result = new String[outputStrings.size()];
        outputStrings.toArray(result);
        return result;
    }

}

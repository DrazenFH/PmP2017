package impl;

import pmp.filter.DataTransformationFilter1;
import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CircularShift extends DataTransformationFilter2<String[], String[]> {
    private String[] _frequentWords;
    private int countLine = 0;

    public CircularShift(Readable<String[]> input, String[] frequentWords) throws InvalidParameterException {
        super(input);
        _frequentWords = frequentWords;
        frequentWordsToLowerCase();

    }

    public CircularShift(Writeable<String[]> output, String[] frequentWords) throws InvalidParameterException {
        super(output);
        _frequentWords = frequentWords;
        frequentWordsToLowerCase();
    }

    @Override
    protected String[] process(String[] words) {
        countLine++;

        if(words.length > 0 && words[0].equals("\0")){
            return words;
        }

        if(String.join("", words).trim().length() <= 0) {
            return new String[0];
        }

        List<String> outputStrings = new LinkedList<>();
        int i = 0;
        while ( i < words.length){

            if(!Arrays.asList(_frequentWords).contains(words[0].toLowerCase())) {
                String line = String.join(" ", words) + " : " + countLine;
                outputStrings.add(line);
            }

            String firstWord = words[0];
            System.arraycopy(words, 1, words, 0, words.length - 1);
            words[words.length - 1] = firstWord;

            i++;
        }

        String[] result = new String[outputStrings.size()];
        outputStrings.toArray(result);

        return result;
    }

    private void frequentWordsToLowerCase(){
        for(int index = 0; index < _frequentWords.length; index++) {
            this._frequentWords[index] = _frequentWords[index].toLowerCase();
        }
    }
}

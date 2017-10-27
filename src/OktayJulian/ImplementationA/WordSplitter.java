package OktayJulian.ImplementationA;

import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class WordSplitter extends DataTransformationFilter2<String, String[]> {

    public WordSplitter(Readable<String> input) throws InvalidParameterException {
        super(input);
    }

    public WordSplitter(Writeable<String[]> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected String[] process(String line) {
        line = line.replaceAll("_", "").replaceAll("[^\\w\\s\0]", "");
        List<String> words = new LinkedList<>(Arrays.asList(line.split("\\s+")));
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()){
            String word = iterator.next();
            word = word.replaceAll("\\s+", "");
            if(word.equals("")){
                iterator.remove();
            }
        }
        String[] wordsArray = new String[words.size()];
        return words.toArray(wordsArray);
    }
}

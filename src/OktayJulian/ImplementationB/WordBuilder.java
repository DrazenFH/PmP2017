package OktayJulian.ImplementationB;

import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import java.security.InvalidParameterException;

public class WordBuilder extends DataTransformationFilter2<Character, String> {
    private StringBuilder word;
    WordBuilder(Readable<Character> input) throws InvalidParameterException {
        super(input);
    }

    WordBuilder(Writeable<String> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected String process(Character character) {
        if(word == null) {
            word = new StringBuilder();
        }
        if (character == '\0') {
            return word.append('\0').toString();
        }
        if(Character.isLetterOrDigit(character)){
            word.append(character);
        } else if((character == ' ' || character == '\n' || character == '\t') && word.toString().length() > 0) {
            String returnString = word.toString();
            word = new StringBuilder();
            return returnString;
        }
        return null;
    }
}

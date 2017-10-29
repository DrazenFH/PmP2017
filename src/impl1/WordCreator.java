package impl1;

import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import java.security.InvalidParameterException;

public class WordCreator extends DataTransformationFilter2<Character, String> {
    private StringBuilder _newWord;

    WordCreator(Readable<Character> input) throws InvalidParameterException {
        super(input);
    }

    WordCreator(Writeable<String> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected String process(Character entity) {
        if(_newWord == null) {
            _newWord = new StringBuilder();
        }

        if (entity == '\0') {
            return _newWord.append('\0').toString();
        }

        if(Character.isLetterOrDigit(entity)){
            _newWord.append(entity);

        } else if((entity == ' ' || entity == '\t' || entity == '\n') && _newWord.toString().length() > 0) {
            String newString = _newWord.toString();
            _newWord = new StringBuilder();
            return newString;
        }

        return null;
    }
}

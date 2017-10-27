package impl;

import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import java.security.InvalidParameterException;

public class WordSeperator extends DataTransformationFilter2<String, String[]> {


    public WordSeperator(Readable<String> input) throws InvalidParameterException {
        super(input);
    }

    public WordSeperator(Writeable<String[]> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected String[] process(String entity) {

        entity = entity.replaceAll("_", "").replaceAll("[^\\w\\s\0]", "");
        String[] line = entity.split("\\s+");
        int i = 0;

        while(line.length > i){
            line[i] = line[i].replaceAll("\\s+","");
            i++;
        }

        return line;

    }
}

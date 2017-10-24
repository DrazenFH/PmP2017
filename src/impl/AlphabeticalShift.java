package impl;

import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import java.security.InvalidParameterException;

public class AlphabeticalShift extends DataTransformationFilter2<String, String[]> {


    public AlphabeticalShift(Readable<String> input, Writeable<String[]> output) throws InvalidParameterException {
        super(input, output);
    }

    @Override
    protected String[] process(String entity) {
        return new String[0];
    }
}

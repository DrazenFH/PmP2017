package impl;

import pmp.filter.DataTransformationFilter1;
import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import java.security.InvalidParameterException;

public class CircularShift extends DataTransformationFilter1<String[]> {


    public CircularShift(Readable<String[]> input) throws InvalidParameterException {
        super(input);
    }

    @Override
    protected void process(String[] entity) {

    }
}

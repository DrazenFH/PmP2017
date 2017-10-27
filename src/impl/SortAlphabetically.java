package impl;

import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.LinkedList;

public class SortAlphabetically extends DataTransformationFilter2<String[], String[]> {
    private LinkedList<String> lines = new LinkedList<>();

    public SortAlphabetically(Readable<String[]> input) throws InvalidParameterException {
        super(input);
    }

    public SortAlphabetically(Writeable<String[]> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected String[] process(String[] newLines) {
        lines.addAll(Arrays.asList(newLines));

        for(String line : newLines) {

            if (line.indexOf('\0') >= 0) {
                lines.sort(String.CASE_INSENSITIVE_ORDER);
                String[] outputArray = new String[lines.size()];
                return lines.toArray(outputArray);
            }
        }

        return null;
    }
}

package OktayJulian.ImplementationB;

import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import java.security.InvalidParameterException;

public class LineBuilder extends DataTransformationFilter2<String, String> {
    private int lineLength;
    private StringBuilder line;
    private LineAlignment lineAlignment;

    LineBuilder(Readable<String> input, int lineLength, LineAlignment lineAlignment) throws InvalidParameterException {
        super(input);
        this.lineLength = lineLength;
        this.lineAlignment = lineAlignment;
    }

    LineBuilder(Writeable<String> output, int lineLength, LineAlignment lineAlignment) throws InvalidParameterException {
        super(output);
        this.lineLength = lineLength;
        this.lineAlignment = lineAlignment;
    }

    @Override
    protected String process(String string) {
        if(string == null) {
            return null;
        }
        if (line == null) {
            line = new StringBuilder("");
        }
        if ((line.length() + string.length() + 1) > lineLength) {
            String finishedLine = fillLine(line.toString());
            line = new StringBuilder(string);
            return finishedLine;
        } else if((string.indexOf('\0')) >= 0){
            return fillLine(line.append(string).toString());
        } else {
            line.append(" ").append(string);
        }
        return null;
    }

    private String fillLine(String line){
        StringBuilder builder = new StringBuilder(line);
        for(int count = line.length(); count < this.lineLength; count++){
            if(this.lineAlignment.equals(LineAlignment.LEFT)){
                builder.append(" ");
            } else if(this.lineAlignment.equals(LineAlignment.RIGHT)){
                builder.insert(0, " ");
            } else if(this.lineAlignment.equals(LineAlignment.CENTER)){
                if((count % 2) == 0) {
                    builder.append(" ");
                } else {
                    builder.insert(0, " ");
                }
            }
        }
        return builder.toString();
    }
}

package impl1;

import pmp.filter.DataTransformationFilter2;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import java.security.InvalidParameterException;

public class LineCreater extends DataTransformationFilter2<String, String> {
    private int _lineLength;
    private StringBuilder _line;
    private LineAlignment _alignment;

    LineCreater(Readable<String> input, int lineLength, LineAlignment alignment) throws InvalidParameterException {
        super(input);
        _lineLength = lineLength;
        _alignment = alignment;
    }

    LineCreater(Writeable<String> output, int lineLength, LineAlignment alignment) throws InvalidParameterException {
        super(output);
        _lineLength = lineLength;
        _alignment = alignment;
    }

    @Override
    protected String process(String entity) {
        if (_line == null) {
            _line = new StringBuilder("");
        }

        if(entity == null) {
            return null;
        }

        if ((_line.length() + entity.length() + 1) > _lineLength) {
            String finishedLine = fill(_line.toString());
            _line = new StringBuilder(entity);
            return finishedLine;

        } else if((entity.indexOf('\0')) >= 0){
            return fill(_line.append(entity).toString());

        } else {
            _line.append(" ").append(entity);
        }


        return null;
    }

    private String fill(String line){
        StringBuilder stringBuilder = new StringBuilder(line);

        int i = line.length();

        while (i < _lineLength) {

            if(_alignment.equals(LineAlignment.LEFT)){
                stringBuilder.append(" ");

            } else if(_alignment.equals(LineAlignment.RIGHT)){
                stringBuilder.insert(0, " ");

            } else if(_alignment.equals(LineAlignment.CENTER)){

                if((i % 2) == 0) {
                    stringBuilder.append(" ");
                } else {
                    stringBuilder.insert(0, " ");
                }
            }

            i++;
        }

        return stringBuilder.toString();
    }
}

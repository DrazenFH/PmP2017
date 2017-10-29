package impl_b;

import pmp.filter.Sink;
import pmp.interfaces.Readable;

import java.io.*;
import java.security.InvalidParameterException;

public class LineOutput extends Sink<String> {
    private BufferedWriter _bufferedWriter;

    LineOutput(Readable<String> input, String path) throws InvalidParameterException {
        super(input);
        try {
            FileOutputStream fileOS = new FileOutputStream(path);
            DataOutputStream dataOS = new DataOutputStream(fileOS);
            _bufferedWriter = new BufferedWriter(new OutputStreamWriter(dataOS));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    LineOutput(String path) throws InvalidParameterException {
        super();
        try {
            FileOutputStream fileOS = new FileOutputStream(path);
            DataOutputStream dataOS = new DataOutputStream(fileOS);
            _bufferedWriter = new BufferedWriter(new OutputStreamWriter(dataOS));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(String line) throws StreamCorruptedException {
        try {

            if(line != null) {
                _bufferedWriter.write(line);
                _bufferedWriter.newLine();

                if(line.indexOf('\0') >= 0){
                    _bufferedWriter.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

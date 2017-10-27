package OktayJulian.ImplementationB;

import pmp.filter.Sink;
import pmp.interfaces.Readable;

import java.io.*;
import java.security.InvalidParameterException;

public class LineToFileOutput extends Sink<String>{
    private BufferedWriter buffer;

    LineToFileOutput(Readable<String> input, String path) throws InvalidParameterException {
        super(input);
        try {
            FileOutputStream fos = new FileOutputStream(path);
            DataOutputStream dos = new DataOutputStream(fos);
            buffer = new BufferedWriter(new OutputStreamWriter(dos));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    LineToFileOutput(String path) throws InvalidParameterException {
        super();
        try {
            FileOutputStream fos = new FileOutputStream(path);
            DataOutputStream dos = new DataOutputStream(fos);
            buffer = new BufferedWriter(new OutputStreamWriter(dos));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(String line) throws StreamCorruptedException {
        try {
            if(line != null) {
                buffer.write(line);
                buffer.newLine();
                if(line.indexOf('\0') >= 0){
                    buffer.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

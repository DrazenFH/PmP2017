package impl;

import pmp.filter.Source;
import pmp.pipes.SimplePipe;

import java.io.*;

public class Input extends Source<String> {

    FileInputStream inputStream;
    BufferedReader bufferedReader;
    StringBuilder out;
    String line;


    public Input(String path) throws FileNotFoundException {

        inputStream = new FileInputStream(path);
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        out = new StringBuilder();
        line = new String();

    }

    @Override
    public String read() throws StreamCorruptedException {

        try{

            return readNextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public String readNextLine() throws IOException {

        if ((line = bufferedReader.readLine()) != null) {
            return line;
        }

        return null;

    }

}

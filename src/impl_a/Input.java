package impl_a;

import pmp.filter.Source;

import java.io.*;

public class Input extends Source<String> {

    FileInputStream inputStream;
    DataInputStream dataInputStream;
    BufferedReader bufferedReader;
    StringBuilder out;
    String line;


    public Input(String path) throws FileNotFoundException {

        inputStream = new FileInputStream(path);
        dataInputStream = new DataInputStream(inputStream);
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
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

        if((line = this.bufferedReader.readLine()) != null) {
            return line;
        }

        //return null;
        return "\0";

    }

    void closeStream(){
        try {
            this.bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

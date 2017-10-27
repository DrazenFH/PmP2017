package OktayJulian.ImplementationA;

import pmp.filter.Source;

import java.io.*;

public class Input extends Source<String> {
    private BufferedReader buffer;

    Input(String path) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        DataInputStream dis = new DataInputStream(fis);
        this.buffer = new BufferedReader(new InputStreamReader(dis));
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

    private String readNextLine() throws IOException {
        String line;
        if((line = this.buffer.readLine()) != null) {
            return line;
        }
        return "\0";
    }

    void closeStream(){
        try {
            this.buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

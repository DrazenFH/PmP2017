package OktayJulian.ImplementationB;

import pmp.filter.Source;
import pmp.interfaces.Writeable;

import java.io.*;
import java.nio.charset.Charset;

public class FileToCharacterInput extends Source<Character> {
    private BufferedReader buffer;

    FileToCharacterInput(Writeable<Character> output, String path) throws FileNotFoundException {
        super(output);
        InputStream in = new FileInputStream(path);
        Reader reader = new InputStreamReader(in, Charset.defaultCharset());
        this.buffer = new BufferedReader(reader);
    }

    @Override
    public Character read() throws StreamCorruptedException {
        try {
            int r;
            if ((r = buffer.read()) != -1) {
                return (char) r;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        Character output;
        try {
            do {
                if (m_Output == null)
                    throw new StreamCorruptedException("output filter is null");

                output = read();
                m_Output.write(output);
            } while(output != null);
            m_Output.write('\0');
            epilogue();

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}

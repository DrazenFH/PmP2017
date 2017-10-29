package impl1;

import pmp.filter.Source;
import pmp.interfaces.Writeable;

import java.io.*;
import java.nio.charset.Charset;

public class CharacterInput extends Source<Character> {
    private BufferedReader _bufferedWriter;
    private InputStream _inputStream;
    private Reader _reader;

    CharacterInput(Writeable<Character> output, String path) throws FileNotFoundException {
        super(output);
        _inputStream = new FileInputStream(path);
        _reader = new InputStreamReader(_inputStream, Charset.defaultCharset());
        _bufferedWriter = new BufferedReader(_reader);
    }

    @Override
    public Character read() throws StreamCorruptedException {
        try {
            int character;
            if ((character = _bufferedWriter.read()) != -1) {
                return (char) character;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void run(){
        Character out;
        try {
            do {
                if (m_Output == null)
                    throw new StreamCorruptedException("output filter is null");

                out = read();
                m_Output.write(out);

            } while(out != null);

            m_Output.write('\0');
            epilogue();

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}

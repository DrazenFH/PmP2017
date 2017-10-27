package OktayJulian.pmp.pipes;

import pmp.interfaces.IOable;
import pmp.interfaces.Readable;
import pmp.interfaces.Writeable;

import javax.activity.InvalidActivityException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DoubleExitPushPipe<T> implements IOable<T, T> {
    private List<Writeable<T>> m_Output = null;

    public DoubleExitPushPipe(Writeable<T>... output)   {
        if (output == null){
            throw new InvalidParameterException("output filter can't be null!");
        }
        m_Output = new LinkedList<>(Arrays.asList(output));
    }

    public T read() {
        try {
            throw new InvalidActivityException();
        } catch (InvalidActivityException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(T input) throws StreamCorruptedException {
        if ( m_Output == null )
            throw new InvalidParameterException("output filter can't be null!");

        m_Output.forEach((out) -> {
            try {
                out.write(input);
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            }
        });
    }
}

import org.mockito.Mockito;

public class Main {
    interface ISource {
        char readChar();
        int readChars(char[] buffer, int offset, int count);
    }

    interface IDestination {
        void writeChar(char c);
        void writeChars(char[] values, int offset, int count);
    }

    static class Copier {
        private final ISource source;
        private final IDestination destination;

        public Copier(ISource source, IDestination destination) {
            this.source = source;
            this.destination = destination;
        }

        public void copy() {
            char c;
            while ((c = source.readChar()) != '\n') {
                destination.writeChar(c);
            }
        }

        public void copyMultiChars(int count) {
            char[] buffer = new char[count];
            int bytesRead;
            while ((bytesRead = source.readChars(buffer, 0, count)) != 0) {
                destination.writeChars(buffer, 0, bytesRead);
            }
        }
    }

        public static void main(String[] args) {

            ISource mockSource = Mockito.mock(ISource.class);
            Mockito.when(mockSource.readChar()).thenReturn('T', 'e', 's', 't', '\n');
            IDestination mockDestination = Mockito.mock(IDestination.class);
            Copier copier = new Copier(mockSource, mockDestination);
            copier.copy();
            Mockito.verify(mockDestination, Mockito.times(4)).writeChar(Mockito.anyChar());

            Mockito.when(mockSource.readChars("Hello World".toCharArray(), 9,8)).thenReturn(9);
            copier.copyMultiChars(9);

        }


}
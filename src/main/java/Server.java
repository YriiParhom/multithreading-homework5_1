import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", 8089));

        while (true) {
            try (SocketChannel socketChannel = serverChannel.accept()) {
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    int bytesCount = socketChannel.read(inputBuffer);
                    if (bytesCount == -1) break;

                    final String msg = new String(inputBuffer.array(), 0, bytesCount,
                            StandardCharsets.UTF_8);
                    inputBuffer.clear();

                    int value = Integer.parseInt(msg);
                    long n0 = 0;
                    long n1 = 1;
                    long tempNthTerm;
                    if (value == 0 || value == 1) {
                        n1 = value;
                    } else {
                        for (int i = 2; i < value; i++) {
                            tempNthTerm = n0 + n1;
                            n0 = n1;
                            n1 = tempNthTerm;
                        }
                    }
                    String res = Long.toString(n1);
                    socketChannel.write(ByteBuffer.wrap((msg + "-й член ряда Фибоначчи = " + res)
                            .getBytes(StandardCharsets.UTF_8)));
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088);

        try (Socket socket = serverSocket.accept()){
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String msg;
            while ((msg = in.readLine()) != null){
                if (msg.equals("end") || msg.equals("утв")) break;
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
                out.println(value + "-е число ряда Фибоначчи = " + n1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

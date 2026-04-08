package B9_Session9.B2_Ex2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PrimeCheckerServer
{
    public static void main(String[] args)
    {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server (DataStream) đang đợi kết nối...");
            while (true)
            {
                try (Socket socket = serverSocket.accept();
                     DataInputStream dis = new DataInputStream(socket.getInputStream());
                     DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

                    var number = dis.readDouble();


                    dos.writeUTF(checkPrime(number));
                    dos.flush();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String checkPrime(double n)
    {
        if (n != Math.floor(n)) return "No";
        int num = (int) n;
        if (num <= 1) return " Yes";
        for (int i = 2; i <= Math.sqrt(num); i++) if (num % i == 0) return "No";
        return "Yes";
    }
}

package B3.E4;
import java.io.*;
import java.net.*;

public class MulticastServer {
    private static final String GROUP_ADDRESS = "239.255.0.1";
    private static final int PORT = 7000;
    private static final int BUFFER_SIZE = 1024; // 1 KB

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket();
             FileInputStream fis = new FileInputStream("src/B3/E4/dataB3E4.txt")) {

            InetAddress group = InetAddress.getByName(GROUP_ADDRESS);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            System.out.println("Bắt đầu gửi file...");

            while ((bytesRead = fis.read(buffer)) != -1) {
                DatagramPacket packet = new DatagramPacket(buffer, bytesRead, group, PORT);
                socket.send(packet);

                Thread.sleep(100);
            }

            // Gửi thông báo kết thúc
            byte[] doneMsg = "DONE".getBytes();
            socket.send(new DatagramPacket(doneMsg, doneMsg.length, group, PORT));

            System.out.println("Gửi file hoàn tất!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

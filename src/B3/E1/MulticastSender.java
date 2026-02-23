package B3.E1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastSender
{
    public static final String GROUP_ADDRESS = "224.0.0.1";
    public static final String FILE_PATH = "src/B3/E1/inputS3E1.txt";
    public static final int PORT = 8888;
    public static void main(String[] args) throws InterruptedException {
        DatagramSocket socket = null;
        try {
            // Get the address that we are going to connect to.
            InetAddress address = InetAddress.getByName(GROUP_ADDRESS);

            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            // Create a new Multicast socket
            int charWordData;

            System.out.println("Start send file: " + FILE_PATH);
            socket = new DatagramSocket();
            // Đọc từng dòng từ file cho đến khi hết
            while ((charWordData = reader.read()) != -1) {
                // Chuyển dòng văn bản thành byte
                char word = (char) charWordData;
                String msg = String.valueOf(word);
                byte[] buffer = msg.getBytes();

                // Tạo gói tin
                DatagramPacket outPacket = new DatagramPacket(buffer, buffer.length, address, PORT);
                socket.send(outPacket);

                System.out.println("Server đã gửi: " + word);
                Thread.sleep(10);
            }
            System.out.println("Hoàn thành gửi toàn bộ ký tự.");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}

package B3.E2;

import java.io.FileOutputStream;
import java.net.*;

public class MulticastImageReceiver
{
    public static void main(String[] args) {
        String multicastIP = "224.0.0.1";
        int port = 8888;
        String outputPath = "received_image.jpg";

        try {
            MulticastSocket socket = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName(multicastIP);
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

            // Join vào nhóm Multicast
            socket.joinGroup(new InetSocketAddress(group, port), networkInterface);

            FileOutputStream fos = new FileOutputStream(outputPath);
            byte[] buffer = new byte[8192];

            System.out.println("Đang chờ nhận ảnh...");

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                // Kiểm tra nếu là tín hiệu kết thúc
                String msg = new String(packet.getData(), 0, packet.getLength());
                if (msg.equals("END")) {
                    break;
                }

                fos.write(packet.getData(), 0, packet.getLength());
            }

            System.out.println("Đã nhận xong và lưu thành: " + outputPath);
            fos.close();
            socket.leaveGroup(new InetSocketAddress(group, port), networkInterface);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

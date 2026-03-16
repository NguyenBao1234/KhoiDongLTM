package B4_Session4.B9_Ex9;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPBroadcastClient
{
    public static void main(String[] args) {
        int port = 8888;

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Đang lắng nghe thông báo từ mạng LAN (Cổng " + port + ")...");

            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Chương trình sẽ dừng ở đây cho đến khi có tin broadcast đến
                socket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("\n[THÔNG BÁO MỚI]: " + received);
                System.out.println("Từ địa chỉ IP: " + packet.getAddress().getHostAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

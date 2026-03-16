package B4_Session4.B9_Ex9;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPBroadcastServer
{
    public static void main(String[] args) {
        int port = 8888;
        String broadcastMessage = "THÔNG BÁO: Hệ thống sẽ bảo trì vào lúc 12h đêm nay!";

        try (DatagramSocket socket = new DatagramSocket()) {
            // QUAN TRỌNG: Cho phép socket gửi tin broadcast
            socket.setBroadcast(true);

            // Gửi tin đến địa chỉ 255.255.255.255 (toàn mạng)
            InetAddress address = InetAddress.getByName("255.255.255.255");

            byte[] buffer = broadcastMessage.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

            System.out.println("Đang phát tin broadcast...");
            socket.send(packet);
            System.out.println("Đã gửi thông báo thành công.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

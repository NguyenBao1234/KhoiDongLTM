package B4_Session4.B10_Ex10;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPMulticastServer
{
    public static void main(String[] args) {
        String groupAddress = "230.0.0.1"; // Địa chỉ IP của nhóm Multicast
        int port = 4446;

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress group = InetAddress.getByName(groupAddress);

            String message = "Dữ liệu cảm biến nhiệt độ: 28°C";
            byte[] buffer = message.getBytes();

            System.out.println("Đang phát dữ liệu Multicast đến nhóm " + groupAddress + "...");

            // Gửi thông tin định kỳ mỗi 2 giây
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
                socket.send(packet);
                System.out.println("Đã gửi: " + message);
                Thread.sleep(2000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package B4_Session4.B10_Ex10;

import java.net.*;

public class UDPMulticastClient
{
    public static void main(String[] args) {
        String groupAddress = "230.0.0.1";
        int port = 4446;

        // Lưu ý: Sử dụng MulticastSocket thay vì DatagramSocket
        try (MulticastSocket socket = new MulticastSocket(port)) {
            InetAddress group = InetAddress.getByName(groupAddress);
            InetSocketAddress groupSocket = new InetSocketAddress(group, port);

            // Tìm interface mạng (cần thiết cho Java đời mới)
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

            // 1. Gia nhập nhóm Multicast
            socket.joinGroup(groupSocket, networkInterface);
            System.out.println("Đã tham gia nhóm Multicast: " + groupAddress);

            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // Đợi nhận dữ liệu

                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Nhận từ Group: " + received);
            }

            // socket.leaveGroup(groupSocket, networkInterface); // Rời nhóm khi xong
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

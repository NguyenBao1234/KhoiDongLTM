package B3.E5;

import java.net.*;

public class MulticastTimeClient {
    public static void main(String[] args) {
        String groupAddr = "239.1.1.1";
        int port = 9999;

        try
        {
            MulticastSocket socket = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName(groupAddr);
            NetworkInterface netIf = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

            // Join group (Subscribe)
            socket.joinGroup(new InetSocketAddress(group, port), netIf);
            System.out.println("Đã tham gia nhóm Multicast. Chờ nhận dữ liệu...");

            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String receivedTimeStr = new String(packet.getData(), 0, packet.getLength());
                long serverTime = Long.parseLong(receivedTimeStr);
                long localTime = System.currentTimeMillis();

                System.out.println("Thời gian từ Server: " + new java.util.Date(serverTime));
                // Mở rộng: Đo độ trễ (Latency)
                System.out.println("Độ trễ: " + (localTime - serverTime) + " ms");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
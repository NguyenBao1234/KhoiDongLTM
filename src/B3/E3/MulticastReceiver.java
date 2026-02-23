package B3.E3;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;

public class MulticastReceiver
{
    public static void main(String[] args)
    {
        String groupAddress = "224.0.0.1";
        int port = 5000;

        try
        {
            MulticastSocket socket = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName(groupAddress);
            InetSocketAddress groupSocket = new InetSocketAddress(group, port);

            // Lấy network interface mặc định để join group
            NetworkInterface netIf = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

            // Tham gia vào nhóm multicast
            socket.joinGroup(groupSocket, netIf);
            System.out.println("Đã tham gia nhóm " + groupAddress + ". Đang đợi dữ liệu...");

            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // Chờ nhận gói tin

                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Nhận được từ " + packet.getAddress() + ": " + received);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
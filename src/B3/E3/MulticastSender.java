package B3.E3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastSender
{
    public static void main(String[] args)
    {
        String groupAddress = "224.0.0.1";
        int port = 5000;
        String message = "Hello multicast!";

        try
        {
            DatagramSocket socket = new DatagramSocket();
            InetAddress group = InetAddress.getByName(groupAddress);

            System.out.println("Bắt đầu gửi dữ liệu tới nhóm " + groupAddress + "...");

            while (true)
            {
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);

                socket.send(packet);
                System.out.println("Đã gửi: " + message);

                Thread.sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
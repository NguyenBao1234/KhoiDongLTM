package B3.E5;

import java.net.*;
import java.util.Date;

public class MulticastTimeServer {
    public static void main(String[] args) {
        String groupAddr = "239.1.1.1";
        int port = 9999;

        try
        {
            DatagramSocket socket = new DatagramSocket();
            System.out.println("Server đang phát thời gian mỗi giây...");
            InetAddress group = InetAddress.getByName(groupAddr);

            while (true)
            {
                String currentTime = String.valueOf(System.currentTimeMillis());
                byte[] buffer = currentTime.getBytes();

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
                socket.send(packet);

                System.out.println("Đã gửi: " + new Date());
                Thread.sleep(1000); // Nghỉ 1 giây
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
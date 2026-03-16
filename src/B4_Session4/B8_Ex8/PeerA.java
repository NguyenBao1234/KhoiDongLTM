package B4_Session4.B8_Ex8;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class PeerA
{
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(9000)) {
            System.out.println("Peer A: Đang nghe tại cổng 9000...");

            new Thread(() -> {
                try {
                    byte[] buf = new byte[1024];
                    while (true) {
                        DatagramPacket p = new DatagramPacket(buf, buf.length);
                        socket.receive(p); // Chương trình sẽ treo ở đây đợi gói tin
                        System.out.println("\nNhận: " + new String(p.getData(), 0, p.getLength()));
                        System.out.print("Gửi: ");
                    }
                } catch (Exception e) { /* Bỏ qua lỗi nhận để duy trì thread */ }
            }).start();

            // Luồng Gửi
            Scanner sc = new Scanner(System.in);
            InetAddress ip = InetAddress.getByName("localhost");
            while (true) {
                System.out.print("Gửi: ");
                String msg = sc.nextLine();
                byte[] data = msg.getBytes();
                // Chỉ định đích đến ngay trong gói tin
                DatagramPacket p = new DatagramPacket(data, data.length, ip, 9001);
                socket.send(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package B4_Session4.B8_Ex8;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class PeerB
{
    public static void main(String[] args) {

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(9001); // Cổng của PeerB
            System.out.println("Peer B: Đang chạy tại cổng 9001...");

            final DatagramSocket finalSocket = socket;

            Thread receiver = new Thread(() -> {
                try {
                    byte[] buffer = new byte[1024];
                    while (!finalSocket.isClosed()) {
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        finalSocket.receive(packet); // Đợi tin nhắn

                        String msg = new String(packet.getData(), 0, packet.getLength());
                        System.out.println("\nPeer A: " + msg);
                        System.out.print("Bạn: ");
                    }
                } catch (Exception e) {
                    if (!finalSocket.isClosed()) {
                        System.err.println("Lỗi nhận dữ liệu: " + e.getMessage());
                    }
                }
            });
            receiver.start();

            // 2. Luồng GỬI (Sender) - Chạy trực tiếp trên luồng main
            Scanner sc = new Scanner(System.in);
            InetAddress address = InetAddress.getByName("localhost");

            while (true) {
                System.out.print("Bạn: ");
                String msg = sc.nextLine();

                // Gõ 'exit' để đóng chat
                if (msg.equalsIgnoreCase("exit")) break;

                byte[] data = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, 9000);
                finalSocket.send(packet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Đã đóng kết nối.");
            }
        }
    }
}

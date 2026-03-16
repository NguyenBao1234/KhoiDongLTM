package B4_Session4.B11_Ex11;
import java.net.*;
import java.util.Scanner;
public class UDPGameClient
{
    public static void main(String[] args) {
        String host = "localhost";
        int serverPort = 9876;
        int broadcastPort = 8888;

        try (DatagramSocket clientSocket = new DatagramSocket();
             Scanner sc = new Scanner(System.in)) {

            // Luồng phụ: Nghe thông báo Broadcast khi có người thắng
            new Thread(() -> {
                try (DatagramSocket bSocket = new DatagramSocket(broadcastPort)) {
                    byte[] buf = new byte[1024];
                    while (true) {
                        DatagramPacket p = new DatagramPacket(buf, buf.length);
                        bSocket.receive(p);
                        System.out.println("\n[HỆ THỐNG]: " + new String(p.getData(), 0, p.getLength()));
                        System.exit(0); // Kết thúc game
                    }
                } catch (Exception e) {}
            }).start();

            System.out.println("--- CHÀO MỪNG ĐẾN VỚI GAME ĐOÁN SỐ (1-1000) ---");
            InetAddress serverAddr = InetAddress.getByName(host);

            while (true) {
                System.out.print("Nhập số dự đoán: ");
                String guess = sc.nextLine();

                byte[] sendData = guess.getBytes();
                clientSocket.send(new DatagramPacket(sendData, sendData.length, serverAddr, serverPort));

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                System.out.println("Server trả lời: " + new String(receivePacket.getData(), 0, receivePacket.getLength()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

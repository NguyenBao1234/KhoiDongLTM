package B4_Session4.B3_Ex3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServerWordSwitch
{
    public static void main(String[] args)
    {
        int port = 9876;
        try (DatagramSocket serverSocket = new DatagramSocket(port))
        {
            System.out.println("Server xử lý chuỗi đang chạy tại cổng " + port + "...");

            byte[] receiveData = new byte[1024];

            while (true) {
                // 1. Nhận gói tin từ Client
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String input = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Dữ liệu gốc từ Client: " + input);

                // 2. Logic xử lý:
                String result;
                if (input.equals(input.toUpperCase()) && !input.equals(input.toLowerCase())) {
                    result = input.toLowerCase();
                } else {
                    result = input.toUpperCase();
                }

                // 3. Gửi kết quả ngược lại cho Client
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                byte[] sendData = result.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
                System.out.println("---------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

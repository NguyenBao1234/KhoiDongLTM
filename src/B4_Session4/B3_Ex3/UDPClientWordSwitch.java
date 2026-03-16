package B4_Session4.B3_Ex3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClientWordSwitch
{
    public static void main(String[] args)
    {
        String hostname = "localhost";
        int port = 9876;

        try (DatagramSocket clientSocket = new DatagramSocket();
             Scanner sc = new Scanner(System.in)) {

            InetAddress serverAddress = InetAddress.getByName(hostname);

            System.out.print("Nhập chuỗi bất kỳ: ");
            String message = sc.nextLine();

            // Gửi dữ liệu
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, port);
            clientSocket.send(sendPacket);

            // Nhận kết quả từ Server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String result = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Kết quả từ Server: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

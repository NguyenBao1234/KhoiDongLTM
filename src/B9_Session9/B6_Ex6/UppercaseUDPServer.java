package B9_Session9.B6_Ex6;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UppercaseUDPServer
{
    public static void main(String[] args)
    {
        int port = 9876;
        try (DatagramSocket serverSocket = new DatagramSocket(port))
        {
            System.out.println("Server running at " + port + "...");

            byte[] receiveData = new byte[1024];

            while (true)
            {
                // Nhận
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Nhận từ Client: " + message);

                // Lấy thông tin địa chỉ và cổng của Client để gửi ngược lại
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                byte[] sendData = message.toUpperCase().getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

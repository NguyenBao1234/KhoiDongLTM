package B9_Session9.B6_Ex6;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UppercaseUDPClient
{
    public static void main(String[] args)
    {
        String hostname = "localhost";
        int port = 9876;

        try (DatagramSocket clientSocket = new DatagramSocket();
             Scanner sc = new Scanner(System.in)) {

            InetAddress serverAddress = InetAddress.getByName(hostname);

            System.out.print("Nhập chuỗi cần gửi: ");
            String message = sc.nextLine();


            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, port);
            clientSocket.send(sendPacket);

            // 2. Nhận phản hồi từ Server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Phản hồi từ Server: " + modifiedSentence);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

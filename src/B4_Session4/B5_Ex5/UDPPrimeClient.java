package B4_Session4.B5_Ex5;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPPrimeClient
{
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 9879;

        try (DatagramSocket clientSocket = new DatagramSocket();
             Scanner sc = new Scanner(System.in)) {

            InetAddress serverAddress = InetAddress.getByName(hostname);

            System.out.println("--- UDP Prime Number ---");
            System.out.print("Nhập số: ");
            String message = sc.nextLine();

            // Gửi biểu thức
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, port);
            clientSocket.send(sendPacket);

            // Nhận kết quả
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            System.out.println(new String(receivePacket.getData(), 0, receivePacket.getLength()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

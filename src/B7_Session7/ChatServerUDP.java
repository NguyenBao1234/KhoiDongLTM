//Nguyễn Văn Bạo 2255020008 22CDP
package B7_Session7;

import javax.swing.JOptionPane;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ChatServerUDP {
    public static void main(String[] args)
    {
        int port = 9876;
        try (DatagramSocket serverSocket = new DatagramSocket(port))
        {
            byte[] receiveData = new byte[1024];

            while (true)
            {
                // receive from Client
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String clientMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());

                String response = JOptionPane.showInputDialog(null,"Client hỏi: " + clientMsg + "\nServer Nhap:","Server", JOptionPane.QUESTION_MESSAGE);

                if (response == null) break; // Nhấn Cancel để thoát

                // Server send
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                byte[] sendData = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
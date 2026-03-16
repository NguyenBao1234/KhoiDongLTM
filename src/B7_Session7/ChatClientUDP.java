//Nguyễn Văn Bạo 2255020008 22CDP
package B7_Session7;

import javax.swing.JOptionPane;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ChatClientUDP
{
    public static void main(String[] args)
    {
        String serverHostname = "localhost";
        int port = 9876;

        try (DatagramSocket clientSocket = new DatagramSocket())
        {
            InetAddress IPAddress = InetAddress.getByName(serverHostname);
            byte[] receiveData = new byte[1024];

            while (true)
            {
                String message = JOptionPane.showInputDialog(null,"Client Nhap:","Client", JOptionPane.QUESTION_MESSAGE);

                if (message == null) break;
                //send
                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                clientSocket.send(sendPacket);

                // result from Server
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                String serverMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());

                JOptionPane.showMessageDialog(null, "Server trả lời: " + serverMsg);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
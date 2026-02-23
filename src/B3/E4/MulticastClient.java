package B3.E4;

import java.io.FileOutputStream;
import java.net.*;

public class MulticastClient
{
    private static final String GROUP_ADDRESS = "239.255.0.1";
    private static final int PORT = 7000;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args)
    {
        try
        {
            InetAddress group = InetAddress.getByName(GROUP_ADDRESS);
            MulticastSocket socket = new MulticastSocket(PORT);

            // Tham gia vào nhóm Multicast
            socket.joinGroup(new InetSocketAddress(group, PORT),
                    NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));

            FileOutputStream fos = new FileOutputStream("receivedS3E4.txt");
            byte[] buffer = new byte[BUFFER_SIZE];

            System.out.println("Đang chờ nhận dữ liệu...");

            while (true)
            {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                if (message.equals("DONE")) {
                    System.out.println("Đã nhận đủ file!");
                    break;
                }

                fos.write(packet.getData(), 0, packet.getLength());
            }

            fos.close();
            socket.leaveGroup(new InetSocketAddress(group, PORT),
                    NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

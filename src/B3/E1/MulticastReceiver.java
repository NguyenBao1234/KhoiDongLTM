package B3.E1;

import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.StandardSocketOptions;

public class MulticastReceiver
{
    public static final String OUTPUT_FILE = "outputS3E1.txt";
    public static final byte[] BUFFER = new byte[4096];
    public static void main(String[] args) {
        MulticastSocket socket = null;
        DatagramPacket inPacket = null;
        try {
            // Get the address that we are going to connect to.
            InetAddress address = InetAddress.getByName(MulticastSender.GROUP_ADDRESS);
            // Create a new Multicast socket
            socket = new MulticastSocket(MulticastSender.PORT);
            socket.setOption(StandardSocketOptions.IP_MULTICAST_LOOP, false);
            // Joint the Multicast group
            socket.joinGroup(address);

            System.out.println("Đang chờ nhận dữ liệu và ghi vào file " + OUTPUT_FILE + "...");
            FileWriter writer = new FileWriter(OUTPUT_FILE);
            while (true) {
                // Receive the information and print it.
                inPacket = new DatagramPacket(BUFFER, BUFFER.length);
                socket.receive(inPacket);
                String msg = new String(BUFFER, 0, inPacket.getLength());
                System.out.println("From " + inPacket.getAddress() + " Msg : " + msg);
                // Ghi vào file
                writer.write(msg);
                // Flush để dữ liệu được ghi xuống đĩa ngay lập tức
                writer.flush();
                Thread.sleep(1000);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

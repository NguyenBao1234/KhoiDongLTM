package B4_Session4.B7_Ex7;

import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class UDPFileClient
{
    public static void main(String[] args) {
        String serverHost = "localhost";
        int port = 9874;
        String fileToRequest = "SnvvNghenMeme.jpg"; //
        String fileOutput = "received_" + fileToRequest;

        try (DatagramSocket clientSocket = new DatagramSocket();
             FileOutputStream fos = new FileOutputStream(fileOutput)) {

            InetAddress serverAddr = InetAddress.getByName(serverHost);

            // 1. Gửi yêu cầu lấy file
            byte[] requestData = fileToRequest.getBytes();
            clientSocket.send(new DatagramPacket(requestData, requestData.length, serverAddr, port));

            byte[] receiveBuffer = new byte[2048];
            System.out.println("Đang nhận file...");

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);

                // Gói tin trống là tín hiệu kết thúc
                if (receivePacket.getLength() == 0) break;

                // 2. Tách Sequence Number và Dữ liệu
                ByteBuffer wrapped = ByteBuffer.wrap(receivePacket.getData(), 0, receivePacket.getLength());
                int seqNum = wrapped.getInt();

                byte[] fileContent = new byte[receivePacket.getLength() - 4];
                wrapped.get(fileContent);

                fos.write(fileContent);
                System.out.println("Đã nhận gói: " + seqNum);
            }

            System.out.println("Lưu file thành công: " + fileOutput);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package B4_Session4.B7_Ex7;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class UDPFileServer {
    public static void main(String[] args) {
        int port = 9874;
        int bufferSize = 1024; // Kích thước dữ liệu mỗi gói

        try (DatagramSocket serverSocket = new DatagramSocket(port))
        {
            System.out.println("File Server đang đợi yêu cầu...");

            byte[] receiveBuffer = new byte[1024];
            DatagramPacket requestPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            serverSocket.receive(requestPacket);

            String fileName = new String(requestPacket.getData(), 0, requestPacket.getLength()).trim();
            String path = "src/B4_Session4/B7_Ex7/" + fileName;
            File file = new File(path);

            if (!file.exists()) {
                System.out.println("File không tồn tại!");
                return;
            }

            FileInputStream fis = new FileInputStream(file);
            InetAddress clientAddr = requestPacket.getAddress();
            int clientPort = requestPacket.getPort();

            byte[] fileBuffer = new byte[bufferSize];
            int bytesRead;
            int sequenceNumber = 0;

            while ((bytesRead = fis.read(fileBuffer)) != -1) {
                // Tạo gói tin: 4 byte đầu là STT + dữ liệu
                ByteBuffer packetBuffer = ByteBuffer.allocate(4 + bytesRead);
                packetBuffer.putInt(sequenceNumber);
                packetBuffer.put(fileBuffer, 0, bytesRead);

                byte[] dataToSend = packetBuffer.array();
                DatagramPacket sendPacket = new DatagramPacket(dataToSend, dataToSend.length, clientAddr, clientPort);
                serverSocket.send(sendPacket);

                System.out.println("Đã gửi gói số: " + sequenceNumber);
                sequenceNumber++;
                Thread.sleep(10); // Nghỉ ngắn để tránh tràn buffer ở Client
            }

            // Gửi gói tin trống để báo hiệu kết thúc
            serverSocket.send(new DatagramPacket(new byte[0], 0, clientAddr, clientPort));
            System.out.println("Gửi file hoàn tất.");
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
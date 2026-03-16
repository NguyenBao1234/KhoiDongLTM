package B4_Session4.B6_Ex6;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UDPTimeServer
{
    public static void main(String[] args) {
        int port = 9875;
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println("Time Server đang chạy tại cổng " + port + "...");

            byte[] receiveData = new byte[1024];

            while (true) {
                // 1. Nhận yêu cầu từ Client
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String request = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                System.out.println("Yêu cầu nhận được: " + request);

                String response;
                if (request.equalsIgnoreCase("TIME")) {
                    // 2. Lấy và định dạng thời gian hiện tại
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    response = now.format(formatter);
                } else {
                    response = "Lỗi: Hãy gửi lệnh 'TIME' để lấy thời gian.";
                }

                // 3. Gửi lại cho Client
                byte[] sendData = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
                System.out.println("Đã gửi phản hồi: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

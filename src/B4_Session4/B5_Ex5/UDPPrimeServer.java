package B4_Session4.B5_Ex5;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPPrimeServer
{
    public static void main(String[] args) {
        int port = 9879;
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println("Server kiểm tra số nguyên tố đang chạy tại cổng " + port + "...");

            byte[] receiveData = new byte[1024];

            while (true) {
                // 1. Nhận gói tin từ Client
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String input = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                System.out.println("Nhận từ Client số: " + input);

                String result;
                try {
                    int number = Integer.parseInt(input);
                    if (isPrime(number)) {
                        result = "YES";
                    } else {
                        result = "NO";
                    }
                } catch (NumberFormatException e) {
                    result = "Lỗi: Vui lòng gửi một số nguyên hợp lệ!";
                }

                // 2. Gửi phản hồi lại cho Client
                byte[] sendData = result.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
                System.out.println("Phản hồi: " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm kiểm tra số nguyên tố
    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
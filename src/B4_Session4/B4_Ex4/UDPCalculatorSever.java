package B4_Session4.B4_Ex4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPCalculatorSever
{

    public static void main(String[] args)
    {
        int port = 9877;
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println("Calculator Server đang chạy tại cổng " + port + "...");

            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String expression = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                System.out.println("Yêu cầu từ Client: " + expression);

                String result;
                try {
                    result = calculate(expression);
                } catch (Exception e) {
                    result = "Lỗi: Định dạng không hợp lệ (VD: 10 + 5)";
                }

                // Gửi kết quả lại cho Client
                byte[] sendData = result.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String calculate(String msg) {
        String[] parts = msg.split("\\s+"); // Tách chuỗi bởi khoảng trắng
        if (parts.length != 3) throw new IllegalArgumentException();

        double num1 = Double.parseDouble(parts[0]);
        String operator = parts[1];
        double num2 = Double.parseDouble(parts[2]);
        double res = 0;

        switch (operator) {
            case "+": res = num1 + num2; break;
            case "-": res = num1 - num2; break;
            case "*": res = num1 * num2; break;
            case "/":
                if (num2 == 0) return "Lỗi: Không thể chia cho 0";
                res = num1 / num2;
                break;
            default: return "Lỗi: Toán tử không hỗ trợ (" + operator + ")";
        }
        return "Kết quả: " + res;
    }
}

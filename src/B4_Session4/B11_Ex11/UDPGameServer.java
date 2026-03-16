package B4_Session4.B11_Ex11;
import java.net.*;
import java.util.Random;

public class UDPGameServer {
    public static void main(String[] args) {
        int port = 9876;
        int broadcastPort = 8888;
        Random rd = new Random();
        int targetNumber = rd.nextInt(1000) + 1;
        boolean isGameOver = false;

        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            serverSocket.setBroadcast(true);
            System.out.println("--- GAME ĐOÁN SỐ ĐANG CHẠY ---");
            System.out.println("Số bí mật (chỉ server biết): " + targetNumber);

            byte[] receiveData = new byte[1024];

            while (!isGameOver) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String clientInput = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                try {
                    int guess = Integer.parseInt(clientInput);
                    String response;

                    if (guess < targetNumber) {
                        response = "So của tôi Lớn hơn!";
                    } else if (guess > targetNumber) {
                        response = "Số của tôi Nhỏ hơn!";
                    } else {
                        response = "CHÍNH XÁC!";
                        isGameOver = true;
                        // Thông báo cho người thắng
                        sendResponse(serverSocket, "BẠN ĐÃ THẮNG!", clientAddress, clientPort);
                        // Broadcast cho tất cả mọi người cùng biết
                        String winnerMsg = "GAME KẾT THÚC! Người chơi " + clientAddress + " đã đoán đúng số " + targetNumber;
                        broadcast(serverSocket, winnerMsg, broadcastPort);
                        break;
                    }
                    sendResponse(serverSocket, response, clientAddress, clientPort);

                } catch (NumberFormatException e) {
                    sendResponse(serverSocket, "Vui lòng nhập số!", clientAddress, clientPort);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendResponse(DatagramSocket socket, String msg, InetAddress addr, int port) throws Exception {
        byte[] data = msg.getBytes();
        socket.send(new DatagramPacket(data, data.length, addr, port));
    }

    private static void broadcast(DatagramSocket socket, String msg, int port) throws Exception {
        byte[] data = msg.getBytes();
        InetAddress broadcastAddr = InetAddress.getByName("255.255.255.255");
        socket.send(new DatagramPacket(data, data.length, broadcastAddr, port));
    }
}
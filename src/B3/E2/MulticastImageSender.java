package B3.E2;

import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastImageSender
{
    public static void main(String[] args) {
        String multicastIP = "224.0.0.1";
        int port = 8888;
        String imagePath = "src/B3/E2/KonoDioDa.jpg";

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress group = InetAddress.getByName(multicastIP);
            File file = new File(imagePath);
            FileInputStream fis = new FileInputStream(file);

            byte[] buffer = new byte[8192]; //
            int bytesRead;

            System.out.println("Đang bắt đầu gửi ảnh...");

            while ((bytesRead = fis.read(buffer)) != -1) {
                DatagramPacket packet = new DatagramPacket(buffer, bytesRead, group, port);
                socket.send(packet);
                Thread.sleep(10);
            }

            // Gửi một gói tin rỗng hoặc từ khóa "END" để báo kết thúc
            byte[] endMsg = "END".getBytes();
            socket.send(new DatagramPacket(endMsg, endMsg.length, group, port));

            System.out.println("Gửi file hoàn tất!");
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

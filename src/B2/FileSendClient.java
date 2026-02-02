package B2;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class FileSendClient
{
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 9999;

        try {
            // Mở hộp thoại chọn file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chon file can gui");

            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                System.out.println("=== FILE CLIENT ===");
                System.out.println("File da chon: " + file.getAbsolutePath());

                // Kết nối đến server
                Socket socket = new Socket(serverAddress, port);
                System.out.println("✓ Ket noi den server: " + serverAddress + ":" + port);

                // Gửi tên file
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(file.getName());

                // Gửi kích thước
                long fileSize = file.length();
                dataOutputStream.writeLong(fileSize);

                System.out.println("Dang gui: " + file.getName() + " (" + fileSize + " bytes)");

                // Gửi nội dung
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead;
                long totalSent = 0;

                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    dataOutputStream.write(buffer, 0, bytesRead);
                    totalSent += bytesRead;

                    int percent = (int) ((totalSent * 100) / fileSize);
                    System.out.print("\rTien trinh: " + percent + "%");
                }

                fileInputStream.close();
                System.out.println("\n✓ Gui thanh cong!");

                // Nhận phản hồi
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String response = dis.readUTF();
                System.out.println("Server: " + response);

                socket.close();

            } else {
                System.out.println("Khong chon file!");
            }

        } catch (IOException e) {
            System.out.println("Loi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

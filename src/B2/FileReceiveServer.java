package B2;
import java.io.*;
import java.net.*;
public class FileReceiveServer
{
    public static void main(String[] args)
    {
        int port = 9999;
        String saveDirectory = "received_files/"; // Thư mục lưu file

        // Tạo thư mục nếu chưa có
        File dir = new File(saveDirectory);
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("=== FILE SERVER ===");
            System.out.println("Server dang lang nghe tai port: " + port);
            System.out.println("Thu muc luu file: " + saveDirectory);
            System.out.println("Cho client gui file...\n");

            while (true) {
                // Chấp nhận kết nối
                Socket clientSocket = serverSocket.accept();
                System.out.println("✓ Client ket noi: " + clientSocket.getInetAddress());

                // Nhận tên file
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                String fileName = dis.readUTF();
                System.out.println("Ten file: " + fileName);

                // Nhận kích thước file
                long fileSize = dis.readLong();
                System.out.println("Kich thuoc: " + fileSize + " bytes");

                // Nhận nội dung file
                FileOutputStream fos = new FileOutputStream(saveDirectory + fileName);
                byte[] buffer = new byte[4096];
                int bytesRead;
                long totalRead = 0;

                while (totalRead < fileSize && (bytesRead = dis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                    totalRead += bytesRead;

                    // Hiển thị tiến trình
                    int percent = (int) ((totalRead * 100) / fileSize);
                    System.out.print("\rDang nhan: " + percent + "%");
                }

                fos.close();

                // Gửi thông báo thành công
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                dos.writeUTF("File da duoc luu thanh cong!");

                System.out.println("\n✓ Da luu file: " + saveDirectory + fileName);
                System.out.println("Client ngat ket noi\n");

                clientSocket.close();
            }

        } catch (IOException e) {
            System.out.println("Loi server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

package B9_Session9.B5_Ex5;
import java.io.*;
import java.net.*;
import java.util.Date;

public class HttpMiniServer
{
    public static void main(String[] args)
    {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Mini HTTP Server đang chạy tại: http://localhost:" + port);

            while (true) {
                // Chấp nhận kết nối từ Browser
                try (Socket socket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(socket.getOutputStream());
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    // Đọc dòng đầu tiên của Request (VD: GET / HTTP/1.1)
                    String requestLine = in.readLine();
                    System.out.println("Yêu cầu từ Browser: " + requestLine);

                    // Nội dung trang HTML
                    String htmlBody = "<html>" +
                            "<head><meta charset='UTF-8'><title>Java HTTP Server</title></head>" +
                            "<body style='text-align:center; font-family:Arial; padding-top:50px;'>" +
                            "<h1 style='color: #2e86de;'>Xin chào từ Java Server!</h1>" +
                            "<p>Đây là phản hồi trực tiếp qua Socket TCP.</p>" +
                            "<p>Thời gian hiện tại: <b>" + new Date() + "</b></p>" +
                            "</body></html>";

                    // Gửi HTTP Response Header đúng chuẩn
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/html; charset=utf-8");
                    out.println("Content-Length: " + htmlBody.getBytes().length);
                    out.println(); // Dòng trống bắt buộc

                    // Gửi nội dung trang web
                    out.println(htmlBody);
                    out.flush(); // Đẩy dữ liệu đi ngay lập tức

                    System.out.println("Đã gửi phản hồi thành công.");
                } catch (Exception e) {
                    System.err.println("Lỗi xử lý kết nối: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package B1;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyLocalInfo {
    public static void main(String[] args) {
        try {
            // Lấy thông tin máy cục bộ
            InetAddress myDevice = InetAddress.getLocalHost();

            System.out.println("Tên máy chủ (Hostname): " + myDevice.getHostName());
            System.out.println("Địa chỉ IP của máy: " + myDevice.getHostAddress());

        } catch (UnknownHostException e) {
            System.out.println("Không thể tìm thấy thông tin máy cục bộ.");
            e.printStackTrace();
        }
    }
}
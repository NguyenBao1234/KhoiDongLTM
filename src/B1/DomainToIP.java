package B1;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DomainToIP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập tên miền (VD: www.google.com): ");
        String domain = scanner.nextLine();

        try {
            // Một tên miền có thể có nhiều IP (load balancing)
            InetAddress[] addresses = InetAddress.getAllByName(domain);

            System.out.println("Các địa chỉ IP của " + domain + " là:");
            for (InetAddress addr : addresses) {
                System.out.println("- " + addr.getHostAddress());
            }
        } catch (UnknownHostException e) {
            System.out.println("Không thể tìm thấy địa chỉ IP cho tên miền này.");
        }
    }
}

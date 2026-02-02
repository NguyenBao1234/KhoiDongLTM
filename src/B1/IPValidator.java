package B1;

import java.util.Scanner;
import java.util.regex.Pattern;

public class IPValidator {
    // Regex kiểm tra IPv4 (0-255 . 0-255 . 0-255 . 0-255)
    private static final String IPV4_PATTERN =
            "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";

    private static final Pattern pattern = Pattern.compile(IPV4_PATTERN);

    public static boolean isValidIPv4(String ip) {
        return pattern.matcher(ip).matches();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập địa chỉ IP cần kiểm tra: ");
        String ipInput = scanner.nextLine();

        if (isValidIPv4(ipInput)) {
            System.out.println("=> Địa chỉ IP HỢP LỆ.");
        } else {
            System.out.println("=> Địa chỉ IP KHÔNG hợp lệ.");
        }
    }
}
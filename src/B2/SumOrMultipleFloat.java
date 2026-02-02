package B2;
import java.util.Scanner;
public class SumOrMultipleFloat
{
    public static void main(String[] args) {
        Scanner scannerInput = new Scanner(System.in);
        System.out.println("Nhap a: ");
        float a = scannerInput.nextFloat();

        System.out.println("Nhap b: ");
        float b = scannerInput.nextFloat();

        System.out.println("Nhap c: ");
        float c = scannerInput.nextFloat();

        System.out.printf("Tong 3 so: %.2f + %.2f + %.2f = %.2f\n", a,b,c, a+b+c);
        System.out.printf("Tich 3 so: %.2f x %.2f x %.2f = %.2f\n", a,b,c, a*b*c);
    }
}

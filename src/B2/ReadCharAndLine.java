package B2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReadCharAndLine
{
    public static void main(String[] args) {
        Scanner scannerChar = new Scanner(System.in);

        System.out.println("Nhap ten file");
        String FileName = "src/B2/"+ scannerChar.nextLine();
        int WordCount = 0, LineCount = 0, CharCount = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName));
            String WordLine;
            while ((WordLine = bufferedReader.readLine())!=null)
            {
                LineCount++;
                CharCount += WordLine.length();
                if (!WordLine.trim().isEmpty()) {
                    String[] cacTu = WordLine.trim().split("\\s+");
                    WordCount += cacTu.length;}
            }
            System.out.println("\nKet qua:");
            System.out.printf("So dong: %d \n", LineCount);
            System.out.printf("So tu: %d\n", WordCount);
            System.out.printf("So ky tu: %d\n", CharCount);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scannerChar.close();
    }
}

package B4_Session4.B1_Ex1;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient
{
    private static DatagramSocket socket;
    private static JTextArea textArea;
    private static JTextField inputField;
    private static String username;
    private static InetAddress serverAddress;
    private static final int SERVER_PORT = 9876;

    public static void main(String[] args) throws Exception {

        username = JOptionPane.showInputDialog(null, "Nhập username của Client:", "Client Login", JOptionPane.PLAIN_MESSAGE);
        if (username == null || username.trim().isEmpty()) username = "Client";

        // Nhập IP server qua popup
        String serverIP = JOptionPane.showInputDialog(null, "Nhập địa chỉ IP Server:", "Kết nối Server", JOptionPane.PLAIN_MESSAGE);
        if (serverIP == null || serverIP.trim().isEmpty()) serverIP = "localhost";

        socket = new DatagramSocket();
        serverAddress = InetAddress.getByName(serverIP);

        // Tạo JFrame chat
        JFrame frame = new JFrame("Client - " + username);
        frame.setSize(450, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);

        inputField = new JTextField();
        JButton sendButton = new JButton("Gửi");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        textArea.append("[Đã kết nối tới server " + serverIP + ":" + SERVER_PORT + "]\n");

        // Action gửi tin
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        // Thread nhận tin
        Thread receiveThread = new Thread(() -> {
            byte[] receiveData = new byte[1024];
            while (true)
            {
                try
                {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);

                    String message = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();

                    // Tách username và nội dung
                    String[] parts = message.split(":", 2);
                    String senderName = parts.length > 1 ? parts[0] : "Unknown";
                    String content = parts.length > 1 ? parts[1] : message;

                    if (content.equalsIgnoreCase("bye")) {
                        textArea.append(senderName + " đã ngắt kết nối.\n");
                        textArea.append("[Chương trình kết thúc]\n");
                        socket.close();
                        break;
                    }

                    textArea.append(senderName + " : " + content + "\n");
                } catch (Exception ex) {
                    if (socket.isClosed()) break;
                    ex.printStackTrace();
                }
            }
        });
        receiveThread.setDaemon(true);
        receiveThread.start();
    }

    private static void sendMessage() {
        try {
            String content = inputField.getText().trim();
            if (content.isEmpty()) return;

            String message = username + ":" + content;
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
            socket.send(sendPacket);
            textArea.append(username + " : " + content + "\n");
            inputField.setText("");

            if (content.equalsIgnoreCase("bye")) {
                textArea.append("[Bạn đã ngắt kết nối. Chương trình kết thúc]\n");
                socket.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

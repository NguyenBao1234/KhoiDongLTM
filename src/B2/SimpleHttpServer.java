package B2;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException
    {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new FormHandler());
        server.createContext("/calculate", new CalculatorHandler());
// Khởi động server
        server.setExecutor(null);
        server.start();

        System.out.println("Server dang chay tai:");
        System.out.println("- Localhost: http://localhost:8000");
        System.out.println("- LAN: http://" + getLocalIP() + ":8000");
        System.out.println("\nVi du truy cap:");
        System.out.println("http://localhost:8000/calculate?a=10&b=20");

    }

    private static String getLocalIP()
    {
        try {
            return java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "unknown";
        }
    }

    static class FormHandler implements HttpHandler
    {
        @Override
        public void handle(HttpExchange exchange) throws IOException
        {
            String response = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Demo HTTP Server Calculator</title>
                    <style>
                        body {\s
                            font-family: Arial;\s
                            max-width: 600px;\s
                            margin: 50px auto;
                            padding: 20px;
                        }
                        .form-group { margin: 15px 0; }
                        input {\s
                            padding: 8px;\s
                            width: 100px;\s
                            font-size: 16px;
                        }
                        button {\s
                            padding: 10px 20px;\s
                            background: #007bff;\s
                            color: white;\s
                            border: none;
                            cursor: pointer;
                            font-size: 16px;
                        }
                        button:hover { background: #0056b3; }
                        .info {\s
                            background: #f8f9fa;\s
                            padding: 15px;\s
                            border-radius: 5px;
                            margin: 20px 0;
                        }
                    </style>
                </head>
                <body>
                    <h1>Java HTTP Server</h1>
                    
                    <div class="info">
                        <h3>Nhập 2 số để tính toán:</h3>
                        <form action="/calculate" method="GET">
                            <div class="form-group">
                                <label>Số a: </label>
                                <input type="number" name="a" required>
                            </div>
                            <div class="form-group">
                                <label>Số b: </label>
                                <input type="number" name="b" required>
                            </div>
                            <button type="submit">Tính toán</button>
                        </form>
                    </div>
                    
                    <div class="info">
                        <h3>Hoặc truy cập trực tiếp:</h3>
                        <p>
                            <a href="/calculate?a=10&b=20">
                                /calculate?a=10&b=20
                            </a>
                        </p>
                    </div>
                </body>
                </html>
                """;

            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.getBytes().length);

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class CalculatorHandler implements HttpHandler
    {
        @Override
        public void handle(HttpExchange exchange) throws IOException
        {
            URI uri = exchange.getRequestURI();
            Map<String, String> params = parseQuery(uri.getQuery());

            String response;

            try {
                // Lấy tham số a và b
                double a = Double.parseDouble(params.get("a"));
                double b = Double.parseDouble(params.get("b"));

                // Tính toán
                double tong = a + b;
                double hieu = a - b;
                double tich = a * b;
                double thuong = (b != 0) ? a / b : 0;

                // Tạo response HTML
                response = String.format("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta charset="UTF-8">
                        <title>Kết quả tính toán</title>
                        <style>
                            body { 
                                font-family: Arial; 
                                max-width: 600px; 
                                margin: 50px auto;
                                padding: 20px;
                            }
                            .result { 
                                background: #d4edda; 
                                padding: 20px; 
                                border-radius: 5px;
                                margin: 20px 0;
                            }
                            .param { 
                                background: #f8f9fa; 
                                padding: 15px; 
                                border-radius: 5px;
                                margin: 10px 0;
                            }
                            a { 
                                display: inline-block;
                                margin-top: 20px;
                                padding: 10px 20px;
                                background: #007bff;
                                color: white;
                                text-decoration: none;
                                border-radius: 5px;
                            }
                            a:hover { background: #0056b3; }
                        </style>
                    </head>
                    <body>
                        <h1>📊 Kết quả tính toán</h1>
                        
                        <div class="param">
                            <h3>Tham số nhận được:</h3>
                            <p><strong>a =</strong> %.2f</p>
                            <p><strong>b =</strong> %.2f</p>
                        </div>
                        
                        <div class="result">
                            <h3>Kết quả:</h3>
                            <p><strong>Tổng:</strong> %.2f + %.2f = %.2f</p>
                            <p><strong>Hiệu:</strong> %.2f - %.2f = %.2f</p>
                            <p><strong>Tích:</strong> %.2f × %.2f = %.2f</p>
                            <p><strong>Thương:</strong> %.2f ÷ %.2f = %.2f</p>
                        </div>
                        
                        <a href="/">← Quay lại trang chủ</a>
                    </body>
                    </html>
                    """,
                        a, b,
                        a, b, tong,
                        a, b, hieu,
                        a, b, tich,
                        a, b, thuong
                );

            } catch (Exception e) {
                response = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta charset="UTF-8">
                        <title>Lỗi</title>
                        <style>
                            body { 
                                font-family: Arial; 
                                max-width: 600px; 
                                margin: 50px auto;
                                padding: 20px;
                            }
                            .error { 
                                background: #f8d7da; 
                                padding: 20px; 
                                border-radius: 5px;
                            }
                            a { 
                                display: inline-block;
                                margin-top: 20px;
                                padding: 10px 20px;
                                background: #007bff;
                                color: white;
                                text-decoration: none;
                                border-radius: 5px;
                            }
                        </style>
                    </head>
                    <body>
                        <h1>Lỗi</h1>
                        <div class="error">
                            <p>Vui lòng nhập đúng định dạng: /calculate?a=10&b=20</p>
                        </div>
                        <a href="/">← Quay lại trang chủ</a>
                    </body>
                    </html>
                    """;
            }

            // Gửi response
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.getBytes().length);

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private Map<String, String> parseQuery(String query) {
            Map<String, String> result = new HashMap<>();
            if (query == null) return result;

            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                if (pair.length > 1) {
                    result.put(pair[0], pair[1]);
                }
            }
            return result;
        }
    }
}

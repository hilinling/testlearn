package tcp.p1;

import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

/**
 * Created by ling on 17/7/13.
 */
public class TCPp1testApplication {


    public static void main(String[] args){

        WebSocketClient webSocketClient = new StandardWebSocketClient();

//        String url="ws://localhost:8200/cupro";
        String url="ws://123.56.249.175:8200/cupro";
        WebSocketHandler webSocketHandler = new WebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                byte[] bytes = init();
                for(int i=0;i<10;i++){
                    session.sendMessage(new BinaryMessage(bytes));
                }
//                session.sendMessage(new TextMessage("123\n"));
            }

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                System.out.println("Coming data >>>> "+message.getPayload());
            }

            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                System.out.println("afterConnectionClosed data >>>> ");
            }

            @Override
            public boolean supportsPartialMessages() {
                return false;
            }
        };
        WebSocketConnectionManager webSocketConnectionManager = new WebSocketConnectionManager(webSocketClient,webSocketHandler,url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("origin","T1-01234567890123456789");
        webSocketConnectionManager.setHeaders(headers);
        try {
            while (true){

                webSocketConnectionManager.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        byte[] bytes = calStringByte("hello");
//        pringByte(bytes);
//        printString(bytes);




//        SpringApplication.run(Application.class,args);
    }

    private static byte[] init(){
        String msg1 = "93 01 04 00 00 00 21 11 3e 64 64 64 64 21 11 11 11 21 11 11 11 21 11 11 11";

        String[] list = msg1.split(" ");

        byte[] result = new byte[list.length];
        for(int i=0;i<list.length;i++){
            String a = list[i];
            result[i] = Integer.valueOf(a,16).byteValue();
        }
        StringBuilder sb = new StringBuilder();
        for (byte airB : result) {
            sb.append(String.format("%02X ", airB));
        }
        System.out.println("the send message is "+sb);
        return result;

    }
    private static String calverify(byte[] bytes){
        int sum = 0;
        StringBuilder sb = new StringBuilder();
        for(byte b:bytes){
            sum +=b &0xFF;
            sb.append(String.format("%02X ", b));
        }
        int i = 0xFF + 1 - (sum & 0xFF);
        return Integer.toHexString(i);
    }

    private static byte[] calStringByte(String s){
        byte[] result = new byte[64];
        byte[] sbt = s.getBytes();
        for(int i = 0 ;i<result.length;i++){
            if(i<sbt.length){
                result[i] = sbt[i];
            }else {
                result[i] =0x00;
            }
        }
        return result;

    }

    private static void pringByte(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte airB : bytes) {
            sb.append(String.format("%02X ", airB));
        }
        System.out.println("byte[] ==="+sb);
    }
    private static void printString(byte[] bytes){
        String mesg = new String(bytes).trim();
        System.out.println("msg ==="+mesg);
    }
}


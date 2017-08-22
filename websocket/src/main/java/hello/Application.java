package hello;

import javafx.beans.binding.StringBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ling on 17/7/13.
 */
public class Application {


    public static void main(String[] args){

        WebSocketClient webSocketClient = new StandardWebSocketClient();

        String url="ws://localhost:9990/hwbsupro";
        WebSocketHandler webSocketHandler = new WebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                session.sendMessage(new BinaryMessage(init()));
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
        List<String> deviceList = new LinkedList<>();
        deviceList.add("N2-12345678901234567890");
        headers.put("deviceId",deviceList);
        List<String> versionList = new LinkedList<>();
        versionList.add("N2-12345678901234567890");
        headers.put("version",versionList);
        List<String> protocol = new LinkedList<>();
        protocol.add("tcp");
        headers.put("protocol",protocol);
        List<String> line = new LinkedList<>();
        line.add("1");
        headers.put("line",line);
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
        String msg1 = "01 00 01 04";//心跳包
        String msg2 = "10 00 05 20 03 10 27 00 ";//数据包  pm2d5 = 100.00ug/m3 (10000D = 2710H)
        String msg3 = "10 00 0a 20 03 10 27 00 24 03 e0 2e 00";//数据包  pm2d5 = 100.00ug/m3 (10000D = 2710H),pm10 = 120.00(ug/m3)(12000 =2ee0H )
        //数据包  pm2d5 = 100.00ug/m3 (10000D = 2710H),pm10 = 120.00(ug/m3)(12000 =2ee0H ) fan1 = 2000rpm(2000=7d0H)
        String msg4 = "10 00 0e 20 03 10 27 00 24 03 e0 2e 00 60 02 d0 07";
        //请求包 指令0x20,pairing:{data:1,delayed:15000,by:"user"}(15000=3a98H)
        String requestMes1 = "20 00 08 30 00 98 3a 00 00 01 01";
        //请求包 指令0xC0,wifi:{data:"bluesky",delayed:15000,by:"user"}(15000=3a98H)}
        String requestMes2 = "20 00 47 c0 00 98 3a 00 00 40 62 6C 75 73 65 6B 79 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";

        //请求包 指令0xC0,wifi:{data:"bluesky",delayed:15000,by:"user"}(15000=3a98H),0xC1password:{data:"hello",delayed:15000,by:"user"}
        String requestMes3 = "20 00 8e c0 00 98 3a 00 00 40 62 6C 75 73 65 6B 79 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00"
                +" c1 00 98 3a 00 00 40 68 65 6C 6C 6F 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
        //请求包 指令0xD0,
        String requestTime ="20 00 07 D0 00 98 3a 00 00 00";

        //指令响应包
        String line = "30 00 02 00 02";



        String[] list = msg1.split(" ");

        byte[] result = new byte[list.length+1];
        for(int i=0;i<list.length;i++){
            String a = list[i];
            result[i] = Integer.valueOf(a,16).byteValue();
        }
        result[list.length] =Integer.valueOf(calverify(result),16).byteValue() ;
        int sum = 0;
        StringBuilder sb = new StringBuilder();
        for (byte airB : result) {
            sum += airB & 0xFF;
            sb.append(String.format("%02X ", airB));
        }
        System.out.println("sum==="+(sum&0xFF));
        System.out.println("sum==="+((sum&0xFF)==0x00));
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

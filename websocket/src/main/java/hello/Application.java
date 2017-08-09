package hello;

import javafx.beans.binding.StringBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * Created by ling on 17/7/13.
 */
public class Application {


    public static void main(String[] args){

        WebSocketClient webSocketClient = new StandardWebSocketClient();

        String url="ws://localhost:9900/testcu";
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
        webSocketConnectionManager.setOrigin("N2-12345678901234567890");
        webSocketConnectionManager.start();
//        init();




//        SpringApplication.run(Application.class,args);
    }

    private static byte[] init(){
        String msg = "01 00 01 04 FA";
        String[] list = msg.split(" ");

        byte[] result = new byte[list.length];
        for(int i=0;i<list.length;i++){
            String a = list[i];
            result[i] = Integer.valueOf(a,16).byteValue();

        }
        int sum = 0;
        StringBuilder sb = new StringBuilder();
        for (byte airB : result) {
            sum += airB & 0xFF;
            sb.append(String.format("%02X ", airB));
        }
        System.out.println("sb==="+(sum&0xFF));
        System.out.println("sb==="+((sum&0xFF)==0x00));
        return result;

    }
}

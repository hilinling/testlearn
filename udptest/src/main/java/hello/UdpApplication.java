package hello;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ling on 17/8/16.
 */
@SpringBootApplication
public class UdpApplication {
    public static void main(String... args) throws Exception{
        SpringApplication.run(UdpApplication.class,args);

    }

    @Bean
    CommandLineRunner init(){
        return args -> {
            UdpEntity entity = new UdpEntity();
            entity.setDeviceId("N2-1234567890");
            entity.setProtocol("udp");
            HashMap<String,RequestSpy> map = new HashMap<>();
            map.put("ssid",new RequestSpy("hwlt",15000,"auto"));
            entity.setRequest(map);
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress inetAddress = InetAddress.getLocalHost();
            byte[] sendData = new byte[1024];
            byte[] receivedData = new byte[1024];

            ObjectMapper objectMapper = new ObjectMapper();

            String sentence = objectMapper.writeValueAsString(entity);
            sendData = sentence.getBytes();

            DatagramPacket packet = new DatagramPacket(sendData,sendData.length,inetAddress,59830);
            clientSocket.send(packet);

            DatagramPacket receivedPacket = new DatagramPacket(receivedData,receivedData.length);
            clientSocket.receive(receivedPacket);
            String modifiedSentence = new String(receivedPacket.getData()).trim();
            System.out.println("From server: "+modifiedSentence);
            clientSocket.close();
        };
    }
}

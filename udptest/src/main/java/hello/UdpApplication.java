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
            entity.setDeviceId("M700-3C28C1BEF9E730003457");
            entity.setProtocol("2.0");
            entity.setVersion("M700 1.0.1");
            HashMap<String,RequestSpy> requestMap = new HashMap<>();
            RequestSpy time = new RequestSpy();
            time.setData(3);
            time.setBy("auto");
            requestMap.put("fanLevel",time);

            HashMap<String,Double> data = new HashMap<>();
            data.put("pm2d5",20000.00);


            entity.setRequests(requestMap);
            entity.setData(data);
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress remoteAddress = InetAddress.getByName("udp.support.hw99lt.com");


            InetAddress inetAddress = InetAddress.getLocalHost();
            byte[] sendData = new byte[1024];
            byte[] receivedData = new byte[1024];

            ObjectMapper objectMapper = new ObjectMapper();

            String sentence = objectMapper.writeValueAsString(entity);
            sendData = sentence.getBytes();

            DatagramPacket packet = new DatagramPacket(sendData,sendData.length,remoteAddress,59830);
            clientSocket.send(packet);

            DatagramPacket receivedPacket = new DatagramPacket(receivedData,receivedData.length);
            clientSocket.receive(receivedPacket);
            String modifiedSentence = new String(receivedPacket.getData()).trim();
            System.out.println("From server: "+modifiedSentence);
            clientSocket.close();
        };//123.56.168.98
    }
}

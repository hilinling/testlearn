package hello;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


/**
 * Created by ling on 17/7/25.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args){


        SpringApplication.run(Application.class,args);
    }

    @Bean
    public CommandLineRunner test(ObjectMapper objectMapper){
        return args -> {
            Record record = new Record();
            record.setVer("N2 2.0.1");
//            record.setDevId("N2-A2F810661D0FB658D9B10874F4089E42");
            String msg = objectMapper.writeValueAsString(record);

            System.out.println(String.format("======msg: %s",msg));

            byte[] buffer = msg.getBytes();
            InetAddress address = InetAddress.getByName("123.57.183.103");
            DatagramSocket datagramSocket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buffer,buffer.length,address,59730);
            datagramSocket.send(packet);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            msg = "";
            while((msg=reader.readLine()).equals("")){
                //接收数据
                DatagramPacket inputPacket = new DatagramPacket(new byte[512], 512);
                datagramSocket.receive(inputPacket);
                System.out.println(new String(inputPacket.getData(), 0 , inputPacket.getLength()));
                datagramSocket.close();
            }

        };
    }


}

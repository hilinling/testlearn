package hello.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by ling on 17/7/20.
 */
@RestController
public class ResourceController {

    @RequestMapping("/")
    public Message home(){
        return new Message("Hello,World");
    }

    class Message{
        private String id = UUID.randomUUID().toString();
        private String content;
        public Message(String content){
            this.content = content;
        }

        public Message() {
        }

        public String getId() {
            return id;
        }

        public String getContent() {
            return content;
        }
    }
}

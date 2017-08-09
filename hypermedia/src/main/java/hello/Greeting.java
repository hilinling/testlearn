package hello;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by ling on 17/7/12.
 */
public class Greeting extends ResourceSupport {


    private final String content;

    @JsonCreator
    public Greeting(@JsonProperty("content") String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

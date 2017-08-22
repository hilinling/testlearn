package hello;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by ling on 17/8/10.
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestSpy {
    private Object data;
    private int delayed;
    private String by;

    public RequestSpy() {
    }
}

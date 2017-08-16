package hello;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by ling on 17/8/10.
 */
@Data
@AllArgsConstructor
public class RequestSpy {
    private String data;
    private int delayed;
    private String by;
}

package hello;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ling on 17/8/16.
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UdpEntity {
    String deviceId;
    String version;
    String protocol;
    Integer line;
    HashMap<String,RequestSpy> requests;
    HashMap<String,Double> data;
}

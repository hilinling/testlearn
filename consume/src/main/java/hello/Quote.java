package hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ling on 17/7/10.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    private String type;
    private Value value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Quote{"+
                "type='"+type+"', value="+value+"}";
    }
}


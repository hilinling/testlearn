package hello;

/**
 * Created by ling on 17/8/17.
 */
public enum Type {

    ssid("ssid"),
    password("password"),
    started("started"),
    pairing("pairing"),
    power("power"),
    mode("mode"),
    LEDSwitch("LEDSwitch"),
    fanspeed("fan"),
    time("time"),
    weather("weather");

    private String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


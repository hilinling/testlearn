package hello;

/**
 * Created by ling on 17/7/25.
 */
public class Inventor {
    private String name;

    public Inventor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "inventor 【"+name+"】";
    }
}

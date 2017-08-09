package hello;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ling on 17/8/1.
 */
public class Dog {
    private List<String> dogs = new ArrayList<>();

    public List<String> getDogs() {
        return dogs;
    }

    public void setDogs(List<String> dogs) {
        this.dogs = dogs;
    }

    public Dog() {
    }
}

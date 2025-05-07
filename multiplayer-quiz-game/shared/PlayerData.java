package shared;

import java.io.Serializable;

public class PlayerData implements Serializable {
    public String name;
    public int score;

    public PlayerData(String name) {
        this.name = name;
        this.score = 0;
    }
}

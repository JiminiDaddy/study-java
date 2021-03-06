package ch05;

import lombok.Getter;

@Getter
public class Trader {
    private final String name;
    private final String city;
    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Trader:" + this.name + " in "  + this.city;
    }
}

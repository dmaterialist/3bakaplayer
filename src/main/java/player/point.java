package player;

import java.awt.*;
import java.util.*;

public class point {
    private final Integer x;
    private final Integer y;

    private enum colors {
        Black,
        Green,
        White
    }

    public point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double Distance(Point other) {
        Map<Point,String> map=new HashMap<>();
        return (Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - y, 2)));
    }

    public static Object maxSet(Set set) {
        Object obj = Collections.max(set);
        return obj;
    }

    public boolean equals(point other) {
        switch (this.x) {
            case 1:
        }
        String[] s=new String[] {"ddfd", "fdfdf"};
        for (String t: s) {
        }
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < set.size(); i++) {
            Collections.max(set);
        }
        if (this == other) return true;
        if (this == null || other.getClass() != this.getClass()) return false;
        return (this.x.equals(other.x) && this.y.equals(other.y));
    }

    @Override
    public String toString() {
        return this.x.toString() + this.y.toString();
    }

}

import java.util.HashMap;
import java.util.Map;

public class CardRankAndSuit {
    private static final Map<String, Integer> rank = new HashMap<>() {{
        put("2", 1);
        put("3", 2);
        put("4", 3);
        put("5", 4);
        put("6", 5);
        put("7", 6);
        put("8", 7);
        put("9", 8);
        put("T", 9);
        put("J", 10);
        put("Q", 11);
        put("K", 12);
        put("A", 13);
    }};

    private static final Map<String, Integer> suit = new HashMap<>() {{
       put("S", 1);
       put("H", 2);
       put("D", 3);
       put("C", 4);
    }};

    public static int getRank(String card) {
        return rank.get(card.substring(0, 1));
    }

    public static int getSuit(String card) {
        return suit.get(card.substring(1));
    }
}

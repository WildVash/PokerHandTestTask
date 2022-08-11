import java.util.Objects;

public class Card {

    private final int rank;
    private final int suit;

    public Card(String card) {
        this.rank = CardRankAndSuit.getRank(card);
        this.suit = CardRankAndSuit.getSuit(card);
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank);
    }
}

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PokerHand implements Comparable<PokerHand> {

    private final List<Card> cards;
    private HandRank handRank;

    public PokerHand(String hand) {
        if (hand.length() == 14 && hand.matches("(?:[2-9TJQKA][SHDC]\\s?){5}")) {
            cards = getCardsList(hand);
        } else {
            throw new RuntimeException("Expected string format: \"2C 3C AC 4C 5C\"");
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public HandRank getHandRank() {
        return handRank;
    }

    public void setHandRank(HandRank handRank) {
        this.handRank = handRank;
    }

    @Override
    public int compareTo(PokerHand hand) {
        int comparedValue = Integer.compare(hand.getHandRank().getValue(), handRank.getValue());

        if (comparedValue == 0) {
           return compareByHighCard(hand);
        }
        return comparedValue;
    }

    private int compareByHighCard(PokerHand hand) {
        List<Card> handCards = hand.getCards();

        moveWinnerCombinationToTheEndOfListIfNeeded(handCards);

        for (int i = cards.size() - 1; i >= 0; i--) {
            Card card = cards.get(i);
            Card handCard = handCards.get(i);
            int cardRank = card.getRank();
            int handCardRank = handCard.getRank();

            if (cardRank != handCardRank) {
                return Integer.compare(handCardRank, cardRank);
            }
        }
        return 0;
    }

    private void moveWinnerCombinationToTheEndOfListIfNeeded(List<Card> handCards) {
        if (HandRank.PAIR.equals(handRank) || HandRank.TWO_PAIRS.equals(handRank) || HandRank.THREE_OF_A_KIND.equals(handRank)
                || HandRank.FOUR_OF_A_KIND.equals(handRank)) {
            List<Card> winnerCombination = getWinnerCombination(cards);
            List<Card> secondWinnerCombination = getWinnerCombination(handCards);

            cards.removeAll(winnerCombination);
            cards.addAll(winnerCombination);

            handCards.removeAll(secondWinnerCombination);
            handCards.addAll(secondWinnerCombination);
        }
    }

    private List<Card> getWinnerCombination(List<Card> cards) {
        return cards.stream()
                .filter(card -> Collections.frequency(cards, card) >= 2)
                .collect(Collectors.toList());
    }

    private List<Card> getCardsList(String hand) {
        return Arrays.stream(hand.split(" "))
                .map(Card::new)
                .sorted(Comparator.comparingInt(Card::getRank))
                .collect(Collectors.toList());
    }
}

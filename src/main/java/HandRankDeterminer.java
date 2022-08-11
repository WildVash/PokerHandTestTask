import java.util.Collections;
import java.util.List;

public class HandRankDeterminer {
    public static void determine(PokerHand hand) {
            hand.setHandRank(getHandRank(hand.getCards()));
    }

    private static HandRank getHandRank(List<Card> cards) {
         if (isRoyalFlush(cards)) {
            return HandRank.ROYAL_FLUSH;
        } else if (isStraightFlush(cards)) {
            return HandRank.STRAIGHT_FLUSH;
        } else if (isFourOfAKind(cards)) {
            return HandRank.FOUR_OF_A_KIND;
        } else if (isFullHouse(cards)) {
            return HandRank.FULL_HOUSE;
        } else if (isFlush(cards)) {
            return HandRank.FLUSH;
        } else if (isStraight(cards)) {
            return HandRank.STRAIGHT;
        } else if (isThreeOfAKind(cards)) {
            return HandRank.THREE_OF_A_KIND;
        } else if (isTwoPairs(cards)) {
            return HandRank.TWO_PAIRS;
        } else if (isPair(cards)) {
            return HandRank.PAIR;
        } else {
            return HandRank.HIGH_CARD;
        }
    }

    private static boolean isRoyalFlush(List<Card> cards) {
        for (int i = 0, j = 9; i < cards.size(); i++, j++) {
            if (cards.get(i).getRank() != j) {
                return false;
            }
        }
        return isFlush(cards);
    }

    private static boolean isStraightFlush(List<Card> cards) {
        return isStraight(cards) && isFlush(cards);
    }

    private static boolean isFourOfAKind(List<Card> cards) {

        return findDupes(cards, 4) == 4;
    }

    private static boolean isFullHouse(List<Card> cards) {
        return isPair(cards) && isThreeOfAKind(cards);
    }

    private static boolean isFlush(List<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getSuit() != cards.get(i + 1).getSuit()) {
                return false;
            }
        }
        return true;
    }

    private static boolean isStraight(List<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getRank() + 1 != cards.get(i + 1).getRank()) {
                return false;
            }
        }
        return true;
    }

    private static boolean isThreeOfAKind(List<Card> cards) {
        return findDupes(cards, 3) == 3;
    }

    private static boolean isTwoPairs(List<Card> cards) {
        long count = cards.stream()
                .filter(card -> Collections.frequency(cards, card) == 2)
                .distinct()
                .count();
        return count == 2;
    }

    private static boolean isPair(List<Card> cards) {
        return findDupes(cards, 2) == 2;
    }

    private static long findDupes(List<Card> cards, int dupesQty) {
        return cards.stream()
                .filter(card -> Collections.frequency(cards, card) == dupesQty)
                .count();
    }
}

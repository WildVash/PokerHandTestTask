import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PokerHandTest {

    private static Stream<Arguments> provideStringsForComparing() {
        return Stream.of(
                Arguments.of("8H TH 9H JH QH", "2S AD 2C AD AH", -1),
                Arguments.of("5C AS 3C AH 5D", "QD AS QH QS QC", 1),
                Arguments.of("5C 7S 3C AH 2D", "5S 7H 3D AD 2C", 0),
                Arguments.of("TC 4S 7H TH QD", "TD KH TS 2S 6H", 1),
                Arguments.of("2C 5S 3C 6H 4D", "4S 6D 3H 5C 7C", 1),
                Arguments.of("8H TH 9H JH QH", "8C TC 9C JC QC", 0),
                Arguments.of("2S AD 2C AD AH", "QS KD QC KD KH", -1),
                Arguments.of("AC AS 3C AH 2D", "5C AS 3C AH 5D", -1),
                Arguments.of("AH TH KH JH QH", "8H TH 9H JH QH", -1),
                Arguments.of("5C AS 3C AH 2D", "5C 7S 3C AH 2D", -1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideStringsForComparing")
    void handComparingTest(String firsHand, String secondHand, int expected) {
        PokerHand first = new PokerHand(firsHand);
        PokerHand second = new PokerHand(secondHand);

        HandRankDeterminer.determine(first);
        HandRankDeterminer.determine(second);

        assertEquals(expected, first.compareTo(second));
    }

    @Test
    void collectionSortingTest() {
        List<PokerHand> hands = new ArrayList<>();
        hands.add(new PokerHand("2S AD 2C AD AH"));
        hands.add(new PokerHand("AC AS 3C AH 2D"));
        hands.add(new PokerHand("5C 7S 3C AH 2D"));
        hands.add(new PokerHand("8H TH 9H JH QH"));
        hands.add(new PokerHand("2C AC 4C KC JC"));
        hands.add(new PokerHand("AH TH KH JH QH"));
        hands.add(new PokerHand("5C AS 3C AH 2D"));
        hands.add(new PokerHand("QD AS QH QS QC"));
        hands.add(new PokerHand("5C AS 3C AH 5D"));
        hands.add(new PokerHand("2C 5S 3C 6H 4D"));

        List<HandRank> actual = hands.stream()
                .peek(HandRankDeterminer::determine)
                .sorted()
                .map(PokerHand::getHandRank)
                .collect(Collectors.toList());

        List<HandRank> expected = Arrays.asList(HandRank.ROYAL_FLUSH, HandRank.STRAIGHT_FLUSH, HandRank.FOUR_OF_A_KIND, HandRank.FULL_HOUSE,
                HandRank.FLUSH, HandRank.STRAIGHT, HandRank.THREE_OF_A_KIND, HandRank.TWO_PAIRS, HandRank.PAIR, HandRank.HIGH_CARD);

        assertEquals(expected, actual);
    }
}
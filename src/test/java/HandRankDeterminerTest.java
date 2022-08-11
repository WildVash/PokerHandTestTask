import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class HandRankDeterminerTest {

    private static Stream<Arguments> provideStringsForDetermine() {
        return Stream.of(
                Arguments.of("AH TH KH JH QH", HandRank.ROYAL_FLUSH),
                Arguments.of("8H TH 9H JH QH", HandRank.STRAIGHT_FLUSH),
                Arguments.of("QD AS QH QS QC", HandRank.FOUR_OF_A_KIND),
                Arguments.of("2S AD 2C AD AH", HandRank.FULL_HOUSE),
                Arguments.of("2C AC 4C KC JC", HandRank.FLUSH),
                Arguments.of("2C 5S 3C 6H 4D", HandRank.STRAIGHT),
                Arguments.of("AC AS 3C AH 2D", HandRank.THREE_OF_A_KIND),
                Arguments.of("5C AS 3C AH 5D", HandRank.TWO_PAIRS),
                Arguments.of("5C AS 3C AH 2D", HandRank.PAIR),
                Arguments.of("5C 7S 3C AH 2D", HandRank.HIGH_CARD)
        );
    }

    @ParameterizedTest
    @MethodSource("provideStringsForDetermine")
    void combinationsDeterminerTest(String hand, HandRank expected) {
        PokerHand pokerHand = new PokerHand(hand);

        HandRankDeterminer.determine(pokerHand);

        Assertions.assertEquals(expected, pokerHand.getHandRank());
    }
}
package com.stardy.poker_defense.unit.util;

import com.stardy.poker_defense.unit.entity.CardSuit;
import com.stardy.poker_defense.unit.entity.OwnedUnit;

import java.util.*;
import java.util.stream.Collectors;

public class PokerHandEvaluator {

    public enum PokerHand {
        HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH, ROYAL_FLUSH
    }

    public static PokerHand evaluateHand(List<OwnedUnit> units) {
        if (units == null || units.size() < 2 || units.size() > 5) {
            return PokerHand.HIGH_CARD;
        }

        List<Integer> numbers = units.stream()
                .map(unit -> parseCardNumber(unit.getNumber()))
                .sorted()
                .collect(Collectors.toList());
        List<CardSuit> suits = units.stream()
                .map(OwnedUnit::getSuit)
                .collect(Collectors.toList());

        boolean isFlush = isFlush(suits);
        boolean isStraight = isStraight(numbers);

        if (isStraight && isFlush) {
            if (isRoyal(numbers)) {
                return PokerHand.ROYAL_FLUSH;
            }
            return PokerHand.STRAIGHT_FLUSH;
        }

        Map<Integer, Long> counts = numbers.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        if (isFourOfAKind(counts)) return PokerHand.FOUR_OF_A_KIND;
        if (isFullHouse(counts)) return PokerHand.FULL_HOUSE;
        if (isFlush) return PokerHand.FLUSH;
        if (isStraight) return PokerHand.STRAIGHT;
        if (isThreeOfAKind(counts)) return PokerHand.THREE_OF_A_KIND;
        if (isTwoPair(counts)) return PokerHand.TWO_PAIR;
        if (isPair(counts)) return PokerHand.PAIR;

        return PokerHand.HIGH_CARD;
    }

    private static boolean isFlush(List<CardSuit> suits) {
        if (suits.size() < 5) return false;
        return new HashSet<>(suits).size() == 1;
    }

    private static boolean isStraight(List<Integer> numbers) {
        if (numbers.size() < 5) return false;

        // 중복된 숫자가 있으면 스트레이트가 아님
        if (new HashSet<>(numbers).size() < 5) {
            return false;
        }

        // 일반적인 스트레이트 (4, 5, 6, 7, 8)
        boolean isNormalStraight = (numbers.get(4) - numbers.get(0) == 4);
        if (isNormalStraight) return true;

        // 'A, 10, J, Q, K' (마운틴) 스트레이트 확인
        boolean isMountain = numbers.equals(Arrays.asList(1, 10, 11, 12, 13));
        return isMountain;
    }

    // 로얄 플러시를 위한 스트레이트인지 확인 (A, K, Q, J, 10)
    private static boolean isRoyal(List<Integer> numbers) {
        return numbers.size() == 5 && numbers.equals(Arrays.asList(1, 10, 11, 12, 13));
    }

    private static boolean isFourOfAKind(Map<Integer, Long> counts) {
        return counts.containsValue(4L);
    }

    private static boolean isFullHouse(Map<Integer, Long> counts) {
        return counts.containsValue(3L) && counts.containsValue(2L);
    }

    private static boolean isThreeOfAKind(Map<Integer, Long> counts) {
        return counts.containsValue(3L);
    }

    private static boolean isTwoPair(Map<Integer, Long> counts) {
        return counts.values().stream().filter(count -> count == 2L).count() == 2;
    }

    private static boolean isPair(Map<Integer, Long> counts) {
        return counts.containsValue(2L);
    }

    public static Integer parseCardNumber(String numberStr) {
        try {
            return Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            return switch (numberStr.toUpperCase()) {
                case "J" -> 11;
                case "Q" -> 12;
                case "K" -> 13;
                case "A" -> 1;
                default -> 0;
            };
        }
    }

    public static double getEnhancementMultiplier(PokerHand hand) {
        switch (hand) {
            case PAIR: return 1.2;
            case TWO_PAIR: return 1.4;
            case THREE_OF_A_KIND: return 1.6;
            case STRAIGHT: return 1.8;
            case FLUSH: return 2.0;
            case FULL_HOUSE: return 2.5;
            case FOUR_OF_A_KIND: return 3.0;
            case STRAIGHT_FLUSH: return 4.0;
            case ROYAL_FLUSH: return 5.0;
            case HIGH_CARD:
            default: return 1.0;
        }
    }

    public static double getDisassembleGoldMultiplier(PokerHand hand) {
        switch (hand) {
            case PAIR: return 0.6;
            case TWO_PAIR: return 0.7;
            default: return 0.5;
        }
    }
}

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
            if (numbers.size() == 5 && numbers.get(0) == 1 && numbers.get(1) == 10 && numbers.get(2) == 11 && numbers.get(3) == 12 && numbers.get(4) == 13) {
                return PokerHand.ROYAL_FLUSH;
            }
            return PokerHand.STRAIGHT_FLUSH;
        }

        Map<Integer, Long> counts = numbers.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        long maxCount = 0;
        long pairs = 0;
        long threes = 0;
        long fours = 0;

        for (Long count : counts.values()) {
            if (count > maxCount) {
                maxCount = count;
            }
            if (count == 2) pairs++;
            if (count == 3) threes++;
            if (count == 4) fours++;
        }

        if (fours > 0) return PokerHand.FOUR_OF_A_KIND;
        if (threes > 0 && pairs > 0) return PokerHand.FULL_HOUSE;
        if (isFlush) return PokerHand.FLUSH;
        if (isStraight) return PokerHand.STRAIGHT;
        if (threes > 0) return PokerHand.THREE_OF_A_KIND;
        if (pairs == 2) return PokerHand.TWO_PAIR;
        if (pairs == 1) return PokerHand.PAIR;

        return PokerHand.HIGH_CARD;
    }

    private static boolean isFlush(List<CardSuit> suits) {
        if (suits.size() < 5) return false;
        CardSuit firstSuit = suits.get(0);
        return suits.stream().allMatch(s -> s == firstSuit);
    }

    private static boolean isStraight(List<Integer> numbers) {
        if (numbers.size() < 5) return false;
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        if (uniqueNumbers.size() != numbers.size()) {
            return false;
        }

        boolean normalStraight = true;
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i) + 1 != numbers.get(i + 1)) {
                normalStraight = false;
                break;
            }
        }
        if (normalStraight) return true;

        if (numbers.size() == 5 && numbers.containsAll(Arrays.asList(1, 2, 3, 4, 5))) {
            return true;
        }

        return false;
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
package com.prestonroesslet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Blackjack {
    List<Card> deck;
    List<Card> playerHand;
    List<Card> dealerHand;
    Scanner input;

    public Blackjack() {
        deck = new ArrayList<>();
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        input = new Scanner(System.in);

        buildDeck();
        dealCards();
        playerTurn();
        dealerTurn();
        determineWinner();

    }

    public void buildDeck() {

        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                deck.add(new Card(r, s));
            }
        }

        // Shuffle the deck
        Collections.shuffle(deck);
    }

    public void dealCards() {
        // Two cards to player
        playerHand.add(deck.remove(0));
        playerHand.add(deck.remove(0));

        // Two Cards to dealer
        dealerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));

        // Show dealers first card
        System.out.println("The dealer's first card is:");
        System.out.println(dealerHand.get(0));
        System.out.println("");

        // Show Player their cards
        System.out.println("Your current hand is:");
        printHand(playerHand);
        System.out.println("");
        System.out.println("Your hand total is " + getHandTotal(playerHand));
    }

    public void playerTurn() {


        while (true) {
            System.out.println("Would you like to hit or stand?");
            System.out.println("1. Hit");
            System.out.println("2. Stand");

            if (input.nextInt() == 1) {
                playerHand.add(deck.remove(0));
                System.out.println("Your hand is now:");
                printHand(playerHand);
                System.out.println("Hand total: " + getHandTotal(playerHand));
                System.out.println("");

                if (getHandTotal(playerHand) > 21) {
                    System.out.println("You busted!");
                    break;
                }
            } else {
                break;
            }
        }
    }

    public void dealerTurn() {
        System.out.println("It is now the dealer's turn");
        while (getHandTotal(dealerHand) < 17) {
            dealerHand.add(deck.remove(0));
        }
    }

    public void printHand(List<Card> hand) {
        for (Card c : hand) {
            System.out.println(c);
        }
    }

    public int getHandTotal(List<Card> hand) {
        int total = 0;
        int acesAsEleven = 0;

        for (Card c : hand) {
            total += c.getValue();

            if (c.getRank() == Rank.ACE) {
                acesAsEleven += 1;
            }
        }

        while (total > 21 && acesAsEleven > 0) {
            total -= 10;
            acesAsEleven -= 1;
        }

        return total;
    }

    public void determineWinner() {
        int playerTotal = getHandTotal(playerHand);
        int dealerTotal = getHandTotal(dealerHand);

        if (playerTotal > 21) {
            System.out.println("Sorry, you lose - you busted!");
        } else if (dealerTotal > 21) {
            System.out.println("Congrats, you won! The dealer busted");
        } else if (playerTotal > dealerTotal) {
            System.out.println("Congrats, you win! You had " + playerTotal + ". The dealer had " + dealerTotal);
        } else if (dealerTotal > playerTotal) {
            System.out.println("Sorry, you lose. The dealer had " + dealerTotal + ". You only had " + playerTotal);
        } else if (playerTotal == dealerTotal) {
            System.out.println("It's a tie!");
        }

        System.out.println("");
        System.out.println("Final Hands");
        System.out.println("Your hand - " + playerTotal);
        printHand(playerHand);
        System.out.println("");
        System.out.println("Dealer hand - " + dealerTotal);
        printHand(dealerHand);
    }
}

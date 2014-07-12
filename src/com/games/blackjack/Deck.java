package com.games.blackjack;

import java.util.Random;

public class Deck {
	private Card[] cards; // a deck has an array of cards indexed at zero
	private int currentIndex; // a marker to find number of cards already dealt.

	public Deck() {
		cards = new Card[52];
		initDeck();
	}

	public void initDeck() {
		int totalCards = 0;
		for (int i = 0; i <= Suit.values().length - 1; i++) {
			for (int j = 1; j <= 13; j++) {
				cards[totalCards] = new Card(j, Suit.values()[i]);
				totalCards++;
			}

		}
	}

	// only for testing with Driver program-remove later
	public Card[] getCards() {
		return cards;
	}
	public Card getCard(int i){
		return cards[i];
	}

	// get the index of card to be dealt
	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int index) {
		currentIndex = index;
	}

	// shuffle function,used to randomize the card positions in cards array
	public void shuffle() {
		Random rnd = new Random();
		for (int i = 0; i < cards.length; i++) {
			int position = rnd.nextInt(cards.length); // generate random no. for
														// a position
			Card temp = cards[i];
			cards[i] = cards[position];
			cards[position] = temp;
		}
		setCurrentIndex(cards.length-1);

	}

}

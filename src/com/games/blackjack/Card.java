package com.games.blackjack;

import java.util.HashMap;

public class Card {
	// properties of a card - Face Value, Suit

	private final int value; // restrict setting of values outside constructor
	private final Suit suit; // restrict setting of suit outside constructor
	private final HashMap<Integer, String> FaceCard;

	public Card(int value, Suit suit) {
		this.value = value;
		this.suit = suit;
		FaceCard = new HashMap<Integer, String>();
		FaceCard.put(1, "Ace");
		FaceCard.put(11, "Jack");
		FaceCard.put(12, "Queen");
		FaceCard.put(13, "King");
	}

	public int getValue() {
		return value;
	}

	public Suit getSuit() {
		return suit;
	}

	@Override
	public String toString() {
		if ((this.getValue() > 1) && (this.getValue() <= 10)) {
			return Integer.toString(this.getValue()) + " of  "
					+ this.getSuit().name();
		} else {
			return FaceCard.get(this.getValue()) + " of "
					+ this.getSuit().name();
		}

	}

}

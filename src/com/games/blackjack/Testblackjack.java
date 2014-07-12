package com.games.blackjack;

import static org.junit.Assert.*;
import org.junit.Test;

public class Testblackjack {

	@Test
	public void test() {
		Deck d1 = new Deck();
		Deck d2 = new Deck();
		d2.shuffle(); //test shuffle
		assertFalse("shuffled and unshuffled decks", d1.getCards().equals(d2.getCards()));
	}

}

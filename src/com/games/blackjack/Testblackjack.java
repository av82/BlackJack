package com.games.blackjack;

import static org.junit.Assert.*;
import org.junit.Test;

public class Testblackjack {

	public void printDeck(Deck d){ 
		int deckSize= d.getCards().length;
		for(int i=0;i<deckSize;i++){
				System.out.println(i+","+d.getCards()[i].getValue()+" of "+ d.getCards()[i].getSuit().name() );
			}
	}
	@Test
	public void test() {
		Deck d1 = new Deck();
		Deck d2 = new Deck();
		System.out.println("\n ------Deck d2 Before Shuffling-------");
		printDeck(d2);
		d2.shuffle(); //test shuffle
		System.out.println("\n ------Deck d2 after Shuffling--------");
		printDeck(d2);
		assertFalse("shuffled and unshuffled decks", d1.getCards().equals(d2.getCards()));
	}

}

package com.games.blackjack;

public class Driver {

	public void printDeck(Deck d){ 
		int deckSize= d.getCards().length;
		for(int i=0;i<deckSize;i++){
				System.out.println(i+","+d.getCards()[i].getValue()+" of "+ d.getCards()[i].getSuit().name() );
			}
	}
	/*public static void main(String[] args){
		Driver drvr = new Driver();
		Deck d = new Deck();
		System.out.println("Before Shuffle");
		drvr.printDeck(d);
		d.shuffle();
		System.out.println("After Shuffle");
		drvr.printDeck(d);
	}*/
}

package com.games.blackjack;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Test;

public class Testblackjack {
	Integer[] playerHandIndicesinDeck;
	

	public void printDeck(Deck d){ 
		int deckSize= d.getCards().length;
		for(int i=0;i<deckSize;i++){
				System.out.println(i+","+d.getCards()[i].getValue()+" of "+ d.getCards()[i].getSuit().name() );
			}
	}
	public void checkHand(Field deck,Field player,Field dealer,Field checkBlackJack,Boolean isBlackJack) throws IllegalArgumentException, IllegalAccessException{
		 Game g2 = new Game();
		 Deck d= new Deck();
		 deck.set(g2,d );
		 Player testplayer = new Player("TEST", 100);
		 Player testdealer = new Player("Dealer",0);
		 testplayer.setBet(10);
		
		 testplayer.getCards().add(playerHandIndicesinDeck[0]); // Ace
		 testdealer.getCards().add(playerHandIndicesinDeck[1]);// dearler KING
		 testplayer.getCards().add(playerHandIndicesinDeck[2]); //Jack
		 testdealer.getCards().add(playerHandIndicesinDeck[3]); //dealer Queen
		 
		 player.set(g2,testplayer);
		 dealer.set(g2,testdealer);
		 checkBlackJack.set(g2, isBlackJack);
		 g2.showCards(testplayer, true);
		 g2.showCards(testdealer, true);
		 g2.decideWinner();
	}
	public void setupforPush(){
		playerHandIndicesinDeck[0]= 0; //Ace for Player
	    playerHandIndicesinDeck[1]=12;//King for Dealer
	    playerHandIndicesinDeck[2]=13;//Ace for Player
	    playerHandIndicesinDeck[3]=25;//King for Dealer
	}
	public void setupforDealerWin(){
		playerHandIndicesinDeck[0]=0; //Ace for Player
		playerHandIndicesinDeck[1]=12;//King for Dealer
	    playerHandIndicesinDeck[2]=1;// 2 for Player
	    playerHandIndicesinDeck[3]=25;//King for Dealer
	}
	public void setupforPlayerWin(){
		playerHandIndicesinDeck[0]=0; //Ace for Player
	    playerHandIndicesinDeck[1]=1;//2 for Dealer
	    playerHandIndicesinDeck[2]=5;// 6 for Player
	    playerHandIndicesinDeck[3]=13;//Ace for Dealer
	}
	
	public void setupforPlayerWinWithBlackJack(){
		playerHandIndicesinDeck[0]=0; //Ace for Player
	    playerHandIndicesinDeck[1]=1;//2 for Dealer
	    playerHandIndicesinDeck[2]=25;// King for Player
	    playerHandIndicesinDeck[3]=13;//Ace for Dealer
	}
	
	public void setupforDealerWinWithBlackJack(){
		playerHandIndicesinDeck[0]=5; //6 for Player
	    playerHandIndicesinDeck[1]=9;//10 for Dealer
	    playerHandIndicesinDeck[2]=8;// 9 for Player
	    playerHandIndicesinDeck[3]=13;//Ace for Dealer
	}
	
	public void checkCase() throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException{ 
		 	Class<Game>  gReflect = Game.class;
		    Field deck = gReflect.getDeclaredField("deck");
		    Field player = gReflect.getDeclaredField("player");
		    Field dealer = gReflect.getDeclaredField("dealer");
		    Field checkBlackjack = gReflect.getDeclaredField("checkBlackjack");
		    
		    deck.setAccessible(true);
		    dealer.setAccessible(true);
		    player.setAccessible(true);
		    checkBlackjack.setAccessible(true);
		    
		    setupforPush(); //set cards to test draw/PUSH state
		    System.out.println("\nTesting for Draw scenario");
		    checkHand(deck, player, dealer,checkBlackjack,false);
		    
		    setupforDealerWin(); //set cards for Dealer win !!
		    System.out.println("\nTesting for Dealer Win");
		    checkHand(deck, player, dealer,checkBlackjack,false);
		    
		    setupforPlayerWin();
		    System.out.println("\nTesting for Player Win");
			checkHand(deck, player, dealer,checkBlackjack,false);
			
			
			setupforDealerWinWithBlackJack();
		    System.out.println("\nTesting for Dealer's Win with BlackJack !!");
		    checkHand(deck, player, dealer,checkBlackjack,true);
			
			setupforPlayerWinWithBlackJack();
		    System.out.println("\nTesting for Player's Win with BlackJack !!");
			checkHand(deck, player, dealer,checkBlackjack,true);
			
			
	}
	@Test
	public void test() throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
		Deck d1 = new Deck();
		Deck d2 = new Deck();
		System.out.println("\n ------Deck d2 Before Shuffling-------");
		printDeck(d2);
		d2.shuffle(); //test shuffle
		System.out.println("\n ------Deck d2 after Shuffling--------");
		printDeck(d2);
		playerHandIndicesinDeck=new Integer[4];
		checkCase();
		//check if shuffle randomizes the deck
		assertFalse("shuffled and unshuffled decks", d1.getCards().equals(d2.getCards()));
		//check if hand Player(A,10), Dealer(A,10) is draw (PUSH),provide unshuffle just to check
		
	}

}

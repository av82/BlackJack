package com.games.blackjack;

public class Card {
	//properties of a card - Face Value, Suit
	
	private final int value;  //restrict setting of values outside constructor
	private final Suit suit;  //restrict setting of suit outside constructor
	
	public  Card(int value, Suit suit){
		this.value =value; 
		this.suit = suit;
	}
	
	public int getValue(){
		return value; 
	}
	public Suit getSuit(){
		return suit;
	}
	
	@Override
	public String toString(){
		return Integer.toString(this.getValue())+ " of  " + this.getSuit().name();
	}
	
	
}

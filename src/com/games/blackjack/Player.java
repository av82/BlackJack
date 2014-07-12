package com.games.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private int balanceAmount;
	private int bet;
	private String name;
	private int handValue=0;
	private List<Integer> cards;
	
	public Player(String name, int balanceAmount){ 
		this.balanceAmount=balanceAmount;
		this.name = name;
		this.cards = new ArrayList<Integer>();
	}

	public int getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(int balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHandValue(Deck deck) {
		Boolean aceCard=false;
		for(int i=0;i<this.getCards().size();i++){
			int cardValue=deck.getCard(this.getCards().get(i)).getValue();
			if(cardValue>10)
				cardValue=10;
			if(cardValue==1)
				aceCard=true;
			handValue=handValue+cardValue;
			if(aceCard && (handValue+10 <=21))
			   handValue=handValue+10;
		}
		return handValue;
	}
	
	//get indices of cards dealt to this player with respect to the Deck
	public List<Integer> getCards() {
		return cards;
	}
}

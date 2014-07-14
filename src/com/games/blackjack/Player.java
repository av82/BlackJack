package com.games.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private Integer balanceAmount;
	private Integer bet;
	private String name;
	private Integer handValue;
	private List<Integer> cards;

	public Player(String name, Integer balanceAmount) {
		this.balanceAmount = balanceAmount;
		this.name = name;
		this.cards = new ArrayList<Integer>();
		this.handValue = 0;
	}

	public Integer getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Integer balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Integer getBet() {
		return bet;
	}

	public void setBet(Integer bet) {
		this.bet = bet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// get indices of cards dealt to this player with respect to the Deck
	public List<Integer> getCards() {
		return cards;
	}

	public Integer getHandValue(Deck deck) {
		computeHandValue(deck);
		return handValue;
	}

	public void setHandValue(Integer handValue) {
		this.handValue = handValue;
	}

	private void computeHandValue(Deck deck) {
		Boolean hasAceCard = false;
		int tmphandValue = 0;
		for (int i = 0; i < this.getCards().size(); i++) {
			int cardValue = deck.getCard(this.getCards().get(i)).getValue();
			if (cardValue > 10)
				cardValue = 10;
			if (cardValue == 1)
				hasAceCard = true;
			tmphandValue = tmphandValue + cardValue;
			if (hasAceCard && (tmphandValue + 10 <= 21))
				tmphandValue = tmphandValue + 10;
		}
		this.setHandValue(tmphandValue);

	}

}

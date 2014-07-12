package com.games.blackjack;

import java.util.Scanner;

public class Game {
	private Deck deck;
	private Player player;
	private Player dealer;
	private Commands commands;
	public Game(){ 
	};
	public Game(String playerName){ 
		player=new Player(playerName,100);
		dealer=new Player("Dealer",0);
		deck=new Deck();
	}
	
	public boolean isBusted(Player p){ 
		return (p.getHandValue(deck)>21)?true:false;
	}
	
	public void dealTo(Player p){ 
		p.getCards().add(deck.getCurrentIndex());
	}
	public void hit(Player p){
		
		p.getCards().add(deck.getCurrentIndex());
		deck.setCurrentIndex(deck.getCurrentIndex()-1);
	}
	//deal two cards to player and dealer
	public void deal(){ 
		player.getCards().clear();
		dealer.getCards().clear();
		if(deck.getCurrentIndex()<14){
			deck=new Deck();
			deck.shuffle();
		}
			hit(player);
			hit(player);
			hit(dealer);
			hit(dealer);
			showCards();
	}
	
	public void showCards(){
		System.out.println("\t-----------------Dealer--------------------");
		for(int i=0;i<dealer.getCards().size()-1;i++){
			System.out.println("\t"+deck.getCard(dealer.getCards().get(i)).toString());
		}
		System.out.println();
		System.out.println();
		System.out.println("\t-----------------Player--------------------");
		for(int i=0;i<player.getCards().size();i++){
			System.out.println("\t"+deck.getCard(player.getCards().get(i)));
		}
	}
	
	public void showWinner(){ 
		//only base case 
		if(isBusted(player))
		{
			System.out.println("You are busted !!");
			player.setBalanceAmount(player.getBalanceAmount()-player.getBet());
		}
		else if(isBusted(dealer)){ 
			System.out.println("Dealer is busted !!");
		}
		else if(player.getHandValue(deck)>dealer.getHandValue(deck)){
			System.out.println("You Win !!");
			player.setBalanceAmount(player.getBalanceAmount()+3/2*(player.getBet()));
		}
		else if(player.getHandValue(deck)==dealer.getHandValue(deck)){
			System.out.println("Push"); 
		}
		
	}
	public static void main(String[] args){
		
		Game game= new Game();
		System.out.println(" ----------- ---Lets  Play BlackJack --- -------------");
		System.out.println();
		System.out.println();
		System.out.println(" --COMMANDS MENU ---------------------------------"); 
		System.out.println(" ------ Start a game -  y ");
		System.out.println(" ------ Quit a game  -  q ");
		System.out.println(" ------ Call HIT     -  h ");
		System.out.println(" ------ Call STAND   -  s ");
		
		System.out.print("Please enter y and hit return to start:\n ");
		Scanner input = new Scanner(System.in);
		String  command = input.nextLine();
		while (!command.isEmpty()){
			switch(game.commands){
			case START_COMMAND:System.out.print("Please enter y and hit return to start !:\n  ");
            command= input.nextLine();
            default: break;
		}
		
		Game blackjack = new Game("Arun"); 
		blackjack.deal();
		}
		
	}
}

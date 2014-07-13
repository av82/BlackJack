package com.games.blackjack;

import java.util.Scanner;

public class Game {
	private Deck deck;
	private Player player;
	private Player dealer;
	private Commands command;
	public Game(){ 
	};
	public void initGame(String playerName){ 
		player=new Player(playerName,100);
		dealer=new Player("Dealer",0);
		deck=new Deck();
		deck.shuffle();
	}
	
	public Commands getCommand() {
		return command;
	}
	public boolean isBusted(Player p){ 
		return (p.getHandValue(deck)>21)?true:false;
	}
	
	public void dealTo(Player p){ 
		p.getCards().add(deck.getCurrentIndex());
	}
	public void hit(Player p){
		
		p.getCards().add(deck.getCurrentIndex());
		deck.setCurrentIndex(deck.getCurrentIndex()+1);
		System.out.println("testing next card" + deck.getCard(deck.getCurrentIndex()).toString());
	}
	//deal two cards to player and dealer
	public void deal(){ 
		player.getCards().clear();
		dealer.getCards().clear();
		System.out.println("printing 6 cards");
		for(int i=0;i<=6;i++){
			System.out.println("\t"+deck.getCard(i).toString());
		}
		if(deck.getCurrentIndex()>37){  // when 1/3 rd of cards are done, do a fresh shuffle
			deck=new Deck();
			deck.shuffle();
			//test only remove later
			System.out.println("New Shuffle printing 6 cards");
			for(int i=0;i<=6;i++){
				System.out.println("\t"+deck.getCard(i).toString());
			}
		}
		
			hit(player);
			hit(dealer);
			hit(player);
			hit(dealer);
			showCards();
	}
	
	public void showCards(){

		System.out.println("\t-----------------Player's Hand--------------------");
		for(int i=0;i<player.getCards().size();i++){
			System.out.println("\t"+deck.getCard(player.getCards().get(i)));
		}
		System.out.println();
		System.out.println();
		System.out.println("\t-----------------Dealer's Hand--------------------");
		for(int i=0;i<dealer.getCards().size();i++){
			System.out.println("\t"+deck.getCard(dealer.getCards().get(i)).toString());
		}
	}
	
	public void printMenu(){ 
		System.out.println("\n----COMMANDS MENU ---------------------------------\n"); 
		System.out.println("\t--> Start a game -  y ");
		System.out.println("\t--> Quit a game  -  q ");
		System.out.println("\t--> Call HIT     -  h ");
		System.out.println("\t--> Call STAND   -  s ");
	}
	public void callHit(){ 
		hit(player);
		if (this.dealer.getHandValue(deck)<17){
			hit(dealer);
		}
	}
	public void decideWinner(){ 
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
	public static void main(String[] args) throws Exception{
		
		Game blackjack= new Game();
		System.out.println(" ----------- ---Lets  Play BlackJack --- -------------");
		System.out.println();
		System.out.println();
		System.out.println("Please Enter your Name:");
		Scanner input = new Scanner(System.in);
		String playerName= input.nextLine();
		while (((playerName == null) || (playerName.trim().isEmpty()))){
			playerName=input.nextLine();
		}
		System.out.println("\nHello "+playerName + ", Welcome to BlackJack!!");
		blackjack.printMenu(); 
		
		
		System.out.print("\nPlease enter y to start: ");
		String  inputCommand = input.nextLine();
		while (!((inputCommand == null) || (inputCommand.trim().isEmpty()))) {
			try {
				System.out.println(Commands.valueOf(inputCommand)
						.getStatusCode());
				blackjack.command = Commands.valueOf(inputCommand);

				switch (blackjack.command) {
				case m:
					blackjack.printMenu();
				case y:
					blackjack.initGame("Arun");
					blackjack.deal();
					break;
				case q:
					System.out.print("\nQuitting the Game, Enter y if you want to start a new game !:  ");
					break;
				case h:
					System.out.println("\nYour current call --->"+ blackjack.command.getStatusCode());
					blackjack.callHit();
					break;
				case s:
					blackjack.showCards();
					break;
				default:
					break;

				}
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid Command, please enter m to check the menu");
				inputCommand = input.nextLine();
			}
			inputCommand = input.nextLine();
		}
		
	}
}

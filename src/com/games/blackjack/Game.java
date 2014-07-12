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
	
	public void printMenu(){ 
		System.out.println(" --COMMANDS MENU ---------------------------------"); 
		System.out.println(" ------ Start a game -  y ");
		System.out.println(" ------ Quit a game  -  q ");
		System.out.println(" ------ Call HIT     -  h ");
		System.out.println(" ------ Call STAND   -  s ");
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
		
		System.out.println("Hello "+playerName + ", Welcome to BlackJack!!");
		blackjack.printMenu(); 
		
		
		System.out.print("Please enter y and hit return to start:\n ");
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
					System.out.print("Quitting the Game, Enter y if you want to start a new game !:\n  ");
					break;
				case h:
					System.out.println("Your current call --->"+ blackjack.command.getStatusCode());
					break;
				case s:
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

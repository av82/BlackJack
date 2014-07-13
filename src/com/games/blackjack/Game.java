package com.games.blackjack;

import java.util.Scanner;

public class Game {
	private Deck deck;
	private Player player;
	private Player dealer;
	private Commands command;
	private boolean gameOn;
	public Game(){ 
		gameOn=false;
	};
	public void initGame(String playerName){ 
		player=new Player(playerName,100);
		dealer=new Player("Dealer",0);
		deck=new Deck();
		deck.shuffle();
		gameOn=true;
	}
	
	public boolean isGameOn() {
		return gameOn;
	}
	public void setGameOn(boolean gameOn) {
		this.gameOn = gameOn;
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
		//System.out.println("testing next card" + deck.getCard(deck.getCurrentIndex()).toString());
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
			showCards(player);
			showCards(dealer);
	}
	
	public void showCards(Player showman){
		
		System.out.println("\t-----------------"+ showman.getName()+"'s Hand--------------------");
		for(int i=0;i<showman.getCards().size();i++){
			System.out.println("\t"+deck.getCard(showman.getCards().get(i)));
		}
		System.out.println();
		System.out.println();
		//hide dealers card later;
	}
	
	public void printMenu(){ 
		System.out.println("\n----COMMANDS MENU ---------------------------------\n"); 
		System.out.println("\t--> Start a game -  y ");
		System.out.println("\t--> Quit a game  -  q ");
		System.out.println("\t--> Call HIT     -  h ");
		System.out.println("\t--> Call STAND   -  s ");
	}
	public void callHit(){ 
		//if player is already busted hit does not have 
		if(!isBusted(player)){
			hit(player);
			//The dealer should hit until his hand value is 17 or greater.
			if (this.dealer.getHandValue(deck)<17){ 
				hit(dealer);
			}
		}
		else { 
			System.out.println("Sorry you are already busted !\n");
			System.out.println("please ebter y for a new game !\n");
		}
	}
	public void callStand(){
		//The dealer should hit until his hand value is 17 or greater.
		while (this.dealer.getHandValue(deck)<17){ 
					hit(dealer);
				}
	}
	public void decideWinner(){ 
		//only base case 
		System.out.println(player.getName()+"'s Hand Value is: "+ player.getHandValue(deck).toString());
		System.out.println(dealer.getName()+"'s Hand Value is: "+ dealer.getHandValue(deck).toString());
		
		if(isBusted(player))
		{
			System.out.println("You are busted !!");
			player.setBalanceAmount(player.getBalanceAmount()-player.getBet());
		}
		else if(isBusted(dealer)){ 
			System.out.println("Dealer is busted !!");
		}
		else if(player.getHandValue(deck)>dealer.getHandValue(deck)){
			System.out.println("Congrats You Win !!");
			player.setBalanceAmount(player.getBalanceAmount()+3/2*(player.getBet()));
		}
		else if(player.getHandValue(deck)==dealer.getHandValue(deck)){
			System.out.println("Push"); 
		}
		else {
			System.out.println("Dealer Wins !");
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
					break;
				case y:
					if(!blackjack.isGameOn()){
						blackjack.initGame(playerName);
					}
					else{
						System.out.println("Drawing new Hand !!");
						blackjack.player.setHandValue(0);
					}
					blackjack.deal();
					break;
				case q:
					blackjack.setGameOn(false);
					System.out.print("\nQuitting the Game, Enter y if you want to start a new game !:  ");
					break;
				case h:
					if( blackjack.isGameOn()){ //safety check only allowed after game is on
						System.out.println("\nYour current call --->"+ blackjack.command.getStatusCode());
						blackjack.callHit();
					}
					break;
				case s:
					if( blackjack.isGameOn()){//safety check only allowed after game is on
						blackjack.callStand();
						blackjack.showCards(blackjack.player);
						blackjack.showCards(blackjack.dealer);
						blackjack.decideWinner();
						System.out.println("Please enter m for menu, y for a new game");
					}
					break;
				default:
					break;

				}
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid Command, please enter m to check the menu");
				inputCommand = input.nextLine();
			}
			if(blackjack.command.equals(Commands.y) || (blackjack.command.equals(Commands.h))){
				System.out.println("\nPlease enter h or s to hit/stand\n");
			}
			inputCommand = input.nextLine();
		}
		
	}
}

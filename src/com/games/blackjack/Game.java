package com.games.blackjack;

import java.util.Scanner;

public class Game {
	private Deck deck;
	private Player player;
	private Player dealer;
	private Commands command;
	private boolean gameOn;
	private boolean handOn;
	public Game(){ 
		gameOn=false;
		handOn=false;
	};
	public void initGame(String playerName){ 
		player=new Player(playerName,100);
		dealer=new Player("Dealer",0);
		deck=new Deck();
		deck.shuffle();
		gameOn=true;
		handOn=true;
	}
	
	public boolean isGameOn() {
		return gameOn;
	}
	public void setGameOn(boolean gameOn) {
		this.gameOn = gameOn;
	}
	public boolean isHandOn() {
		return handOn;
	}
	public void setHandOn(boolean handOn) {
		this.handOn = handOn;
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
		/* testing only
		 * for(int i=0;i<=6;i++){
			System.out.println("\t"+deck.getCard(i).toString());
		}*/
		
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
		
		System.out.println("\n\t-----------------"+ showman.getName()+"'s Hand--------------------");
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
			showCards(player);
			showCards(dealer);
		}
		else { 
			this.setHandOn(false); // when player is busted turn hand off 
			System.out.println("\nSorry you are already busted !\n");
			System.out.println("\nplease enter y for a new hand !\n");
		}
	}
	public void callStand(){
		//The dealer should hit until his hand value is 17 or greater.
		while (this.dealer.getHandValue(deck)<17){ 
					hit(dealer);
				}
	}
	public boolean checkBalance(Player p){
		if(p.getBalanceAmount()<1){
			System.out.println("\nSorry You donot have any more chips to bet !!");
			System.out.println("\nPlease pay $100 at counter and Enter y to start a new game !!");
			return false;
		}
		else return true;
	}
	public void placeBet(Player p){
		Scanner in =new Scanner(System.in);
		System.out.println("\nPlease enter amount of bet for this hand!!\n");
		  while (!in.hasNextInt()) in.next();
		  Integer playerBet = in.nextInt();
		while (((playerBet == null) || (playerBet<1) || (playerBet >p.getBalanceAmount()))){
			if(playerBet<1){ 
				System.out.println("You should bet more than 1 chip");
			}
			else if(playerBet>p.getBalanceAmount()){
				System.out.println("\nYour bet exceeds your balance amount");
			}
			else{
				System.out.println("\nInvalid bet, please enter a positive bet greater than 1 and less than your balance amount");
			}
					while (!in.hasNextInt()) in.next();
					playerBet=in.nextInt();
		}
		p.setBet(playerBet);
	
	}
	
	public void decideWinner(){ 
		//only base case 
		System.out.println(player.getName()+"'s Hand Value is: "+ player.getHandValue(deck).toString());
		System.out.println(dealer.getName()+"'s Hand Value is: "+ dealer.getHandValue(deck).toString());
		
		if(isBusted(player))
		{
			System.out.println("\nYou are busted !!");
			player.setBalanceAmount(player.getBalanceAmount()-player.getBet());
		}
		else if(isBusted(dealer)){ 
			System.out.println("\nDealer is busted !!");
			System.out.println("\n\nCongrats You Win !!");
			player.setBalanceAmount(player.getBalanceAmount()+(player.getBet()));
		}
		else if(player.getHandValue(deck)>dealer.getHandValue(deck)){
			System.out.println("\nCongrats You Win !!");
			player.setBalanceAmount(player.getBalanceAmount()+ (player.getBet()));
		}
		else if(player.getHandValue(deck)==dealer.getHandValue(deck)){
			System.out.println("\nPush"); 
		}
		else {
			System.out.println("\nDealer Wins !");
			player.setBalanceAmount(player.getBalanceAmount()-player.getBet());
		}
		
		System.out.println("\nYour current balance is $"+ player.getBalanceAmount() );
		
		this.setHandOn(false);
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
						System.out.println("Starting new Game");
						blackjack.initGame(playerName);
					}
					else{
						if(!blackjack.checkBalance(blackjack.player)){
							System.out.println("Starting new Hand");
							break;
						}
						System.out.println("Drawing new Hand !!");
						blackjack.player.setHandValue(0);
					}
					blackjack.placeBet(blackjack.player);
					System.out.println("\n Your current balance is $"+ blackjack.player.getBalanceAmount() );
					
					blackjack.setHandOn(true);
					blackjack.deal();
					break;
					
				case q:
					blackjack.setGameOn(false);
					blackjack.setHandOn(false);
					System.out.print("\n Quitting the Game, Enter y if you want to start a new game !:  ");
					break;
				case h:
					if(!blackjack.checkBalance(blackjack.player))
						break;
					if( blackjack.isGameOn() && blackjack.handOn){ //safety check only allowed after game is on and hand is on
						System.out.println("\n Your current call --->"+ blackjack.command.getStatusCode());
						blackjack.callHit();
					}
					break;
				case s:
					if( blackjack.isGameOn()  && blackjack.handOn){//safety check only allowed after game is on
						blackjack.callStand();
						blackjack.showCards(blackjack.player);
						blackjack.showCards(blackjack.dealer);
						blackjack.decideWinner();
						System.out.println("\n Please enter m for menu or  y for a new hand");
					}
					break;
				default:
					break;

				}
			} catch (IllegalArgumentException e) {
				System.out.println("\nInvalid Command, please enter m to check the menu");
				inputCommand = input.nextLine();
			}
			if(blackjack.command.equals(Commands.y) || (blackjack.command.equals(Commands.h))){
				System.out.println("\nPlease enter h or s to hit/stand\n");
			}
			else System.out.println("\nPlease enter y for new Hand \n");
			inputCommand = input.nextLine();
		}
		
	}
}

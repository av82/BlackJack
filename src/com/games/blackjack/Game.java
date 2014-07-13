package com.games.blackjack;

import java.util.Scanner;

public class Game {
	private Deck deck;
	private Player player;
	private Player dealer;
	private Commands command;
	private boolean gameOn;
	private boolean handOn;
	private boolean checkBlackjack;
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
	public boolean isCheckBlackjack() {
		return checkBlackjack;
	}
	public void setCheckBlackjack(boolean checkBlackjack) {
		this.checkBlackjack = checkBlackjack;
	}
	public Commands getCommand() {
		return command;
	}
	public boolean isBusted(Player p){ 
		return (p.getHandValue(deck)>21)?true:false;
	}
	
	public void dealnewHand(){ 
			this.player.setHandValue(0);
			System.out.println("\n Your current balance is $"+ this.player.getBalanceAmount() );
			this.placeBet(this.player);
			this.setHandOn(true);
			this.deal();
			setCheckBlackjack(true);
			decideWinner();
			setCheckBlackjack(false);
			
			System.out.println("\nPlease enter h or s to hit/stand\n");
	}
	public void hit(Player p){
		
		p.getCards().add(deck.getCurrentIndex());
		deck.setCurrentIndex(deck.getCurrentIndex()+1);
	}
	//deal two cards to player and dealer
	public void deal(){ 
		player.getCards().clear();
		dealer.getCards().clear();
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
			showCards(player,true);
			showCards(dealer,false);
	}
	
	public void showCards(Player showman,boolean showAllCards){
		int showcardSize=showman.getCards().size();
		
		System.out.println("\n\t-----------------"+ showman.getName()+"'s Hand--------------------");
		for(int i=0;i<showcardSize-1;++i){
			System.out.println("\t"+deck.getCard(showman.getCards().get(i)));
		}
		if (showman.getName().equals("Dealer") && !showAllCards){
			System.out.println("\t***Concealed***");
		}
		else{ 
			System.out.println("\t"+deck.getCard(showman.getCards().get(showcardSize-1)));
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
			showCards(player,true);
			showCards(dealer,false);
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
			this.setGameOn(false);
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
		if(!checkBlackjack){
				//only base case 
				System.out.println(player.getName()+"'s Hand Value is: "+ this.player.getHandValue(this.deck).toString());
				System.out.println(dealer.getName()+"'s Hand Value is: "+ this.dealer.getHandValue(this.deck).toString());
				
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
				this.setHandOn(false);
	  }
	else{
		if((player.getHandValue(deck)==21) && (dealer.getHandValue(deck)==21) ){ 
			System.out.println("\nBoth have a Blackjack !!, but you loose Bet !");
			player.setBalanceAmount(player.getBalanceAmount()-player.getBet());
			this.setHandOn(false);
		}
		else if(player.getHandValue(deck)==21){
			System.out.println("\nCongrats You have a BlackJack !!");
			player.setBalanceAmount(player.getBalanceAmount()+(player.getBet()));
			this.setHandOn(false);
		}
		else if(dealer.getHandValue(deck)==21){
			System.out.println("\nDealer has a BlackJack !!");
			player.setBalanceAmount(player.getBalanceAmount()-player.getBet());
			this.setHandOn(false);
		}
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
			boolean menuFlag=false;
			try {
				System.out.println("Your current Call is "+Commands.valueOf(inputCommand)
						.getStatusCode());
				blackjack.command = Commands.valueOf(inputCommand);

				switch (blackjack.command) {
				case m:
					blackjack.printMenu();
					menuFlag=true;
					break;
				case y:
					if(!blackjack.isGameOn()){
						System.out.println("Starting new Game");
						blackjack.initGame(playerName);
						blackjack.dealnewHand();
					}
					else if (!blackjack.isHandOn()){
						if(!blackjack.checkBalance(blackjack.player)){
							System.out.println("Starting new Hand");
							break;
						}
						System.out.println("Drawing new Hand !!");
						blackjack.dealnewHand();
					}
					else System.out.println("Already dealt a hand, please enter--- h---to HIT or -- s-- to STAND !!");
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
						blackjack.callHit();
						if(blackjack.isBusted(blackjack.player))
						{
							blackjack.setHandOn(false); // when player is busted turn hand off 
							blackjack.player.setBalanceAmount(blackjack.player.getBalanceAmount()-blackjack.player.getBet());
							System.out.println("\nSorry you are busted !\n");
							System.out.println("\n Your current balance is $"+ blackjack.player.getBalanceAmount() );
						}
						else System.out.println("\nPlease enter h or s to hit/stand\n");
					}
					break;
				case s:
					if( blackjack.isGameOn()  && blackjack.handOn){//safety check only allowed after game is on
						blackjack.callStand();
						blackjack.showCards(blackjack.player,true);
						blackjack.showCards(blackjack.dealer,true);
						blackjack.decideWinner();
						System.out.println("\n Your current balance is $"+ blackjack.player.getBalanceAmount() );
					}
					break;
				default:
					break;

				}
			} catch (IllegalArgumentException e) {
				System.out.println("\nInvalid Command, please enter m to check the menu");
				inputCommand = input.nextLine();
			}
		
			if(!menuFlag && !blackjack.handOn){
				System.out.println("\nPlease enter y for a new hand !!");
			}
			if(!menuFlag &&!blackjack.checkBalance(blackjack.player)) {
				System.out.println("\n No more Chips (Balance ZERO) !! , Enter y to start a new game !!");
			}
			inputCommand = input.nextLine();
		}
		
	}
}

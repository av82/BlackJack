package com.games.blackjack;

public enum Commands {
	START_COMMAND("y"), QUIT_COMMAND("q"), HIT_COMMAND("h"), STAND_COMMAND("s");
	 
	private String commandCode;
 
	private Commands(String s) {
		commandCode = s;
	}
 
	public String getStatusCode() {
		return commandCode;
	}
}

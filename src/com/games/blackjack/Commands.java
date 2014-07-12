package com.games.blackjack;

public enum Commands {
	m("MENU"),y("START"), q("QUIT"), h("HIT"), s("STAND");
	 
	private String commandCode;
 
	private Commands(String s) {
		commandCode = s;
	}
 
	public String getStatusCode() {
		return commandCode;
	}
}

package com.games.blackjack;

public enum Commands {
	m("MENU"), y("New Hand"), q("Quit the Game"), h("HIT"), s("STAND"), a(
			"Show statistics for Wins and losses");

	private String commandCode;

	private Commands(String s) {
		commandCode = s;
	}

	public String getStatusCode() {
		return commandCode;
	}
}

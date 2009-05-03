package org.simpletext;

public class Game {
	Page page;

	private Game(Page start) {
		this.page = start;
	}

	public static Game from(Page start) {
		return new Game(start);
	}

	public String pageText() {
		return page.text();
	}

	public void input(char c) {
		page = page.chooseNextPage(c);
	}

	public boolean isFinished() {
		return page.isDeadEnd();
	}

	public String choices() {
		return page.choices();
	}
}

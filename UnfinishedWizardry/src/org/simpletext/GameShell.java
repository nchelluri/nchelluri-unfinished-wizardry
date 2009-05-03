package org.simpletext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameShell {
	public static void main(String args[]) throws IOException {
		Page root = createGamePages();
		Game game = Game.from(root);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.println(game.pageText());

			if (game.isFinished()) {
				System.out.println();
				break;
			}

			System.out.println(game.choices());
			game.input(read(in));
			System.out.println();
		}

		System.out.println("Thanks for playing, jerk.");
	}

	private static char read(BufferedReader in) throws IOException {
		char c = 0;
		try {
			while (c == 0) {
				c = in.readLine().charAt(0);
			}
		} catch (StringIndexOutOfBoundsException e) {
		}

		return c;
	}

	private static Page createGamePages() {
		Page last = Page.from("You win!", null);

		List<Page> pages = new ArrayList<Page>();
		pages.add(last);
		String[] strings = Util.strings("Finish him");
		char[] chars = Util.chars('F');
		Collection<Choice> choices = Choice.listFrom(pages, strings, chars);

		Page middle = Page
				.from(
						"Rage courses through his eyes like lightning. He is about to run you through. You have no choice. Do it now",
						choices);
		pages = new ArrayList<Page>();
		pages.add(middle);

		strings = Util.strings("Run into the guy");
		chars = Util.chars('R');
		choices = Choice.listFrom(pages, strings, chars);

		Page two = Page
				.from(
						"You see a guy. You are about to run in to him. He pulls out a sword made of cinnamon toast crunch, and it has been soaking in a sheath of milk, but somehow you can tell it has maintained a core harder than steel.",
						choices);
		pages = new ArrayList<Page>();
		pages.add(two);

		strings = Util.strings("Start Game");
		chars = Util.chars('S');
		choices = Choice.listFrom(pages, strings, chars);

		Page first = Page.from("Hello son", choices);

		return first;
	}
}

package org.simpletext.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.simpletext.Choice;
import org.simpletext.Game;
import org.simpletext.Page;
import org.simpletext.Util;

public class GameTest extends TestCase {
	public void testOnePageGame() {
		Page page = Page.from("Hi there", null);

		Game game = Game.from(page);
		assertEquals("Hi there", game.pageText());
		assertTrue(game.isFinished());
	}

	public void testTwoPageGame() {
		Page last = Page.from("Goodbye", null);

		List<Page> pages = new ArrayList<Page>();
		pages.add(last);

		Collection<Choice> choices = Choice.listFrom(pages,
				Util.strings("Cya"), Util.chars('C'));
		Page first = Page.from("Hello", choices);

		Game game = Game.from(first);

		assertEquals("Hello", game.pageText());
		assertFalse(game.isFinished());

		game.input('C');

		assertEquals("Goodbye", game.pageText());
		assertTrue(game.isFinished());
	}
}

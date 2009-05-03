package org.simpletext.test;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.simpletext.Choice;
import org.simpletext.Page;

public class PageTest extends TestCase {
	public void testThatPageShowsTextCorrectly() {
		Page page = Page.from("Hello world!", null);

		assertEquals("Hello world!", page.text());
	}

	public void testThatPageShowsChoicesCorrectly() {
		Collection<Choice> choices = new ArrayList<Choice>();
		choices.add(Choice.from(null, "First Choice", 'F'));
		choices.add(Choice.from(null, "Second Choice", 'S'));
		choices.add(Choice.from(null, "Third Choice", '3'));

		Page page = Page.from("Something", choices);

		assertEquals("[F]irst Choice\n[S]econd Choice\n3 - Third Choice", page
				.choices());
	}
}

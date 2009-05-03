package org.simpletext.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.simpletext.Choice;
import org.simpletext.Page;
import org.simpletext.Util;

public class UtilTest extends TestCase {

	public void testArrayOfStrings() {
		assertArraysAreEqual(new String[] {}, Util.strings((String[]) null));
		assertArraysAreEqual(new String[] { "abc" }, Util.strings("abc"));
		assertArraysAreEqual(new String[] { "abc", "def" }, Util.strings("abc",
				"def"));
	}

	public void testArrayOfChars() {
		assertArraysAreEqual(new char[] {}, Util.chars((char[]) null));
		assertArraysAreEqual(new char[] { 'a' }, Util.chars('a'));
		assertArraysAreEqual(new char[] { 'a', 'b' }, Util.chars('a', 'b'));
	}

	public void testChoices() {
		ArrayList<Page> pages = new ArrayList<Page>();
		pages.add(Page.from("Page one wheeeeeeeeee!!!!!!", null));
		pages.add(Page.from("Page two sheeeeeeeeeeeeee!", null));
		String[] choiceNames = Util.strings("Page one - intro!!!",
				"Page two - goodbye galaxy");
		char[] choiceChars = Util.chars('1', '2');

		List<Choice> expected = new ArrayList<Choice>();
		expected.add(Choice.from(pages.get(0), "Page one - intro!!!", '1'));
		expected.add(Choice
				.from(pages.get(1), "Page two - goodbye galaxy", '2'));

		List<Choice> actual = Choice.listFrom(pages, choiceNames, choiceChars);

		assertArraysAreEqual(expected.toArray(), actual.toArray());
	}

	private void assertArraysAreEqual(char[] expected, char[] actual) {
		assertTrue(expected.length == actual.length);

		for (int i = 0; i < expected.length; i++) {
			assertTrue(expected[i] == actual[i]);
		}
	}

	private void assertArraysAreEqual(Object[] expected, Object[] actual) {
		assertTrue(expected.length == actual.length);

		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
	}
}

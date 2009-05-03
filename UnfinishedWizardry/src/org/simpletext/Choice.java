package org.simpletext;

import java.util.ArrayList;
import java.util.List;

public class Choice {
	private Page page;
	private String text;
	private char key;

	private Choice(Page page, String text, char key) {
		this.page = page;
		this.text = text;
		this.key = Character.toUpperCase(key);
	}

	public static Choice from(Page page, String text, char key) {
		return new Choice(page, text, key);
	}

	public static List<Choice> listFrom(List<Page> pages, String[] names,
			char[] chars) {
		List<Choice> choices = new ArrayList<Choice>();

		if (pages.size() == names.length && names.length == chars.length) {
			for (int i = 0; i < pages.size(); i++) {
				choices.add(Choice.from(pages.get(i), names[i], chars[i]));
			}
		}

		return choices;
	}

	public boolean hasChar(char c) {
		c = Character.toUpperCase(c);

		if (c == key) {
			return true;
		}

		return false;
	}

	public Page page() {
		return page;
	}

	public String text() {
		String output;

		int lowIndex = text.indexOf(Character.toLowerCase(key));
		int upIndex = text.indexOf(key);
		if (lowIndex == -1 || upIndex == -1) {
			if (lowIndex == upIndex) {
				output = key + " - " + text;
			} else {
				int index = Math.max(lowIndex, upIndex);
				output = textWithKeyHighlighted(index);
			}
		} else {
			int index = Math.min(lowIndex, upIndex);
			output = textWithKeyHighlighted(index);
		}

		return output;
	}

	private String textWithKeyHighlighted(int index) {
		return text.substring(0, index) + "[" + key + "]"
				+ text.substring(index + 1, text.length());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + key;
		result = prime * result + ((page == null) ? 0 : page.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Choice))
			return false;
		final Choice other = (Choice) obj;
		if (key != other.key)
			return false;
		if (page == null) {
			if (other.page != null)
				return false;
		} else if (!page.equals(other.page))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
}

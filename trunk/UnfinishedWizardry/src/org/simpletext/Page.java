package org.simpletext;

import java.util.Collection;

public class Page {
	private String text;
	private Collection<Choice> choices;

	private Page(String text, Collection<Choice> choices) {
		this.text = text;
		this.choices = choices;
	}

	public static Page from(String text, Collection<Choice> choices) {
		return new Page(text, choices);
	}

	public String text() {
		return text;
	}

	public Page chooseNextPage(char c) {
		Page page = this;

		for (Choice choice : choices) {
			if (choice.hasChar(c)) {
				page = choice.page();
				break;
			}
		}

		return page;
	}

	public boolean isDeadEnd() {
		if (choices == null || choices.size() == 0) {
			return true;
		}

		return false;
	}

	public String choices() {
		if (choices == null || choices.isEmpty()) {
			return "";
		}

		StringBuilder output = new StringBuilder();

		for (Choice choice : choices) {
			output.append(choice.text() + "\n");
		}

		output.deleteCharAt(output.length() - 1);

		return output.toString();
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
		result = prime * result + ((choices == null) ? 0 : choices.hashCode());
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
		if (!(obj instanceof Page))
			return false;
		final Page other = (Page) obj;
		if (choices == null) {
			if (other.choices != null)
				return false;
		} else if (!choices.equals(other.choices))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
}

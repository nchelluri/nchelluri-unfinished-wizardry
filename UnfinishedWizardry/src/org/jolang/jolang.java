package org.jolang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class jolang {
	private static final String jolangName = "jolang interpreter";
	private static final String jolangVersion = "v1.0Alpha";
	private static final String jolangFullName = jolangName + " "
			+ jolangVersion;
	private static final String jolangPrompt = "jolang> ";
	private static final String helpReferenceString = "Type \"help;\" for help.";
	private static final Pattern printLiteralStmt = Pattern
			.compile("print \"([^\"]*)\"");
	private static final Pattern printVariableStmt = Pattern
			.compile("print (\\w+)");
	private static final Pattern assignVariableStmt = Pattern
			.compile("(\\w+) = (\\d+)");
	private static final Pattern jovialStmt = Pattern.compile("jovial",
			Pattern.CASE_INSENSITIVE);

	private final BufferedReader in;
	private final PrintStream vmOut;
	private final PrintStream scriptOut;
	private final Map<String, Integer> variables;

	public static void main(String args[]) throws IOException {
		final BufferedReader in = new BufferedReader(new InputStreamReader(
				System.in));
		final PrintStream out = new PrintStream(System.out);

		jolang jolang = new jolang(in, out);

		jolang.start();
	}

	public void start() throws IOException {
		vmOut.println(jolangFullName);
		vmOut.println(helpReferenceString);
		vmOut.println();
		vmOut.println("started.");

		boolean keepRunning;
		do {
			vmOut.print(jolangPrompt);

			final String statement = in.readLine().trim();
			keepRunning = run(statement);
		} while (keepRunning);

		vmOut.println("terminated.");
	}

	public jolang(final BufferedReader in, final PrintStream out) {
		this(in, out, out);
	}

	public jolang(final BufferedReader in, final PrintStream vmOut,
			final PrintStream scriptOut) {
		this.in = in;
		this.vmOut = vmOut;
		this.scriptOut = scriptOut;
		this.variables = new HashMap<String, Integer>();
	}

	private boolean run(String statement) {
		boolean keepRunning = true;

		if (!statement.endsWith(";")) {
			printStatementMissingSemicolon();
			return keepRunning;
		}

		statement = statement.substring(0, statement.length() - 1);
		statement = statement.replaceAll("\\s+", " ").trim();
		Matcher printLiteralMatcher = printLiteralStmt.matcher(statement);
		Matcher printVariableMatcher = printVariableStmt.matcher(statement);
		Matcher assignVariableMatcher = assignVariableStmt.matcher(statement);
		Matcher jovialMatcher = jovialStmt.matcher(statement);

		if ("".equals(statement)) {
			// no-op
		} else if ("help".equals(statement)) {
			printHelp();
		} else if ("version".equals(statement)) {
			vmOut.println(jolangVersion);
		} else if ("exit".equals(statement)) {
			keepRunning = false;
		} else if (printLiteralMatcher.matches()) {
			String value = printLiteralMatcher.group(1);
			scriptOut.println(value);
		} else if (printVariableMatcher.matches()) {
			Integer value = variables.get(printVariableMatcher.group(1));
			scriptOut.println(value);
		} else if (assignVariableMatcher.matches()) {
			String name = assignVariableMatcher.group(1);
			int value = Integer.parseInt(assignVariableMatcher.group(2));
			variables.put(name, value);
		} else if (jovialMatcher.matches()) {
			vmOut.println("Renny's\n");
		} else {
			printInvalidStatement();
		}

		return keepRunning;
	}

	private void printHelp() {
		vmOut.println(jolangName + ":");
		vmOut.println("\thelp;\t\t\tshow this help information");
		vmOut.println("\tprint \"<string>\";\tprints <string>");
		vmOut.println("\tversion;\t\tdisplay version information");
		vmOut.println("\texit;\t\t\tterminate jolang interpreter");
	}

	private void printInvalidStatement() {
		vmOut.println("Invalid statement.\n");
	}

	private void printStatementMissingSemicolon() {
		vmOut.println("Syntax error: statement must end with a semi-colon.\n");
	}
}

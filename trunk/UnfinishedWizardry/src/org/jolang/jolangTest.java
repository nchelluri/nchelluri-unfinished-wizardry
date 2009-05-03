package org.jolang;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import junit.framework.TestCase;

public class jolangTest extends TestCase {
	private BufferedReader in;
	private ByteArrayOutputStream redirect;
	private String script;

	public void testPrint() throws IOException {
		script = "print \"\";\n" + "exit;\n";
		assertOutputIs("");

		script = "print \"hi there\";\n" + "exit;\n";
		assertOutputIs("hi there");
	}

	public void testSimpleVariable() throws IOException {
		script = "x = 5;\n" + "print x;\n" + "exit;\n";
		assertOutputIs("5");

		script = "x = 5;\n" + "y = 3;\n" + "print y;\n" + "print x;\n"
				+ "exit;\n";
		assertOutputIs("3\r\n" + "5");
	}

	private void assertOutputIs(String expected) throws IOException {
		runScript(script);
		System.out.println("Script:");
		System.out.println(script);
		System.out.println("Expected:");
		System.out.println(expected);
		String actual = redirect.toString().trim();
		System.out.println("Actual:");
		System.out.println(actual);
		System.out.println("\n==\n");
		assertEquals(expected, actual);
	}

	private void runScript(String script) throws IOException {
		in = new BufferedReader(new StringReader(script));
		String nullFilename = "/dev/null";
		if (System.getProperty("os.name").contains("Windows")) {
			nullFilename = "nul";
		}
		PrintStream vmOut = new PrintStream(nullFilename);
		redirect = new ByteArrayOutputStream();
		PrintStream scriptOut = new PrintStream(redirect);

		jolang vm = new jolang(in, vmOut, scriptOut);

		vm.start();
	}
}

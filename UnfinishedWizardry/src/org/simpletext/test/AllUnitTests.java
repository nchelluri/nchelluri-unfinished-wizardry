package org.simpletext.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllUnitTests {
	public static Test suite() {
		TestSuite suite = new TestSuite("All Unit Tests");
		suite.addTestSuite(GameTest.class);
		suite.addTestSuite(UtilTest.class);
		suite.addTestSuite(PageTest.class);

		return suite;
	}
}

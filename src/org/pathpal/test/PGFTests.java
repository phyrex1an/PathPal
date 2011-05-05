package org.pathpal.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.grammaticalframework.UnknownLanguageException;
import org.pathpal.DirectionsForm;
import org.pathpal.TranslatorApi;

import junit.framework.TestCase;

public class PGFTests extends TestCase {
	public void testShouldParseGoTo() {
		TestForm tf = new TestForm();
		try {
			TranslatorApi.translateString("I need to go to Brunnsparken", tf);
			assertEquals(tf.address, "Brunnsparken");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			fail();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail();
			e.printStackTrace();
		} catch (UnknownLanguageException e) {
			// TODO Auto-generated catch block
			fail();
			e.printStackTrace();
		}
	}
	private class TestForm extends DirectionsForm {
		public String address;
		public void goToAddress(String address) {
			this.address = address;
		}
	}
}

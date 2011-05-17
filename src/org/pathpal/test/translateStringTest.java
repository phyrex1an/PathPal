package org.pathpal.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import org.grammaticalframework.UnknownLanguageException;
import org.pathpal.DirectionsForm;
import org.pathpal.TranslatorApi;
import android.R;

import junit.framework.TestCase;

public class translateStringTest extends TestCase {
	public void testTranslateString() throws FileNotFoundException, IOException, UnknownLanguageException{
		MockDirectionsForm form = new MockDirectionsForm();
		InputStream st = new FileInputStream("res/raw/locator.pgf") ;
		assertTrue(TranslatorApi.translateString("I need to go from abc to def",form,st));
		assertTrue(form.startAt.equals("abc"));
		assertTrue(form.travelTo.equals("def"));
		
	}
	
	public class MockDirectionsForm extends DirectionsForm
	{
		public String travelTo;
		public String startAt;
		public MockDirectionsForm() { super(null); }
		public DirectionsForm travelTo(String t) { travelTo = t; return this; }
		public DirectionsForm startAt(String t) { startAt = t; return this; }
	}

}

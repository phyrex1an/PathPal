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
		/*
		assertTrue(TranslatorApi.translateString("I want to go by car to abc",form,st));
		assertTrue(form.travelTo.equals("abc"));
		assertTrue(form.byCar == true);
		*/
		assertTrue(TranslatorApi.translateString("I want to go by car from Dixon to Trixon",form,st));
		assertTrue(form.travelTo.equals("Trixon"));
		assertTrue(form.startAt.equals("Dixon"));
		assertTrue(form.byCar == true);
		
	}
	
	public class MockDirectionsForm extends DirectionsForm
	{
		public String travelTo;
		public String startAt;
		public boolean byCar;
		public MockDirectionsForm() { super(null); }
		public void byCar(){byCar = true;}
		public DirectionsForm travelTo(String t) { travelTo = t; return this; }
		public DirectionsForm startAt(String t) { startAt = t; return this; }
	}

}

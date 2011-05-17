package org.pathpal.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;


import org.grammaticalframework.UnknownLanguageException;
import org.grammaticalframework.Linearizer.LinearizerException;
import org.pathpal.DirectionsForm;
import org.pathpal.TranslatorApi;
import org.pathpal.translator.Fun;
import org.pathpal.translator.FunApp;

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
	
	public void testShouldMakeLinearization() throws LinearizerException, IOException, UnknownLanguageException {
		LinkedList<Fun> fs = new LinkedList<Fun>();
		fs.add(new FunApp("DString3", new LinkedList<Fun>()));
		InputStream st = new FileInputStream("res/raw/questions.pgf") ;
		assertTrue(TranslatorApi.makeTranslation(new FunApp("AskGoTo", fs), new LinkedList<String>(), st).equals("Do you want to go to dummy, dummy or dummy ?"));
		LinkedList<String> ss = new LinkedList<String>();
		ss.add("apa");
		ss.add("bepa");
		ss.add("cykel");
		st = new FileInputStream("res/raw/questions.pgf") ;
		assertTrue(TranslatorApi.makeTranslation(new FunApp("AskGoTo", fs), ss, st).equals("Do you want to go to apa, bepa or cykel ?"));
	
	}
	
	public class MockDirectionsForm extends DirectionsForm
	{
		public String travelTo;
		public String startAt;
		public boolean byCar;
		public MockDirectionsForm() { super(); }
		public void byCar(){byCar = true;}
		public DirectionsForm travelTo(String t) { travelTo = t; return null; }
		public void startAt(String t) { startAt = t; }
	}

}

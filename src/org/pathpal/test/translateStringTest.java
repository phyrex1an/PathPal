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
		DirectionsForm form = null;
		InputStream st = new FileInputStream("res/raw/locator.pgf") ;
		
		TranslatorApi.translateString("I need to go from abc to def",form,st);
		
	}

}

package org.pathpal;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.grammaticalframework.*;
import org.grammaticalframework.Trees.Absyn.*;
import org.grammaticalframework.parser.ParseState;
import org.pathpal.translator.AddressVisitor;
import org.pathpal.translator.Fun;
import org.pathpal.translator.FunApp;
import org.pathpal.translator.FunString;

public class TranslatorApi {
	public static boolean translateString(String inputString, DirectionsForm form, InputStream pgffile) throws FileNotFoundException, IOException, UnknownLanguageException {
		PGF pgf = PGFBuilder.fromInputStream(pgffile);
		ParseState ps = new ParseState(pgf.concrete("LocatorEng"));
		String [] tokens = inputString.split(" ");
		boolean in = false ;
		StringBuffer s = new StringBuffer();
		LinkedList<String> ss = new LinkedList<String>();
		for(String t : tokens) {
			if (!ps.scan(t)) {
				if(!in) {
					ps.scan("dummy");
					s.append(" " + t);
					in = true;
				} else {
					s.append(" " + t);
				}
			} else {
				if(in) {
					in = false;
					ss.add(s.toString().trim());
					s = new StringBuffer();
				}
			}
		}
		if(in) {
			ss.add(s.toString().trim());
		}
		if (ps.getTrees().length < 1) {
			return false;
		}
		
		FunApp f = (FunApp) new AddressVisitor(ss).visit((Application) ps.getTrees()[0], new LinkedList<Fun>());
		if (f.getIdent().equals("GoFromTo")) {
			form.startAt(((FunString) f.getArgs().get(0)).getString());
			form.travelTo(((FunString) f.getArgs().get(1)).getString());
		} else if (f.getIdent().equals("GoTo")) {
			form.travelTo(((FunString) f.getArgs().get(0)).getString());
		}
		return true;
	}
}

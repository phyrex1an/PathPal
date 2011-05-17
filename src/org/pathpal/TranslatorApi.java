package org.pathpal;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.grammaticalframework.*;
import org.grammaticalframework.Linearizer.LinearizerException;
import org.grammaticalframework.Trees.Absyn.*;
import org.grammaticalframework.parser.ParseState;
import org.pathpal.translator.AddressVisitor;
import org.pathpal.translator.Fun;
import org.pathpal.translator.FunApp;
import org.pathpal.translator.FunString;

public class TranslatorApi {
	public static boolean translateString(String inputString, DirectionsForm form, InputStream pgffile) throws FileNotFoundException, IOException, UnknownLanguageException {
		FunApp f = (FunApp) parseString(inputString, pgffile);
		if (f == null) {
			return false;
		} else if (f.getIdent().equals("GoFromTo")) {
			form.startAt(((FunString) f.getArgs().get(0)).getString());
			form.travelTo(((FunString) f.getArgs().get(1)).getString());
		} else if (f.getIdent().equals("GoTo")) {
			form.travelTo(((FunString) f.getArgs().get(1)).getString());
		} else if (f.getIdent().equals("GoByCarTo")) {
			form.travelTo(((FunString) f.getArgs().get(1)).getString());
			form.byCar();
		} else if (f.getIdent().equals("GoByCarFromTo")) {
			form.startAt(((FunString) f.getArgs().get(1)).getString());
			form.travelTo(((FunString) f.getArgs().get(2)).getString());
			form.byCar();
		} else if (f.getIdent().equals("GoFromToVia")){
			form.startAt(((FunString) f.getArgs().get(1)).getString());
			form.travelTo(((FunString) f.getArgs().get(3)).getString());
			form.startAt(((FunString) f.getArgs().get(3)).getString());
			form.travelTo(((FunString) f.getArgs().get(2)).getString());
			
		} else if (f.getIdent().equals("GoToVia")){
			form.travelTo(((FunString) f.getArgs().get(2)).getString());
			form.travelTo(((FunString) f.getArgs().get(1)).getString());
		} else if (f.getIdent().equals("WalkOrTrans")){
			if (((FunApp)f.getArgs().get(0)).getIdent().equals("Walking")){
				form.byFoot();
			}else {
				form.byCar();
			}
				
		} else if (f.getIdent().equals("ProbablyAnAddress")) {
			form.reset();
			form.travelTo(((FunString) f.getArgs().get(0)).getString());
		}
		
		return true;
	}
	
	static Fun parseString(String inputString, InputStream pgffile) throws FileNotFoundException, IOException, UnknownLanguageException {
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
			return null;
		}
		Tree t = ps.getTrees()[0];
		if (t == null) {
			LinkedList<Fun> as = new LinkedList<Fun>();
			as.add(new FunString(inputString));
			return new FunApp("ProbablyAnAddress", as);
		} else {
			return new AddressVisitor(ss).visit((Application) t, new LinkedList<Fun>());
		}
	}
	
	public static String makeTranslation(Fun f, List<String> ss,  InputStream pgffile) throws LinearizerException, FileNotFoundException, IOException, UnknownLanguageException {
		Tree t = AddressVisitor.funToTree(f);
		PGF pgf = PGFBuilder.fromInputStream(pgffile);
		Linearizer lin = new Linearizer(pgf, "QuestionsEng");
		String ret = lin.linearizeString(t);
		for (String s : ss) {
			ret = ret.replaceFirst("dummy", s);
		}
		return ret;
	}
	
}

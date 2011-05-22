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
		if (inputString.trim().equals("")) { 
			return false;
		}
		FunApp f = (FunApp) parseString(inputString.toLowerCase(), pgffile);
		if (f == null) {
			return false;
		} else if(f.getIdent().equals("INeedWantTo")){ 
			f = (FunApp) f.getArgs().get(1);
			translateGoItem(f,form);
		}
		else if(f.getIdent().equals("AndThen")){ 
			f = (FunApp) f.getArgs().get(0);
			translateGoItem(f,form);
		}
		else if(f.getIdent().equals("JustGo")){ 
			f = (FunApp) f.getArgs().get(0);
			translateGoItem(f,form);
		}
		else if (f.getIdent().equals("WalkOrTrans")){
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
	
	public static String fromDummy(FunApp f){
       return ((FunString) f.getArgs().get(0)).getString();
	}
	
	public static boolean translateGoItem(FunApp f,DirectionsForm form){
		String ident = f.getIdent();
		if (ident.equals("GoFromTo")) {
			form.startAt(fromDummy((FunApp)f.getArgs().get(0)));
			form.travelTo(fromDummy((FunApp)f.getArgs().get(1)));
		}
		else if (ident.equals("GoTo")) {
			form.travelTo(fromDummy((FunApp)f.getArgs().get(0)));
		} else if (ident.equals("GoByCarTo")) {
			form.byCar();
			form.travelTo(fromDummy((FunApp)f.getArgs().get(0)));
		} else if (ident.equals("GoFromTo")) {
			form.reset();
			form.startAt(fromDummy((FunApp)f.getArgs().get(0)));
			form.travelTo(fromDummy((FunApp)f.getArgs().get(1)));
		} else if (ident.equals("GoTo")) {
			form.reset();
			form.travelTo(fromDummy((FunApp)f.getArgs().get(0)));
		} else if (ident.equals("GoByCarTo")) {
			form.reset();
			form.travelTo(fromDummy((FunApp)f.getArgs().get(0)));
			form.byCar();
		} else if (ident.equals("GoByCarFromTo")) {
			form.reset();
			form.startAt(fromDummy((FunApp)f.getArgs().get(0)));
			form.travelTo(fromDummy((FunApp)f.getArgs().get(1)));
			form.byCar();
		} else if (ident.equals("GoFromToVia")){
			form.reset();
			form.startAt(fromDummy((FunApp)f.getArgs().get(0)));
			form.travelTo(fromDummy((FunApp)f.getArgs().get(2)));
			form.travelTo(fromDummy((FunApp)f.getArgs().get(1)));
		} else if (ident.equals("GoToVia")){
			form.reset();
			form.travelTo(fromDummy((FunApp)f.getArgs().get(1)));
			form.travelTo(fromDummy((FunApp)f.getArgs().get(0)));
		}else if (ident.equals("GoFrom")){
			form.startAt(fromDummy((FunApp)f.getArgs().get(0)));
		}else if (ident.equals("FromTo")){
			form.reset();
			form.travelTo(fromDummy((FunApp)f.getArgs().get(1)));
			form.startAt(fromDummy((FunApp)f.getArgs().get(0)));
		}else if (ident.equals("GoTo2")){
			form.travelTo(fromDummy((FunApp)f.getArgs().get(0)));
		}else if (ident.equals("WalkTo")){
			form.travelTo(fromDummy((FunApp)f.getArgs().get(0)));
			form.byFoot();
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
			LinkedList<Fun> as = new LinkedList<Fun>();
			as.add(new FunString(inputString));
			return new FunApp("ProbablyAnAddress", as);
		}
		return new AddressVisitor(ss).visit((Application) ps.getTrees()[0], new LinkedList<Fun>());
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

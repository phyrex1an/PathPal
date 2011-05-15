package org.pathpal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;


import org.grammaticalframework.*;
import org.grammaticalframework.Trees.Absyn.*;
import org.pathpal.translator.AddressVisitor;
import org.pathpal.translator.FunApp;

public class TranslatorApi {
	public static boolean translateString(String inputString, DirectionsForm form, InputStream pgffile) throws FileNotFoundException, IOException, UnknownLanguageException {
		String[] words = inputString.split(" ");
		PGF pgf = PGFBuilder.fromInputStream(pgffile);
		Parser parser = new Parser(pgf, "LocatorEng");
		
		Tree[] trees = (Tree[]) parser.parse(words).getTrees();
		if(trees.length < 1) { System.out.println("LOCATOR length < 1"); return false; }
		
		AddressVisitor a = new AddressVisitor();
		FunApp funapp = a.visit((Application) trees[0], new LinkedList<FunApp>());
		if (funapp.getIdent().equals("GoTo")){
			FunApp f = ((LinkedList<FunApp>) funapp.getArgs()).getFirst();
			StringBuffer destString = new StringBuffer();
			while (!(f.getIdent().equals("AddressL"))){
				destString.append(((LinkedList<FunApp>) f.getArgs()).getFirst().getIdent());
				f = f.getArgs().get(1);
			}
			System.out.println(destString.toString());
			form.travelTo(destString.toString());
			
			
		}
		
		/*
		if(!(trees[0] instanceof Application)) { System.out.println("0 !instanceof Application"); return false; }
		Application tree = (Application) trees[0];
		if(!((Function) tree.tree_1).ident_.equals("GoTo")) { System.out.println("ident_ != GoTo. It is: " + ((Function) tree.tree_1).ident_); return false; }
		form.travelTo(((Function) tree.tree_2).ident_);
		*/
		
		return true;
	}
}

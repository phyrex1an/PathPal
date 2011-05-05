package org.pathpal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.grammaticalframework.*;
import org.grammaticalframework.Trees.Absyn.*;

public class TranslatorApi {
	public static boolean translateString(String inputString, DirectionsForm form, InputStream pgffile) throws FileNotFoundException, IOException, UnknownLanguageException {
		String[] words = inputString.split(" ");
		PGF pgf = PGFBuilder.fromInputStream(pgffile);
		Parser parser = new Parser(pgf, "LocatorSwe");
		Tree[] trees = (Tree[]) parser.parse(words).getTrees();
		if(trees.length < 1) { return false; }
		if(!(trees[0] instanceof Application)) { return false; }
		Application tree = (Application) trees[0];
		if(((Function) tree.tree_1).ident_ != "GoTo") { return false; }
		form.goToAddress(((Function) tree.tree_2).ident_);
		return true;
	}

}

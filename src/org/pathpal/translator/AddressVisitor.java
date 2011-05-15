package org.pathpal.translator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.grammaticalframework.Trees.Absyn.Application;
import org.grammaticalframework.Trees.Absyn.Function;
import org.grammaticalframework.Trees.Absyn.Lambda;
import org.grammaticalframework.Trees.Absyn.Literal;
import org.grammaticalframework.Trees.Absyn.MetaVariable;
import org.grammaticalframework.Trees.Absyn.Variable;

public class AddressVisitor implements org.grammaticalframework.Trees.Absyn.Tree.Visitor<FunApp, LinkedList<FunApp>> {
	public FunApp visit(Lambda arg0, LinkedList<FunApp> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public FunApp visit(Variable arg0, LinkedList<FunApp> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public FunApp visit(Application arg0, LinkedList<FunApp> arg1) {
		arg1.addFirst(arg0.tree_2.accept(this, new LinkedList<FunApp>()));
		return arg0.tree_1.accept(this, arg1);
	}

	public FunApp visit(Literal arg0, LinkedList<FunApp> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public FunApp visit(MetaVariable arg0, LinkedList<FunApp> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public FunApp visit(Function arg0, LinkedList<FunApp> arg1) {
		return new FunApp(arg0.ident_, arg1);
	}

}
package org.pathpal.translator;

import java.util.*;

import org.grammaticalframework.Trees.Absyn.Application;
import org.grammaticalframework.Trees.Absyn.Function;
import org.grammaticalframework.Trees.Absyn.Lambda;
import org.grammaticalframework.Trees.Absyn.Literal;
import org.grammaticalframework.Trees.Absyn.MetaVariable;
import org.grammaticalframework.Trees.Absyn.Variable;

public class AddressVisitor implements org.grammaticalframework.Trees.Absyn.Tree.Visitor<Fun, LinkedList<Fun>> {
	private LinkedList<String> queue;
	public AddressVisitor(LinkedList<String> q) {
		queue = q;
	}

	public Fun visit(Lambda arg0, LinkedList<Fun> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Fun visit(Variable arg0, LinkedList<Fun> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Fun visit(Application arg0, LinkedList<Fun> arg1) {
		arg1.addFirst(arg0.tree_2.accept(this, new LinkedList<Fun>()));
		return arg0.tree_1.accept(this, arg1);
	}

	public Fun visit(Literal arg0, LinkedList<Fun> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Fun visit(MetaVariable arg0, LinkedList<Fun> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Fun visit(Function arg0, LinkedList<Fun> arg1) {
		if(arg0.ident_.equals("DString")) {
			return new FunString(queue.removeLast());
		}
		return new FunApp(arg0.ident_, arg1);
	}

}
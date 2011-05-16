package org.pathpal.test;

import java.util.ArrayList;

import java.util.LinkedList;

import org.grammaticalframework.Trees.Absyn.Application;
import org.grammaticalframework.Trees.Absyn.Function;
import org.pathpal.translator.AddressVisitor;
import org.pathpal.translator.Fun;
import org.pathpal.translator.FunApp;

import junit.framework.TestCase;

public class AddressVisitorTest extends TestCase {
	public void setUp() {
		
	}
	
	public void testShouldBuildFunApp() {
		AddressVisitor a = new AddressVisitor(new LinkedList<String>());
		assertTrue(((FunApp) a.visit(new Function("GoFromTo"), new LinkedList<Fun>())).getIdent().equals("GoFromTo"));
		assertTrue(((FunApp) a.visit(new Application(new Function("GoFromTo"), new Function ("Foo")), new LinkedList<Fun>())).getIdent().equals("GoFromTo"));
//		assertTrue(((FunApp) a.visit(new Application(new Function("GoFromTo"), new Function ("Foo")), new LinkedList<Fun>())).getArgs().get(0).getIdent().equals("Foo"));
//		assertTrue(((FunApp) a.visit(new Application(new Application(new Function("GoFromTo"), new Function ("Foo")), new Function("Bar")), new LinkedList<Fun>())).getArgs().get(1).getString().equals("Bar"));
		assertTrue(a.visit(new Application(new Application(new Function("GoFromTo"), new Function ("Foo")), new Function("Bar")), new LinkedList<Fun>()).toString().equals("GoFromTo (Foo) (Bar)"));
	}
}

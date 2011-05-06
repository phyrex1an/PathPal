package org.pathpal.test;

import java.util.ArrayList;
import java.util.LinkedList;

import org.grammaticalframework.Trees.Absyn.Application;
import org.grammaticalframework.Trees.Absyn.Function;
import org.pathpal.translator.AddressVisitor;
import org.pathpal.translator.FunApp;

import junit.framework.TestCase;

public class AddressVisitorTest extends TestCase {
	public void setUp() {
		
	}
	
	public void testShouldBuildFunApp() {
		AddressVisitor a = new AddressVisitor();
		assertTrue(a.visit(new Function("GoFromTo"), new LinkedList<FunApp>()).getIdent().equals("GoFromTo"));
		assertTrue(a.visit(new Application(new Function("GoFromTo"), new Function ("Foo")), new LinkedList<FunApp>()).getIdent().equals("GoFromTo"));
		assertTrue(a.visit(new Application(new Function("GoFromTo"), new Function ("Foo")), new LinkedList<FunApp>()).getArgs().get(0).getIdent().equals("Foo"));
		assertTrue(a.visit(new Application(new Application(new Function("GoFromTo"), new Function ("Foo")), new Function("Bar")), new LinkedList<FunApp>()).getArgs().get(1).getIdent().equals("Bar"));
		assertTrue(a.visit(new Application(new Application(new Function("GoFromTo"), new Function ("Foo")), new Function("Bar")), new LinkedList<FunApp>()).toString().equals("GoFromTo (Foo) (Bar)"));
	}
}

package org.pathpal.test;

import java.util.ArrayList;

import org.grammaticalframework.Trees.Absyn.Function;
import org.pathpal.translator.AddressVisitor;
import org.pathpal.translator.FunApp;

import junit.framework.TestCase;

public class AddressVisitorTest extends TestCase {
	public void testShouldBuildFunApp() {
		AddressVisitor a = new AddressVisitor();
		assertEquals(a.visit(new Function("GoFromTo"), new ArrayList<FunApp>()), new Function("GoFromTo"));
	}
}

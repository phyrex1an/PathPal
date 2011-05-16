package org.pathpal.translator;

public class FunString implements Fun {
	private String string;
	public FunString(String s) {
		string = s;
	}
	public String getString() { return string; }
	public String toString() { return "\"" + getString() + "\""; }
}
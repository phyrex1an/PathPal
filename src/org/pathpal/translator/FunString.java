package org.pathpal.translator;

public class FunString implements Fun {
	private String string;
	public FunString(String s) {
		string = s.trim();
	}
	public String getString() { return string; }
	public String toString() { return "\"" + getString() + "\""; }
}
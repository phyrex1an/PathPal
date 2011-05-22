package org.pathpal.translator;

import java.util.*;

public class FunApp implements Fun {
	private String ident;
	private List<? extends Fun> args;
	public FunApp(String ident, List<? extends Fun> args) {
		this.setIdent(ident);
		this.setArgs(args);
	}
	public void setIdent(String ident) {
		this.ident = ident;
	}
	public String getIdent() {
		return ident;
	}
	public void setArgs(List<? extends Fun> args2) {
		this.args = args2;
	}
	public List<? extends Fun> getArgs() {
		return args;
	}
	public String toString() {
		StringBuffer args = new StringBuffer();
		for(Fun arg : this.args) args.append(" (" + arg.toString() + ")");
		return getIdent() + args.toString();
	}
}
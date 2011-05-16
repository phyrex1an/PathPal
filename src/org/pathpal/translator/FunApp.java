package org.pathpal.translator;

import java.util.List;

public class FunApp implements Fun {
	private String ident;
	private List<Fun> args;
	public FunApp(String ident, List<Fun> args) {
		this.setIdent(ident);
		this.setArgs(args);
	}
	public void setIdent(String ident) {
		this.ident = ident;
	}
	public String getIdent() {
		return ident;
	}
	public void setArgs(List<Fun> args2) {
		this.args = args2;
	}
	public List<Fun> getArgs() {
		return args;
	}
	public String toString() {
		StringBuffer args = new StringBuffer();
		for(Fun arg : this.args) args.append(" (" + arg.toString() + ")");
		return getIdent() + args.toString();
	}
}
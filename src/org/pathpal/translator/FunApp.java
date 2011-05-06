package org.pathpal.translator;

import java.util.List;

public class FunApp {
	private String ident;
	private List<FunApp> args;
	public FunApp(String ident, List<FunApp> args) {
		this.setIdent(ident);
		this.setArgs(args);
	}
	public void setIdent(String ident) {
		this.ident = ident;
	}
	public String getIdent() {
		return ident;
	}
	public void setArgs(List<FunApp> args2) {
		this.args = args2;
	}
	public List<FunApp> getArgs() {
		return args;
	}
}
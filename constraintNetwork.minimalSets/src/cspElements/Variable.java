package cspElements;

public class Variable {
	String id;
	String domain;
	
	public Variable(String id, String dom){
		this.id=id;
		domain=dom;
	}
	public String getId(){
		return id;
	}
	
	public String getDomain(){
		return domain;
	}
	

}

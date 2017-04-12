package cspElements;

public class Variable implements Comparable<Variable>{
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

	@Override
	public int compareTo(Variable o) {
		// TODO Auto-generated method stub
		return id.compareTo(o.getId());
	}
	

}

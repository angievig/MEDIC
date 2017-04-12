package cases;

import java.util.Set;
import java.util.TreeSet;

import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;

public class RenaultLogan {
	
	//CCP
	private CSP csp;
	//variables
	private Variable carbody, brakes, driveWheel, engine, tyreType;
	private Constraint c1, c2, c3, c4 , c5, oc1, oc2, oc3;
	private TreeSet<Variable> vars=  new TreeSet<Variable>();
	private TreeSet <Constraint> cons= new TreeSet<Constraint>();
	
	
	public CSP getCSP(){
		initVariables();
		initConstraints();
		makeCSP();
		return csp;
	}
	
	public void initVariables(){
		carbody= new Variable("Carbody", "1..3");
		engine= new Variable("Engine", "1..3");
		brakes= new Variable("Brakes", "1..3");
		driveWheel= new Variable("DriveWheel", "1..2");
		tyreType= new Variable("TyreType", "1..2");
		
		vars.add(carbody);
		vars.add(engine);
		vars.add(brakes);
		vars.add(driveWheel);
		vars.add(tyreType);	
	}
	
	public void initConstraints(){
		//constraints from c1--c5
		c1= new Constraint("C1","Carbody#= 1 #==> TyreType#\\=1");
		c1.addVariable(carbody);
		c1.addVariable(tyreType);
		
		c2= new Constraint("C2","(Carbody#= 1 #==> Brakes#=1)  #/\\ (Carbody#= 2 #==> Brakes#=2)");
		c2.addVariable(carbody);
		c2.addVariable(brakes);
		
		c3= new Constraint("C3","Carbody#= 3 #==> (Brakes#=3 #/\\ DriveWheel#=2)");
		c3.addVariable(carbody);
		c3.addVariable(brakes);
		c3.addVariable(driveWheel);
		
		c4= new Constraint("C4","Brakes#= 1 #==> DriveWheel#\\=2");
		c4.addVariable(brakes);
		c4.addVariable(driveWheel);
		
		c5= new Constraint("C5","DriveWheel#= 1 #==> Engine#=1");
		c5.addVariable(driveWheel);
		c5.addVariable(engine);
		
		//option constraints
		oc1=new Constraint("OC1", "Carbody#=1");
		oc1.addVariable(carbody);
		
		oc2=new Constraint("OC2", "Engine#\\= 1");
		oc2.addVariable(engine);
		
		oc3=new Constraint("OC3", "TyreType#=2");
		oc3.addVariable(tyreType);
		//adding constraints
		cons.add(c1);
		cons.add(c2);
		cons.add(c3);
		cons.add(c4);
		cons.add(c5);
		
		// version cons
		cons.add(oc1);
		cons.add(oc2);
		//cons.add(oc3);
		
	}
	public  void makeCSP(){
		csp= new CSP();
		csp.setVariables(vars);
		csp.setConstraints(cons);
	}
	
	public String getStart(){
		return carbody.getId();
	}

}

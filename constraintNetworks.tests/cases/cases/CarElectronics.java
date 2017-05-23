package cases;

import java.util.TreeSet;

import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;

public class CarElectronics {

	private CSP csp;
	//variables
	private Variable carElectronics, accessories, electronicWindows, electricSystem, 
					electricManager,
					devices,
					stereo,
					sound,
					gps,
					type1,
					type2,
					electricMg1,
					electricMg2,
					airConditioner,
					wipers;

	
	private Constraint c0, c1, c2, c3, c4 , c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15;
	private TreeSet<Variable> vars=  new TreeSet<Variable>();
	private TreeSet <Constraint> cons= new TreeSet<Constraint>();
	
	
	public CSP getCSP(){
		initVariables();
		initConstraints();
		makeCSP();
		return csp;
	}
	
	public void initVariables(){
		
		carElectronics =  new Variable("Car_electronics", "0..1");
		accessories = new Variable("Accessories", "0..1");
		electronicWindows = new Variable("Electronic_windows", "0..1");
		electricSystem = new Variable("Electric_system", "0..1"); 
		electricManager=  new Variable("Electric_manager", "0..1");
		devices=  new Variable("Devices", "0..1");
		stereo = new Variable("Stereo", "0..1");
		sound = new Variable("Sound", "0..1");
		gps = new Variable("Gps", "0..1");
		type1 = new Variable("Type1", "0..1");
		type2= new Variable("Type2", "0..1");
		electricMg1 = new Variable("Electric_mg1", "0..1");
		electricMg2 = new Variable("Electric_mg2", "0..1");
		airConditioner = new Variable("Air_conditioner", "0..1");
		wipers = new Variable("Wipers", "0..1");
		
		vars.add(carElectronics);
		vars.add(accessories);
		vars.add(electronicWindows);
		vars.add(electricSystem);
		vars.add(electricManager);
		vars.add(devices);
		vars.add(stereo);
		vars.add(sound);
		vars.add(gps);
		vars.add(type1);
		vars.add(type2);
		vars.add(electricMg1);
		vars.add(electricMg2);
		vars.add(airConditioner);
		vars.add(wipers);
	}
	
	public void initConstraints(){
		//constraints from c1--c5
		c0= new Constraint("C0","Car_electronics #=1");
		c0.addVariable(carElectronics);
		
		
		c1= new Constraint("C1","Car_electronics #= Accessories");
		c1.addVariable(carElectronics);
		c1.addVariable(accessories);
		
		c2= new Constraint("C2","Car_electronics #= Electric_system");
		c2.addVariable(carElectronics);
		c2.addVariable(electricSystem);
		
		c3= new Constraint("C3","Car_electronics #>= Electric_manager");
		c3.addVariable(carElectronics);
		c3.addVariable(electricManager);
		
		c4= new Constraint("C4","Car_electronics #= Devices");
		c4.addVariable(carElectronics);
		c4.addVariable(devices);
		
		c5= new Constraint("C5","Devices #>= Air_conditioner");
		c5.addVariable(devices);
		c5.addVariable(airConditioner);
		
		
		c6= new Constraint("C6","Devices #= Wipers");
		c6.addVariable(devices);
		c6.addVariable(wipers);
		
		c7= new Constraint("C7","Devices #= Electronic_windows");
		c7.addVariable(devices);
		c7.addVariable(electronicWindows);
		
		c8= new Constraint("C8","Electronic_windows +Sound #=< 1");
		c8.addVariable(electronicWindows);
		c8.addVariable(sound);
		
		
		c9= new Constraint("C9","Electronic_windows + Electric_mg2 #=< 1");
		c9.addVariable(electronicWindows);
		c9.addVariable(electricMg2);
		

		
		c10= new Constraint("C10","Devices #==> Electric_manager");
		c10.addVariable(devices);
		c10.addVariable(electricManager);
		
		c11= new Constraint("C11","Electric_manager #>= Electric_mg1 #/\\ "
				+ "Electric_manager #>= Electric_mg2 #/\\ "
				+ "1* Electric_manager #=< Electric_mg1 + Electric_mg2 #/\\ "
				+ "Electric_mg1 + Electric_mg2 #=< 1* Electric_manager");
		
		c11.addVariable(electricManager);
		c11.addVariable(electricMg1);
		c11.addVariable(electricMg2);
		
		c12= new Constraint("C12","Electric_system #>= Type1 #/\\ "
				+ "Electric_system #>= Type2 #/\\"
				+ "1* Electric_system #=< Type1 + Type2 #/\\"
				+ "Type1 + Type2 #=< 1* Electric_system");
		
		c12.addVariable(electricSystem);
		c12.addVariable(type1);
		c12.addVariable(type2);
		
		c13= new Constraint("C13","Accessories #>= Stereo #/\\"
				+ "Accessories #>= Sound #/\\"
				+ "Accessories #>= Gps #/\\"
				+ "2* Accessories #=< Stereo + Sound + Gps #/\\"
				+ "Stereo + Sound + Gps #=< 3 * Accessories");
		
		c13.addVariable(accessories);
		c13.addVariable(stereo);
		c13.addVariable(sound);
		c13.addVariable(gps);
		
		c14= new Constraint("C14","Gps  #==> Type1");
		c14.addVariable(gps);
		c14.addVariable(type1);
		
		c15 = new Constraint("C15","Electric_mg1 #==> Type2");
		c15.addVariable(electricMg1);
		c15.addVariable(type2);
		

		//adding constraints
		cons.add(c0);
		cons.add(c1);
		cons.add(c2);
		cons.add(c3);
		cons.add(c4);
		cons.add(c5);
		cons.add(c6);
		cons.add(c7);
		cons.add(c8);
		cons.add(c9);
		cons.add(c10);
		cons.add(c11);
		cons.add(c12);
		cons.add(c13);
		cons.add(c14);
		cons.add(c15);
		

		
	}
	public  void makeCSP(){
		csp= new CSP();
		csp.setVariables(vars);
		csp.setConstraints(cons);
	}
	
	public String getStart(){
		return carElectronics.getId();
	}

}


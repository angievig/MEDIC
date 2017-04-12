package cases;

import java.util.Set;
import java.util.TreeSet;

import cspElements.CSP;
import cspElements.Constraint;
import cspElements.Variable;

public class Felferning {
	//CCP
	private CSP csp;
	//variables
	private Variable ubuntu, textEditor, bash, gui, games, gedit, vi, kde, gnome, gnuchess, glchess;
	private Constraint c0, c1, c2, c3, c4 , c5, c6, c7, c8, c9, c10;
	private TreeSet<Variable> vars=  new TreeSet<Variable>();
	private TreeSet <Constraint> cons= new TreeSet<Constraint>();
	public CSP getCSP(){
		initVariables();
		initConstraints();
		makeCSP();
		return csp;
	}
	
	public void initVariables(){
		ubuntu= new Variable("Ubuntu", "0..1");
		textEditor= new Variable("TextEditor", "0..3");
		bash= new Variable("Bash", "0..1");
		gui= new Variable("Gui", "0..1");
		games= new Variable("Games", "0..1");
		gedit= new Variable("Gedit", "0..1");
		vi= new Variable("Vi", "0..1");
		kde= new Variable("Kde", "0..1");
		gnome= new Variable("Gnome", "0..1");
		gnuchess= new Variable("Gnuchess", "0..1");
		glchess= new Variable("Glchess", "0..1");
		
		vars.add(ubuntu);
		vars.add(textEditor);
		vars.add(bash);
		vars.add(gui);
		vars.add(games);
		vars.add(gedit);
		vars.add(vi);
		vars.add(kde);
		vars.add(gnome);
		vars.add(gnuchess);
		vars.add(glchess);
	}
	
	public void initConstraints(){
		//constraints from c1--c5
		c0= new Constraint("C0","Ubuntu#= 1");
		c0.addVariable(ubuntu);
		
		
		c1= new Constraint("C1","Ubuntu#= TextEditor");
		c1.addVariable(ubuntu);
		c1.addVariable(textEditor);
		
		c2= new Constraint("C2","Ubuntu#= Bash");
		c2.addVariable(ubuntu);
		c2.addVariable(bash);
		
		c3= new Constraint("C3","Ubuntu#= Gui");
		c3.addVariable(ubuntu);
		c3.addVariable(gui);
		
		c4= new Constraint("C4","Ubuntu #>= Games");
		c4.addVariable(ubuntu);
		c4.addVariable(games);
		
		c5= new Constraint("C5","TextEditor #>= Vi #/\\ TextEditor #>= Gedit #/\\ 1*TextEditor #=< Vi+ Gedit #/\\ Vi+ Gedit #=< 2* TextEditor");
		c5.addVariable(textEditor);
		c5.addVariable(vi);
		c5.addVariable(gedit);
		
		c6= new Constraint("C6","TextEditor + Bash #=< 1");
		c6.addVariable(textEditor);
		c6.addVariable(bash);
		
		c7= new Constraint("C7","Gui + Bash #=< 1");
		c7.addVariable(gui);
		c7.addVariable(bash);
		
		c8= new Constraint("C8","Gui #>= Kde #/\\ Gui #>= Gnome #/\\ 1*Gui #=< Kde+ Gnome #/\\ Kde+ Gnome #=< 2* Gui");
		c8.addVariable(gui);
		c8.addVariable(kde);
		c8.addVariable(gnome);
		
		c9= new Constraint("C9","Games #==>  Gui");
		c9.addVariable(games);
		c9.addVariable(gui);
		
		c10= new Constraint("C10","(1 * Games) #=< (Gnuchess + Glchess) #/\\ (Gnuchess + Glchess) #=< (1 * Games)");
		c10.addVariable(games);
		c10.addVariable(gnuchess);
		c10.addVariable(glchess);

		//adding constraints
		cons.add(c0);
		cons.add(c1);
		cons.add(c2);
		cons.add(c3);
		cons.add(c4);
		cons.add(c5);
		//cons.add(c6);
		cons.add(c7);
		cons.add(c8);
		cons.add(c9);
		cons.add(c10);
		

		
	}
	public  void makeCSP(){
		csp= new CSP();
		csp.setVariables(vars);
		csp.setConstraints(cons);
	}
	
	public String getStart(){
		return ubuntu.getId();
	}

}

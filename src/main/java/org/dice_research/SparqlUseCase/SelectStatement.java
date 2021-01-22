package org.dice_research.SparqlUseCase;

import java.util.HashMap;
import java.util.Map;


public class SelectStatement implements Statement{
	
	Map<String, String> variables;
	
	public SelectStatement() {
		this.variables = new HashMap<String, String>();
	}
	
	public String toString() {
		String res="";
		for(Map.Entry<String, String> e: variables.entrySet()) {
			res+=e.getKey()+" -> "+e.getValue()+"\n";
		}
		return res;
	}
	
	public String getType(){
		return "SELECT";
	}
	
	public boolean putVariable(String original, String renamed) {
		if(variables != null) {
			variables.put(original, renamed);
			return true;
		}
		return false;
	}
	
	public Map<String, String> getVariables(){
		return variables;
	}
}
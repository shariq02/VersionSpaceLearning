package org.dice_research.SparqlUseCase;

import java.util.Map;

public interface Statement {
	
	public String getType();
	public String toString();
	public Map<String, String> getVariables();
	public boolean putVariable(String o, String r);
}
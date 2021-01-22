package org.dice_research.SparqlUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SPARQLQueryParser{
	/*
	 * Removes all the white space and all the lines in the input query that start with "PREFIX".
	 * All the mappings from the prefixes to their respective values are saved in the prefixes
	 * arraylist in the query.
	 * */
	public static String removePrefixes(Query query){
		String toReturn = "";
		//remove all newlines and unnecessary whitespace from the query
		query.setQuery(query.getOriginalQuery().replaceAll("\\s{2,}", " ").trim());
		String[] tmp = query.getOriginalQuery().split(" ");
		int i=0;
		while(i<tmp.length){
			//if this holds we know that the next two words will be the prefix variable and uri
			if(tmp[i].toLowerCase().equals("prefix")){
				String v = tmp[i+1].substring(0,tmp[i+1].length()-1);
				String u = tmp[i+2].substring(1,tmp[i+2].length()-1);
				query.prefixes.put(v,u);
				i+=3;
			}
			else{
				//return the query without the prefixes
				for(int j =i;j<tmp.length;j++) {
					toReturn = toReturn+tmp[j]+" ";
				}
				break;
			}
		}
		return toReturn;
	}
	
	/*
	 * Parses the input query
	 * */
	public static void parse(Query query){
		query.setQuery(removePrefixes(query));
		String[] tmp = query.getOriginalQuery().trim().split(" ");

		boolean searchForTriples = false;
		for(int i=0;i<tmp.length;i++) {
			Statement s;
			//if next word is a SELECT statement add and rename all variables present in the statement to the vars map
			if(tmp[i].equals("SELECT")) {
				i++;
				s = new SelectStatement();
				while(!tmp[i].equals("{")) {
					if(tmp[i].equals("*")) {
						((SelectStatement)s).putVariable(tmp[i], "*");
					}
					else if(tmp[i].startsWith("?")){
						((SelectStatement)s).putVariable(tmp[i], "?v"+query.j);
						query.j++;
					}
					i++;
				}
				query.statements.add(s);
			} 
			//parsing other types of queries apart form SELECT queries is not supported
			else if(tmp[i].equals("ASK")) {
				s = new Statement() {
					public String getType() {
						return "ASK";
					}
				};
				query.statements.add(s);
				break;
			} else if(tmp[i].equals("DESCRIBE")) {
				s = new Statement() {
					public String getType() {
						return "DESCRIBE";
					}
				};
				query.statements.add(s);
				break;
				
			} else if(tmp[i].equals("CONSTRUCT")) {
				s = new Statement() {
					public String getType() {
						return "CONSTRUCT";
					}
				};
				query.statements.add(s);
				break;
			}
			
			if(searchForTriples) {
				if(tmp[i].equals("}")) {
					//nothing, empty brackets
				}
				else if(tmp[i+3].equals(".") || tmp[i+3].equals("}") || tmp[i+3].equals("{") || Query.reservedWords.contains(tmp[i+3])) {
					query.triples.add(new Triple(tmp[i], tmp[i + 1], tmp[i + 2]));
					i += 3;
				}
				else if(tmp[i].equals("{")) {
					//embedded brackets
				}
				else {
					System.out.println("Some kind of error happened while parsing: " + tmp[i-1] + " " +tmp[i] + " " +tmp[i+1]+ " " +tmp[i+2]);
				}
				
				if(!tmp[i].equals(".")) {
						searchForTriples = false;
				}
				
			}
			else {
				if(tmp[i].equals("{") && !Query.reservedWords.contains(tmp[i+1])) {
					searchForTriples = true;
				}
				
				if((i<tmp.length-1) && tmp[i+1].startsWith("?")){
					searchForTriples = true;
				}
			}
		}
		
		query.renameVariables(query.statements, query.triples);
		query.replacePrefixVariables();
	}
}
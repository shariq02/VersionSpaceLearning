package org.dice_research.vspace;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hierarchy {

	// map of parents to children
	Map<String, List<String>> ds;

	public Hierarchy() {
		ds = new HashMap<String, List<String>>();
	}

	void printData() {
		ds.forEach((key, children) -> {
			System.out.print(key + " -> ");
			children.forEach((child) -> {
				System.out.print(child + " ");
			});
			System.out.println();
		});
	}

	// read hieachy information from file
	void readData(File f) {
		List<String> data = new ArrayList<String>();
		if (f.exists()) {
			try {
				data = Files.readAllLines(f.toPath(), Charset.defaultCharset());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("File doesn't exist");
		}

		for (String line : data) {
			String[] splitData = line.split(",");
			List<String> children;
			if (!ds.containsKey(splitData[0])) {
				children = new ArrayList<String>();
				children.add(splitData[1]);
				ds.put(splitData[0], children);
			} else {
				ds.get(splitData[0]).add(splitData[1]);
			}

		}
	}

	// get the child values of a feature value
	String[] getChildren(String s) {
		if (ds.containsKey(s)) {
			String[] res = new String[ds.get(s).size()];
			ds.get(s).toArray(res);
			return res;
		}
		return null;
	}

	// get the parent value of a feature value
	String getParent(String s) {
		for (Map.Entry<String, List<String>> value : ds.entrySet()) {
			if (value.getValue().contains(s)) {
				return value.getKey();
			}
		}
		return null;
	}
}

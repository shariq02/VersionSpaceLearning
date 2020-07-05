package org.dice_research.vspace;

import java.util.HashSet;

/*
 * This class will create the Acyclic Hierarchical structure 
 * 
 * @author Susmita Goswami
 */

public class Ontology {
	private Vertices root;

	public Ontology() {
		root = new Vertices("?");
	}

	public Vertices getRoot() {
		return root;
	}

	public void addSucc(Vertices v, String value) {
		v.addChild(value);
	}

	public Vertices search(Vertices v, String value) {
		Vertices result = null;
		if (v.getValue().equals(value))
			result = v;
		else
			for (Vertices child : v.getChild()) {
				result = search(child, value);
				if (result != null)
					break;
			}

		return result;
	}

	public Vertices findCommonParent(String featureVal, String dataV) {
		Vertices result = this.root, oldResult = this.root;
		HashSet<Vertices> children = null;
		boolean loop = true;
		Vertices hypNode = this.search(root, featureVal);
		Vertices dataNode = this.search(root, dataV);

		if (this.search(hypNode, dataV) != null)
			return hypNode;
		if (this.search(dataNode, featureVal) != null)
			return dataNode;

		while (loop) {
			oldResult = result;
			children = result.getChild();
			for (Vertices child : children) {
				if (this.search(child, featureVal) != null && this.search(child, dataV) != null) {
					result = child;
					break;
				}
			}
			if (result == oldResult)
				break;
		}

		return result;
	}

	public void addStopper(Vertices v) {
		if (v.getChild().isEmpty())
			v.addChild("-");
		else {
			for (Vertices child : v.getChild())
				addStopper(child);
		}
	}
}

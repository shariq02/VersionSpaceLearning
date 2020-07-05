package org.dice_research.vspace;

import java.util.HashSet;

public class Vertices
{
    private Vertices parent;
    private HashSet<Vertices> child;
    private String value;

    public Vertices(String value)
    {
        this.value = value;
        this.parent = null;
        this.child = new HashSet<>();
    }

    public Vertices getParent() {
        return parent;
    }

    public String getValue() {
        return value;
    }

    public void setParent(Vertices parent) {
        this.parent = parent;
    }

    public void addChild(String value) {
        Vertices succ = new Vertices(value);
        succ.setParent(this);
        this.child.add(succ);
    }

    public HashSet<Vertices> getChild() {
        return child;
    }
}
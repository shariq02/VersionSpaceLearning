package org.dice_research.vspace;

import java.util.HashSet;

public class VersionSpace
{
    public HashSet<Hypothesis> S ;
    public HashSet<Hypothesis> G ;

    public HashSet<Hypothesis> getS() {
        return S;
    }

    public void setS(HashSet<Hypothesis> s) {
        S = s;
    }

    public void setG(HashSet<Hypothesis> g) {
        G = g;
    }

    public HashSet<Hypothesis> getG() {
        return G;
    }
    public VersionSpace(HashSet<Hypothesis> s,HashSet<Hypothesis> g )
    {
        this.setS(s);
        this.setG(g);
    }
}

package projectwork;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class SpecializeGBoundary {

    public static final String ANY= "?";
    public static final String NONE = "-";

    public HashSet<Hypothesis> specialize(String[] ne, HashSet<Hypothesis> s, ArrayList<ArrayList<String>> f_pssibleValues, Hypothesis k)
    {
        HashSet<Hypothesis> spGList = new HashSet<Hypothesis>();
        String[] base = new String[k.features.length];
        Iterator iter;
        String value;
        // IF our general hypothesis is already consistent with -ve example , no need to specialize it
        if (k.isConsistentWithDataPoint(ne, false)) return spGList;

        /* If not then we ned to create multiple more specific hypothesis
         *  1) For each possible value of each feature the function will create a specialize hypothesis
         *  2) The after creation of the complete list, it will check which hypotheses are more than than at least one of the
         *       hypothesis is s. it will remove others.
         *  3) in order to achieve this all features of the -ve data point is matched with this hypothesis features.
         *        -- if this hypothesis feature is ANY if can specialize it
         *        -- if this hypothesis feature is same as data then we need to skip and look for other features to generalize
         *  4) Finally if this function is able to generate any specialize hypothesis if will return with a arralist of hypotheses */
        for(int i= 0; i< ne.length ; i++)
        {
            if (k.features[i].equals(ANY))
            {
                iter = f_pssibleValues.get(i).iterator();
                while(iter.hasNext())
                {
                    value = iter.next().toString();
                    if (value.equals(ne[i])) continue;
                    for (int j = 0 ; j < k.features.length ; j++)
                    {
                        base[j] = k.features[j].toString();
                    }
                    base[i] = value;
                    spGList.add(new Hypothesis(base));
                }
            }
        }
        iter = spGList.iterator();
        while(iter.hasNext())
        {
            Hypothesis check = ((Hypothesis) iter.next());
            if (!check.isConsistentWithDataPoint(ne, false))
            {
                iter.remove();
                continue;
            }
            int counter = 0;
            for (Hypothesis h : s)
            {
                if(check.isMoreGeneralThan(h))
                {
                    counter++;
                    break;
                }
            }
            if (counter == 0) iter.remove();
        }

        return spGList;
    }

}
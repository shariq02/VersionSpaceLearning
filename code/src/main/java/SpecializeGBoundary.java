import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class SpecializeGBoundary {

    public static final String ANY= "?";
    public static final String NONE = "-";

    public HashSet<Hypothesis> specialize(String[] negativeData, HashSet<Hypothesis> specializedHypotheses, ArrayList<HashSet<String>> possibleFeatureValues,  HashSet<Hypothesis> generalizedHypotheses)
    {
        HashSet<Hypothesis> spGList = new HashSet<Hypothesis>();
        Iterator iter;
        for (Hypothesis h : generalizedHypotheses) {
            String[] base = new String[h.features.length];
            String value;

            // IF our general hypothesis is already consistent with -ve example , no need to specialize it
            if (h.isConsistentWithDataPoint(negativeData, false)) {
                spGList.add(h);
                continue;
            }

            /* If not then we ned to create multiple more specific hypothesis
             *  1) For each possible value of each feature the function will create a specialize hypothesis
             *  2) The after creation of the complete list, it will check which hypotheses are more than than at least one of the
             *       hypothesis is s. it will remove others.
             *  3) in order to achieve this all features of the -ve data point is matched with this hypothesis features.
             *        -- if this hypothesis feature is ANY if can specialize it
             *        -- if this hypothesis feature is same as data then we need to skip and look for other features to generalize
             *  4) Finally if this function is able to generate any specialize hypothesis if will return with a arralist of hypotheses */
            for (int i = 0; i < negativeData.length; i++) {
                if (h.features[i].equals(ANY)||h.features[i].equals(negativeData[i])) {
                    iter = possibleFeatureValues.get(i).iterator();
                    while (iter.hasNext()) {
                        value = iter.next().toString();
                        if (value.equals(negativeData[i])) continue;
                        for (int j = 0; j < h.features.length; j++) {
                            base[j] = h.features[j].toString();
                        }
                        base[i] = value;
                        spGList.add(new Hypothesis(base));
                    }
                }
            }
        }
        iter = spGList.iterator();
        while(iter.hasNext())
        {
            Hypothesis check = ((Hypothesis) iter.next());
            if (!check.isConsistentWithDataPoint(negativeData, false))
            {
                iter.remove();
                continue;
            }
            int counter = 0;
            for (Hypothesis specializedHyp : specializedHypotheses)
            {
                if(check.isMoreGeneralThan(specializedHyp))
                {
                    counter++;
                    break;
                }
            }
            if (counter == 0) iter.remove();
        }

        return spGList;
    }

    public HashSet<Hypothesis> removeMember(HashSet<Hypothesis> specializedHypotheses, HashSet<Hypothesis> generalizedHypotheses)
    {
        HashSet<Hypothesis> spGList = new HashSet<Hypothesis>();
        for (Hypothesis generalizedHyp: generalizedHypotheses)
        {
            int counter = 0;
            for (Hypothesis specializedHyp : specializedHypotheses)
            {
                if(generalizedHyp.isMoreGeneralThan(specializedHyp))
                {
                    counter++;
                    break;
                }
            }
            if (counter != 0) spGList.add(h);
        }
        return spGList;
    }


}
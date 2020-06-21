import java.lang.reflect.Array;
import java.util.Arrays;

public class Instance {
    private String[] attribs;
    private String label;

    public Instance(String[] rawData)
    {
        this.attribs = new String[rawData.length];
        this.attribs = Arrays.copyOf(rawData,rawData.length -1);
        label = (String) Array.get(rawData,rawData.length -1);
    }

    public String getLabel() {
        return label;
    }

    public String[] getAttribs() {
        return attribs;
    }

    public String toString()
    {
        String result = "< ";
        for(String s: this.attribs) {
            result+=s+", ";
        }
        result += this.label + " >";
        return result;
    }
}

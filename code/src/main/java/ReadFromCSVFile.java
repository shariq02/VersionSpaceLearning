import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;


/**
* Here, csvReader() method is trying to read the ".csv" file and storing it to Arraylist of Instance class
* 
* 
* @author  Md Sharique 
*/

public class ReadFromCSVFile 
{
	
	public static ArrayList<Instance> csvReader() throws IOException 
	{
		GeneralizeS genS = new GeneralizeS();
        SpecializeGBoundary spclG = new SpecializeGBoundary();
        BufferedReader br;
        String line = "";
		String xfileLocation = "";
		int index = 0;
		int datalen = 0;
		String fileName1 = "";
		String[] datas;
		ArrayList<Instance> instances = new ArrayList<>();
		ArrayList<HashSet<String>> featureValues = new ArrayList<>();
        new GeneralizeS();
        new SpecializeGBoundary();
        HashSet<Hypothesis> S = new HashSet<>();
        HashSet<Hypothesis> G = new HashSet<>();
		List<String> pathOfFile = new ArrayList<String>();
		pathOfFile = InputFileExtension.pathFile();   //file path is returned from InputFileExtension.pathFile() method.
		ListIterator<String> Itr = pathOfFile.listIterator();	
		String fileName = "";
		while(Itr.hasNext())
		{
			fileName = Itr.next();
			if (fileName.endsWith(".csv"))
			{
				//At present this condition is having a bug, it will work only if csv file is latest, if any other files 
				//is there latest, it will throw an error. Working on it.
				if (fileName.equals(FileUtils.pickLatestFile(".\\src\\test\\resources\\datafile")))
				{
					xfileLocation = fileName; //storing the file path of ".csv" file
					//System.out.println("xfileLocation "+xfileLocation);
				}
				index = xfileLocation.lastIndexOf("\\");
			}
		}
		br = new BufferedReader(new FileReader(new File(xfileLocation)));
		while ((line = br.readLine()) != null)
	    {
			datas = line.split(",");
            System.out.println("line: "+line);
	    	instances.add(new Instance(datas));
	    	if (datalen == 0)
            {
                datalen = datas.length -1;
                S.add(new Hypothesis(datalen,"S"));
                G.add(new Hypothesis(datalen,"G"));
                for (int i = 1; i <= datalen; i++)
                {
                    featureValues.add(new HashSet<>());
                }
            }
            for (int i = 0; i < datalen ; i ++)
            {
                featureValues.get(i).add(datas[i]);
            }
	    }	
	    br.close();
	    for(Instance inst: instances)
        {
            if(inst.getLabel().equals("Yes"))
            {
                S = genS.min_generalizations(S, inst.getAttribs());
            }
            else 
            {
            	G = spclG.specialize(inst.getAttribs(), S, featureValues, G);
            }
        }
        System.out.println("S boundary is:");
        System.out.println(S);
        System.out.println("G boundary is:");
        System.out.println(G);
	
	    fileName1 = xfileLocation.substring(index + 1);
	    FileUtils.filemovetoanotherfolder(".\\src\\test\\resources\\datafile\\", ".\\src\\test\\resources\\archivefile\\", fileName1);
	    return instances;
	}
}

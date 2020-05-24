import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SpecializeGBoundaryTest {

    @Test
    public void specialize() {
        ArrayList<String> f1 = new ArrayList<>();
        ArrayList<String> f2 = new ArrayList<>();
        ArrayList<String> f3 = new ArrayList<>();
        ArrayList<Hypothesis> gList;
        SpecializeGBoundary sg = new SpecializeGBoundary();

        f1.add("Mango");f1.add("Orange");
        f2.add("Yes");f2.add("No");f2.add("Maybe");
        f3.add("OK");f3.add("Good");f3.add("Bad");


        String[] nData = {"Orange","Maybe","OK"};

        ArrayList fList = new ArrayList();
        fList.add(f1);fList.add(f2);fList.add(f3);
        Hypothesis testHyp = new Hypothesis(3,"G");
        Hypothesis testHypS = new Hypothesis(3,"S");
        Hypothesis[] s = new Hypothesis[1];
        s[0] = testHypS;
        gList= sg.specialize(nData, s, fList, testHyp);


        String[] f11 =  {"Mango","?","?"};
        String[] f21 =  {"?","Yes","?"};
        String[] f31 =  {"?","No","?"};
        String[] f41 =  {"?","?","Good"};
        String[] f51 =  {"?","?","Bad"};


        ArrayList expOut = new ArrayList();

        expOut.add(new Hypothesis(f11));expOut.add(new Hypothesis(f21));expOut.add(new Hypothesis(f31));expOut.add(new Hypothesis(f41));expOut.add(new Hypothesis(f51));

        assertEquals(expOut +" " , gList +" ");
    }



    @Test
    public void specialize1() {
        ArrayList<String> f1 = new ArrayList<>();
        ArrayList<String> f2 = new ArrayList<>();
        ArrayList<String> f3 = new ArrayList<>();
        ArrayList<Hypothesis> gList;
        SpecializeGBoundary sg = new SpecializeGBoundary();

        f1.add("Mango");f1.add("Orange");
        f2.add("Yes");f2.add("No");f2.add("Maybe");
        f3.add("OK");f3.add("Good");f3.add("Bad");


        String[] nData = {"Orange","Maybe","OK"};

        ArrayList fList = new ArrayList();
        fList.add(f1);fList.add(f2);fList.add(f3);
        String[] f = {"?", "Maybe","?"};
        Hypothesis testHyp = new Hypothesis(f);

        Hypothesis testHypS = new Hypothesis(3,"S");
        Hypothesis[] s = new Hypothesis[1];
        s[0] = testHypS;
        gList= sg.specialize(nData, s, fList, testHyp);

        String[] f11 =  {"Mango","Maybe","?"};
        String[] f21 =  {"?","Yes","?"};
        String[] f31 =  {"?","No","?"};
        String[] f41 =  {"?","Maybe","Good"};
        String[] f51 =  {"?","Maybe","Bad"};


        ArrayList expOut = new ArrayList();

        expOut.add(new Hypothesis(f11));expOut.add(new Hypothesis(f21));expOut.add(new Hypothesis(f31));expOut.add(new Hypothesis(f41));expOut.add(new Hypothesis(f51));

        assertEquals(expOut +" " , gList +" ");
    }


    @Test
    public void specialize2() {
        ArrayList<String> f1 = new ArrayList<>();
        ArrayList<String> f2 = new ArrayList<>();
        ArrayList<String> f3 = new ArrayList<>();
        ArrayList<Hypothesis> gList;
        SpecializeGBoundary sg = new SpecializeGBoundary();

        f1.add("Mango");f1.add("Orange");
        f2.add("Yes");f2.add("No");f2.add("Maybe");
        f3.add("OK");f3.add("Good");f3.add("Bad");


        String[] nData = {"Orange","Maybe","OK"};

        ArrayList fList = new ArrayList();
        fList.add(f1);fList.add(f2);fList.add(f3);
        String[] f = {"?", "?","OK"};
        Hypothesis testHyp = new Hypothesis(f);

        Hypothesis testHypS = new Hypothesis(3,"S");
        Hypothesis[] s = new Hypothesis[1];
        s[0] = testHypS;
        gList= sg.specialize(nData, s, fList, testHyp);

        String[] f11 =  {"Mango","?","OK"};
        String[] f21 =  {"?","Yes","OK"};
        String[] f31 =  {"?","No","OK"};
        String[] f41 =  {"?","?","Good"};
        String[] f51 =  {"?","?","Bad"};


        ArrayList expOut = new ArrayList();

        expOut.add(new Hypothesis(f11));expOut.add(new Hypothesis(f21));expOut.add(new Hypothesis(f31));expOut.add(new Hypothesis(f41));expOut.add(new Hypothesis(f51));

        assertEquals(expOut +" " , gList +" ");
    }
    @Test
    public void specialize3() {
        ArrayList<String> f1 = new ArrayList<>();
        ArrayList<String> f2 = new ArrayList<>();
        ArrayList<String> f3 = new ArrayList<>();
        ArrayList<Hypothesis> gList;
        SpecializeGBoundary sg = new SpecializeGBoundary();

        f1.add("Mango");f1.add("Orange");
        f2.add("Yes");f2.add("No");f2.add("Maybe");
        f3.add("OK");f3.add("Good");f3.add("Bad");


        String[] nData = {"Orange","Maybe","OK"};

        ArrayList fList = new ArrayList();
        fList.add(f1);fList.add(f2);fList.add(f3);
        String[] f = {"Orange", "?","?"};
        Hypothesis testHyp = new Hypothesis(f);

        Hypothesis testHypS = new Hypothesis(3,"S");
        Hypothesis[] s = new Hypothesis[1];
        s[0] = testHypS;
        gList= sg.specialize(nData, s, fList, testHyp);

        String[] f11 =  {"Mango","?","?"};
        String[] f21 =  {"Orange","Yes","?"};
        String[] f31 =  {"Orange","No","?"};
        String[] f41 =  {"Orange","?","Good"};
        String[] f51 =  {"Orange","?","Bad"};


        ArrayList expOut = new ArrayList();

        expOut.add(new Hypothesis(f11));expOut.add(new Hypothesis(f21));expOut.add(new Hypothesis(f31));expOut.add(new Hypothesis(f41));expOut.add(new Hypothesis(f51));

        assertEquals(expOut +" " , gList +" ");
    }
    @Test
    public void specialize4() {
        ArrayList<String> f1 = new ArrayList<>();
        ArrayList<String> f2 = new ArrayList<>();
        ArrayList<String> f3 = new ArrayList<>();
        ArrayList<Hypothesis> gList;
        SpecializeGBoundary sg = new SpecializeGBoundary();

        f1.add("Mango");f1.add("Orange");
        f2.add("Yes");f2.add("No");f2.add("Maybe");
        f3.add("OK");f3.add("Good");f3.add("Bad");


        String[] nData = {"Orange","Maybe","OK"};

        ArrayList fList = new ArrayList();
        fList.add(f1);fList.add(f2);fList.add(f3);
        String[] f = {"Orange", "?","OK"};
        Hypothesis testHyp = new Hypothesis(f);

        Hypothesis testHypS = new Hypothesis(3,"S");
        Hypothesis[] s = new Hypothesis[1];
        s[0] = testHypS;
        gList= sg.specialize(nData, s, fList, testHyp);

        String[] f11 =  {"Mango","?","OK"};
        String[] f21 =  {"Orange","Yes","OK"};
        String[] f31 =  {"Orange","No","OK"};
        String[] f41 =  {"Orange","?","Good"};
        String[] f51 =  {"Orange","?","Bad"};


        ArrayList expOut = new ArrayList();

        expOut.add(new Hypothesis(f11));expOut.add(new Hypothesis(f21));expOut.add(new Hypothesis(f31));expOut.add(new Hypothesis(f41));expOut.add(new Hypothesis(f51));

        assertEquals(expOut +" " , gList +" ");
    }
    @Test
    public void specialize5() {
        ArrayList<String> f1 = new ArrayList<>();
        ArrayList<String> f2 = new ArrayList<>();
        ArrayList<String> f3 = new ArrayList<>();
        ArrayList<Hypothesis> gList;
        SpecializeGBoundary sg = new SpecializeGBoundary();

        f1.add("Mango");f1.add("Orange");
        f2.add("Yes");f2.add("No");f2.add("Maybe");
        f3.add("OK");f3.add("Good");f3.add("Bad");


        String[] nData = {"Orange","Maybe","OK"};

        ArrayList fList = new ArrayList();
        fList.add(f1);fList.add(f2);fList.add(f3);
        String[] f = {"Orange", "?","?"};
        Hypothesis testHyp = new Hypothesis(f);

        Hypothesis testHypS = new Hypothesis(3,"S");
        Hypothesis[] s = new Hypothesis[1];
        s[0] = testHypS;
        gList= sg.specialize(nData, s, fList, testHyp);

        String[] f11 =  {"Mango","?","?"};
        String[] f21 =  {"Orange","Yes","?"};
        String[] f31 =  {"Orange","No","?"};
        String[] f41 =  {"Orange","?","Good"};
        String[] f51 =  {"Orange","?","Bad"};


        ArrayList expOut = new ArrayList();

        expOut.add(new Hypothesis(f11));expOut.add(new Hypothesis(f21));expOut.add(new Hypothesis(f31));expOut.add(new Hypothesis(f41));expOut.add(new Hypothesis(f51));

        assertEquals(expOut +" " , gList +" ");
    }

    @Test
    public void specialize6() {
        ArrayList<String> f1 = new ArrayList<>();
        ArrayList<String> f2 = new ArrayList<>();
        ArrayList<String> f3 = new ArrayList<>();
        ArrayList<Hypothesis> gList;
        SpecializeGBoundary sg = new SpecializeGBoundary();

        f1.add("Mango");f1.add("Orange");
        f2.add("Yes");f2.add("No");f2.add("Maybe");
        f3.add("OK");f3.add("Good");f3.add("Bad");


        String[] nData = {"Orange","Maybe","OK"};

        ArrayList fList = new ArrayList();
        fList.add(f1);fList.add(f2);fList.add(f3);
        String[] f = {"?", "?","OK"};
        Hypothesis testHyp = new Hypothesis(f);

        Hypothesis testHypS = new Hypothesis(3,"S");
        Hypothesis[] s = new Hypothesis[1];
        s[0] = testHypS;
        gList= sg.specialize(nData, s, fList, testHyp);


        String[] f11 =  {"Mango","?","OK"};
        String[] f21 =  {"?","Yes","OK"};
        String[] f31 =  {"?","No","OK"};
        String[] f41 =  {"?","?","Good"};
        String[] f51 =  {"?","?","Bad"};


        ArrayList expOut = new ArrayList();

        expOut.add(new Hypothesis(f11));expOut.add(new Hypothesis(f21));expOut.add(new Hypothesis(f31));expOut.add(new Hypothesis(f41));expOut.add(new Hypothesis(f51));

        assertEquals(expOut +" " , gList +" ");
    }


    @Test
    public void specialize7() {
        ArrayList<String> f1 = new ArrayList<>();
        ArrayList<String> f2 = new ArrayList<>();
        ArrayList<String> f3 = new ArrayList<>();
        ArrayList<Hypothesis> gList;
        SpecializeGBoundary sg = new SpecializeGBoundary();

        f1.add("Mango");f1.add("Orange");
        f2.add("Yes");f2.add("No");f2.add("Maybe");
        f3.add("OK");f3.add("Good");f3.add("Bad");


        String[] nData = {"Orange","Maybe","OK"};

        ArrayList fList = new ArrayList();
        fList.add(f1);fList.add(f2);fList.add(f3);
        String[] f = {"?","?","OK"};
        Hypothesis testHyp = new Hypothesis(f);
        String[] ths = {"Mango","Yes","OK"};
        Hypothesis testHypS = new Hypothesis(ths);
        Hypothesis[] s = new Hypothesis[1];
        s[0] = testHypS;
        gList= sg.specialize(nData, s, fList, testHyp);

        String[] f11 =  {"Mango","?","OK"};
        String[] f21 =  {"?","Yes","OK"};



        ArrayList expOut = new ArrayList();

        expOut.add(new Hypothesis(f11));expOut.add(new Hypothesis(f21));

        assertEquals(expOut +" " , gList +" ");
    }
    @Test
    public void specialize8() {
        ArrayList<String> f1 = new ArrayList<>();
        ArrayList<String> f2 = new ArrayList<>();
        ArrayList<String> f3 = new ArrayList<>();
        ArrayList<Hypothesis> gList;
        SpecializeGBoundary sg = new SpecializeGBoundary();

        f1.add("Mango");f1.add("Orange");
        f2.add("Yes");f2.add("No");f2.add("Maybe");
        f3.add("OK");f3.add("Good");f3.add("Bad");


        String[] nData = {"Orange","Maybe","OK"};

        ArrayList fList = new ArrayList();
        fList.add(f1);fList.add(f2);fList.add(f3);
        String[] f = {"?","?","?"};
        Hypothesis testHyp = new Hypothesis(f);
        String[] ths = {"Mango","Yes","-"};
        Hypothesis testHypS = new Hypothesis(ths);
        Hypothesis[] s = new Hypothesis[1];
        s[0] = testHypS;
        gList= sg.specialize(nData, s, fList, testHyp);

        String[] f11 =  {"Mango","?","?"};
        String[] f21 =  {"?","Yes","?"};
        String[] f31 =  {"?","?","Good"};
        String[] f41 =  {"?","?","Bad"};



        ArrayList expOut = new ArrayList();

        expOut.add(new Hypothesis(f11));expOut.add(new Hypothesis(f21));expOut.add(new Hypothesis(f31));expOut.add(new Hypothesis(f41));

        assertEquals(expOut +" " , gList +" ");
    }

}
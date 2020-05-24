//public class SpecializeGTesting {
/*
    public void test1(){
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
        System.out.println(gList.size());
        for (Hypothesis h : gList) System.out.println(h.toString());
    }


    public void test2(){
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
        System.out.println(gList.size());
        for (Hypothesis h : gList) System.out.println(h.toString());
    }
}
    }*/    /*
}
    ArrayList<String> f1 = new ArrayList<>();
    ArrayList<String> f2 = new ArrayList<>();
    ArrayList<String> f3 = new ArrayList<>();
    ArrayList<String> f4 = new ArrayList<>();
    ArrayList<String> f5 = new ArrayList<>();
    ArrayList<String> f6 = new ArrayList<>();
    ArrayList<Hypothesis> gList;
    SpecializeGBoundary sg = new SpecializeGBoundary();

        f1.add("Sunny");f1.add("Rainy");f1.add("Cloudy");
                f2.add("Warm");f2.add("Cold");
                f3.add("Normal");f3.add("High");
                f4.add("Strong");f4.add("Weak");
                f5.add("Warm");f5.add("Cool");
                f6.add("Same");f6.add("Change");



                String[] nData = {"Rainy","Cold","High","Strong","Warm","Change"};

                ArrayList fList = new ArrayList();
                fList.add(f1);fList.add(f2);fList.add(f3);
                String[] f = {"?", "Maybe","?"};
                Hypothesis testHyp = new Hypothesis(f);
                Hypothesis testHypS = new Hypothesis(3,"S");
                Hypothesis[] s = new Hypothesis[1];
                s[0] = testHypS;
                gList= sg.specialize(nData, s, fList, testHyp);



                //System.out.println(gList.size());
                //for (Hypothesis h : gList) System.out.println(h.toString());

        /*ArrayList<String> f11 = new ArrayList<>();
        ArrayList<String> f21 = new ArrayList<>();
        ArrayList<String> f31 = new ArrayList<>();

        f11.add("Mango");f11.add("Maybe");f11.add("?");
        f21.add("?");f21.add("Maybe");f21.add("Good");
        f31.add("?");f31.add("Maybe");f31.add("Bad");*/

              /*  String[] f11 =  {"Mango","Maybe","?"};
                String[] f21 =  {"?","Maybe","Good"};
                String[] f31 =  {"?","Maybe","Bad"};


                ArrayList expOut = new ArrayList();

                expOut.add(new Hypothesis(f11));expOut.add(new Hypothesis(f21));expOut.add(new Hypothesis(f31));

                assertEquals(expOut +" " , gList +" ");
                } */

package btp500;

import btp500.a2.Dikuho;

public class A2 {

    public static void main(String[] args) {
        Dikuho dikuho = new Dikuho();
//        dikuho.initTestGame1();
//        dikuho.initTestGame2();
//        dikuho.initTestGame3();
//        dikuho.initTestGame4();
//        dikuho.initTestGame5();
        dikuho.initTestGame6();
//        dikuho.initTestGame7();
//        dikuho.initTestGame8();
        long begin = System.currentTimeMillis();
        dikuho.solve();
        System.out.println("Time taken in milli " + (System.currentTimeMillis() - begin));

        System.out.println("\n\nDONE!!");
    }
}

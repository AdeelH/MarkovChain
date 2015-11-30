package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        long t1 = System.currentTimeMillis();
        Scanner in = new Scanner(System.in);
        try
        {
            MarkovWordChain mc1 = new MarkovWordChain(1, new File("texts/HPADH.txt"));
            MarkovWordChain mc2 = new MarkovWordChain(1, new File("texts/HPATHBP.txt"));
            System.out.printf("\n\nSimilarity: %f %%\n\n", MarkovWordChain.compare(mc1, mc2));
            System.out.printf("\n\n%d\n\n", System.currentTimeMillis() - t1);
//            do
//            {
//                mc.generate(200);
//            }
//            while (!in.next().equalsIgnoreCase("x"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

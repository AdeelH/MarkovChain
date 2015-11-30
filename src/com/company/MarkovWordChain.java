package com.company;

import sun.misc.Regexp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class MarkovWordChain
{
    final static int MAXLEN = 500000;
    public static final String NONWORD = "\n";
    HashMap<Prefix, SuffixList> prefixes;
    Prefix[] prefs;
    int prefLen;

    public MarkovWordChain(int len, File f) throws IOException
    {
        prefLen = len;
        this.prefixes = new HashMap<>(10000);
        buildFromFile(f, len);
        prefs = new Prefix[prefixes.size()];
        prefs = prefixes.keySet().toArray(prefs);
    }

    private void buildFromFile(final File file, int len) throws IOException
    {
        if (file.isDirectory())
        {
            File[] files = file.listFiles();
            if (files == null) return;

            for (File f : files)
            {
                if (!f.isFile()) continue;
                buildChain(f, len);
            }
            for (SuffixList sl : prefixes.values())
            {
                sl.optimize();
            }
        }
        else if (file.isFile())
        {
            buildChain(file, len);
        }
        System.out.printf("%d\n", prefixes.size());
    }

    private void buildChain(File f, int len) throws FileNotFoundException
    {
        try (Scanner in = new Scanner(new FileInputStream(f),"UTF-8").useDelimiter("\\n|\\s+|--+|\\*+|\".*\"|\\(.*\\)|\\[.*\\]|[a-z]\\.(?:[a-z]\\.)+"))
        {
            Prefix curr = new Prefix(len);
            String w;
            while (prefixes.size() < MAXLEN && in.hasNext())
            {
                w = in.next().trim().toLowerCase();
                if (w.length() == 1 && !(w.charAt(0) >= 'a' && w.charAt(0) <= 'z')) continue;
                if (w.matches("[a-z]+\\-+")) w = w.replace("\\-+", "") + in.next();
                if (!prefixes.containsKey(curr))
                    prefixes.put(curr, new SuffixList());
                prefixes.get(curr).add(w);
                curr = new Prefix(curr, w);
            }
            if (!prefixes.containsKey(curr))
                prefixes.put(curr, new SuffixList());
        }
    }

    public void generate(int nwords)
    {
        Prefix prefix;
        while ((prefix = prefs[(int)(Math.random()*prefs.length)]).toString().matches(".*[a-z]+\\..*"));

        boolean incomplete = true;
        String suf;
        System.out.printf("\nGenerated Text:\n");
        System.out.printf("%s ", prefix);
        for (int i = prefLen; i <= nwords || incomplete; i++)
        {
            suf = prefixes.get(prefix).random();
            if (suf.equals(NONWORD))
                break;
            incomplete = suf.lastIndexOf('.') != suf.length() - 1;
            System.out.printf("%s ", suf);
            prefix = new Prefix(prefix, suf);
        }
        System.out.println();
    }

    public static double compare(MarkovWordChain mc1, MarkovWordChain mc2)
    {
        assert mc1.prefLen == mc2.prefLen;
        int matches = 0;
        for (Prefix p : mc1.prefs)
        {
            if (mc2.prefixes.containsKey(p)) matches++;
        }
        return 100.0*matches/Math.min(mc1.prefs.length, mc2.prefs.length);
    }

    public void display()
    {
        for (Prefix pl : prefixes.keySet())
        {
            System.out.printf("%s: ", pl);
            prefixes.get(pl).display();
            System.out.println();
        }
    }
}
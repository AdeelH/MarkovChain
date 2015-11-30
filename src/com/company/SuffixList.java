package com.company;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SuffixList
{
    final static int MAXLEN = 20;
    Suffix[] sl;
    private HashMap<String, Integer> words;

    public SuffixList()
    {
        this.words = new HashMap<String, Integer>(MAXLEN);
    }

    public void add(String w)
    {
        words.put(w, words.containsKey(w) ? (words.get(w) + 1) : 1);
    }

    public void optimize()
    {
        sl = new Suffix[words.size()];
        int i = 0;
        for (Map.Entry<String, Integer> w : words.entrySet())
        {
            sl[i++] = new Suffix(w.getKey(), w.getValue());
        }
        if (sl.length > 1)
            Arrays.sort(sl, new Suffix.FreqComparator());
        words = null;
    }

    public String random()
    {
        if (sl.length == 0) return MarkovWordChain.NONWORD;

        return sl[(int)(Math.random()*sl.length)].word;
    }

    public String mostFreq()
    {
        return sl[0].word;
    }

    public int len()
    {
        return words != null ? words.size() : sl.length;
    }

    public void display()
    {
        for (Suffix w : sl)
        {
            System.out.printf("%s, ", w.toString());
        }
    }
}
package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Prefix
{
    int len;
    ArrayList<String> prefs;

    public Prefix(int len)
    {
        this.len = len;
        prefs = new ArrayList<String>(len);
        for (int i = 0; i < len; i++)
            prefs.add(MarkovWordChain.NONWORD);
    }

    public Prefix(Prefix other, String s)
    {
        this.prefs = new ArrayList<String>();
        for (String w : other.prefs)
        {
            prefs.add(w.trim().toLowerCase());
        }
        prefs.remove(0);
        prefs.add(s);
    }

    @Override
    public String toString()
    {
        String s = prefs.get(0);
        for (int i = 1; i < prefs.size(); i++)
        {
            s = s + " " + prefs.get(i);
        }
        return s;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Prefix)) return false;

        Prefix that = (Prefix) o;

        return prefs.equals(that.prefs);

    }

//    @Override
//    public int hashCode()
//    {
//        return prefs.hashCode();
//    }

    @Override
    public int hashCode()
    {
        int h = 0;
        for (String s : prefs)
        {
            h ^= s.hashCode();
        }
        return h;
    }
}

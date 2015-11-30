package com.company;


import java.util.Comparator;

public class Suffix
{
    String word;
    int f;

    public Suffix(String w, Integer f)
    {
        this.word = w;
        this.f = f;
    }

    public static class FreqComparator implements Comparator<Suffix>
    {
        @Override
        public int compare(Suffix s1, Suffix s2)
        {
            if (s1.f < s2.f)
                return 1;
            else if (s1.f > s2.f)
                return -1;
            return 0;
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Suffix)) return false;

        Suffix suffix = (Suffix) o;

        return word.equals(suffix.word);

    }

    @Override
    public int hashCode()
    {
        return word.hashCode();
    }

    @Override
    public String toString()
    {
        return String.format("(%s, %d)", word, f);
    }
}

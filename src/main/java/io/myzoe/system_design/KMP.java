package io.myzoe.system_design;

import java.util.Arrays;

public class KMP {
    private char[] txt;
    private char[] pat;
    private int[] lps;

    public KMP(String txt, String pat) {
        this.txt = txt.toCharArray();
        this.pat = pat.toCharArray();
        lps = calcLps();
    }

    private int[] calcLps() {
        int j = 0;
        int i = 1;
        int n = this.pat.length;
        int[] lps = new int[n];
        while (i < n) {
            if (pat[i] == pat[j]) {
                lps[i++] = ++j;
            }
            else {
                if (j == 0) {
                    //lps[i] = 0;
                    i++;

                }
                else {
                    j = lps[j-1];
                }
            }
        }

        return lps;
    }

    public int firstMatch() {

        int m = this.txt.length;
        int n = this.pat.length;

        int p = 0;
        int t = 0;
        while (t < m) {
            if (this.pat[p] == this.txt[t]) {
                p++;
                t++;
            }
            else {
                if (p == 0) {
                    t++;
                }
                else {
                    p = this.lps[p-1];
                }
            }

            if (p == n) {
                return t;
            }
        }
        return -1;
    }

    public int firstMatch(String txt) {
        this.txt = txt.toCharArray();
        return firstMatch();
    }


    public static void main(String[] args) {
        String txt = "aasfsdfsdabcabcabcdabcaaaabaabaaasgd";
        String pat = "aabaabaaa";
        KMP kmp = new KMP(txt, pat);
        int last = kmp.firstMatch();
        int first = last - pat.length() + 1;
        System.out.println(first + " - " + last + " = " + txt.substring(first, last+1));
        for (int i = 0; i < txt.length(); i++) {
            System.out.println(i + " - " + txt.charAt(i));
        }
        System.out.println(Arrays.toString(kmp.lps));
    }
}

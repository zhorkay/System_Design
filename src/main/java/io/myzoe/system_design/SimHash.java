package io.myzoe.system_design;

import java.util.Arrays;

public class SimHash {

    private static int[] getFeatures(String val) {
        return Arrays.stream(val.split(" ")).mapToInt(String::hashCode).toArray();
    }

    public static int simhash(int[] features) {
        int[] cnt = new int[32];
        for (int f : features) {
            for (int i = 0; i < 32; ++i) {
                int mask = 1 << i;
                if ((f&mask) == 0) {
                    cnt[i]--;
                }
                else {
                    cnt[i]++;
                }
            }
        }
        int res = 0;
        for (int i = 0; i < 32; i++) {
            if (cnt[i] > 0) {
                res |= 1 << i;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        String doc1 = "this is a similar document to the second one";
        String doc2 = "this is a similar document to the first one";
        int sh1 = simhash(getFeatures(doc1));
        int sh2 = simhash(getFeatures(doc2));
        System.out.println(Integer.toBinaryString(sh1));
        System.out.println(Integer.toBinaryString(sh2));
        System.out.println(Integer.toBinaryString(sh1 ^ sh2));
        System.out.println(Integer.bitCount(sh1 ^ sh2) + " = " + ((32 - Integer.bitCount(sh1 ^ sh2)) / 32f) * 100 + "%" );
    }

}

package io.myzoe.system_design;


import java.io.*;

import java.util.Random;
import java.util.Scanner;

public class TrieTest {
    public static void main(String[] args) {
        String fileName = "D:/Workspaces/Projects/System_Design/src/main/resources/words.txt";
        
        Random r = new Random();
        int R = 256;
        int RANK = 1_000_000_000;
        Trie trie = new Trie(R);

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            String line;
            long cnt = 0;
            long t0 = System.currentTimeMillis();
            while ((line = br.readLine()) != null) {
                int rank = r.nextInt(RANK);
                trie.put(line, rank);
                cnt++;
            }
            long t1 = System.currentTimeMillis();
            System.out.println("LOAD time: " + (double)(t1-t0) / 1000D + " sec of " + cnt + " different terms");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Trie.TrieNode tn = trie.root();
        while (true) {
            String a;
            Scanner S = new Scanner(System.in);
            a=S.next();
            tn = trie.get(tn, a.charAt(0));
            System.out.println(tn.getTopTen());
        }

    }
}

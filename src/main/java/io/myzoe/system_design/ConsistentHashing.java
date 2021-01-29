package io.myzoe.system_design;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Random;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConsistentHashing {
    // KEY: server hash_id
    // VALUE: server address
    private static ConcurrentSkipListMap<Integer, String> servers = new ConcurrentSkipListMap<>();

    //Using FNV1_32_HASH algorithm
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // If the calculated value is negative, take its absolute value.
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    //Get the node that should be routed to
    private static String getServer(String key) {
        //Get the hash value of the key
        int hash = getHash(key);
        //Get all Map s that are larger than the Hash value
        SortedMap<Integer, String> subServers = servers.tailMap(hash);
        if(subServers.isEmpty()){
            //If there is no one larger than the hash value of the key, start with the first node
            Integer i = servers.firstKey();
            //Return to the corresponding server
            return servers.get(i);
        }else{
            //The first Key is the nearest node clockwise past the node.
            Integer i = subServers.firstKey();
            //Return to the corresponding server
            return subServers.get(i);
        }
    }


    public static String[] addServer(String s) {
        int hash_key = getHash(s);
        return new String[]{String.valueOf(hash_key), servers.put(hash_key, s)};
    }

    public static void addServersEqually(String[] serverList) {
        int step = Integer.MAX_VALUE / serverList.length;
        int hash_key = 0;
        for (String server : serverList) {
            servers.put(hash_key, server);
            hash_key += step;
        }
    }

    public static String removeServer(String s) {
        int hash_key = getHash(s);
        return servers.remove(hash_key);
    }



    public static void main(String[] args) {
        String[] serverList = {
                "192.168.0.101",
                "192.168.0.102",
                "192.168.0.103",
                "192.168.0.104",
                "192.168.0.105",
                "192.168.0.106",
                "192.168.0.107",
                "192.168.0.108",
                "192.168.0.109",
                "192.168.0.110",
                "192.168.0.111",
                "192.168.0.112",
                "192.168.0.113",
                "192.168.0.114",
                "192.168.0.115",
                "192.168.0.116",
                "192.168.0.117",
                "192.168.0.118",
                "192.168.0.119",
                "192.168.0.120"
        };

        int distribution = args.length == 0 ? 1 : Integer.parseInt(args[0]);


        if (distribution == 1) {
            addServersEqually(serverList);
            System.out.println("Servers were added equally");
        }
        else {
            for (String s: serverList) {
                String[] res = addServer(s);
                if (res[1] != null) {
                    if (s.equals(res[1]))
                        System.out.println("Server {" + s + "} already exists");
                    else {
                        System.out.println("Server {" + s + "} replaced another server {" + res[1] + "} due to hash_id collision!!!");
                    }
                }
                else {
                    System.out.println("Server {" + s + "} added under hash_id: " + res[0]);
                }
            }
        }



        // generate random websites
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        final int N = 10000;
        String[] websites = new String[N];
        for (int i = 0; i < N; i++) {
            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            websites[i] = "http://www.example.org/" + generatedString;
        }
        // iterate through all servers 200 times and check whether the routed servers are the same
        HashMap<String, HashMap<String, Integer>> routed = new HashMap<>();
        HashMap<String, Integer> requestPerServer = new HashMap<>();
        for (String website : websites) {
            HashMap<String, Integer> routedServers = new HashMap<>();
            for (int i = 0; i < 2 * N; i++) {
                String server = getServer(website);
                routedServers.put(server, routedServers.getOrDefault(server, 0) + 1);
                requestPerServer.put(server, requestPerServer.getOrDefault(server, 0) + 1);
            }
            routed.put(website, routedServers);
            if (routedServers.size() != 1) {
                System.out.println("Website {" + website + "} routed as follows: ");
                System.out.println(routedServers);
            }

        }

        System.out.println("Requests per Server");
        for (String s : serverList) {
            int cnt = requestPerServer.getOrDefault(s, 0);
            float rel = (float)cnt / (float) (2 * N * N / 100);
            System.out.println(s + ": No of Requests: " + cnt + " Percentage of Total: " + rel + "%");
        }
    }

}

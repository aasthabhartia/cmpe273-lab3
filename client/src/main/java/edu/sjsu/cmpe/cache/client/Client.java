package edu.sjsu.cmpe.cache.client;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        
        CacheServiceInterface cache1 = new DistributedCacheService(
                "http://localhost:3000");
        CacheServiceInterface cache2 = new DistributedCacheService(
                "http://localhost:3001");
        CacheServiceInterface cache3 = new DistributedCacheService(
                "http://localhost:3002");

        ConsistentHash c = new ConsistentHash(Hashing.md5());

        c.add(cache1);
        c.add(cache2);
        c.add(cache3);


        Map<Integer, String> data = new HashMap<Integer, String>();
        data.put(1, "a");
        data.put(2, "b");
        data.put(3, "c");
        data.put(4, "d");
        data.put(5, "e");
        data.put(6, "f");
        data.put(7, "g");
        data.put(8, "h");
        data.put(9, "i");
        data.put(10, "j");

        CacheServiceInterface cache = null;
        

        for (Map.Entry<Integer, String> entry : data.entrySet()) 
        {
            cache = c.get(entry.getKey());
            System.out.println("Adding " + entry.getValue() + " to Server " + cache.getUrl());
            cache.put(entry.getKey(),entry.getValue());
        }

        for (Map.Entry<Integer, String> entry : data.entrySet()) 
        {
            cache = c.get(entry.getKey());
            System.out.println("Getting " + cache.get(entry.getKey()) + " from Server " + cache.getUrl());
            
        }



        System.out.println("Existing Cache Client...");
    }

}

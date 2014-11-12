package edu.sjsu.cmpe.cache.client;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashFunction;


public class ConsistentHash {

  private final HashFunction hashFunction;
  private final SortedMap<Integer, CacheServiceInterface> circle = new TreeMap<Integer, CacheServiceInterface>();

  public ConsistentHash(HashFunction hashFunction) {

    this.hashFunction = hashFunction;

    }

  public void add(CacheServiceInterface node) {
    
      circle.put(hashFunction.hashString(node.toString()).asInt(),node);
    
  }

  public void remove(CacheServiceInterface node) {
    
      circle.remove(hashFunction.hashString(node.toString()).asInt());
    
  }

  public CacheServiceInterface get(int key) {
    if (circle.isEmpty()) {
      return null;
    }
    int hash = hashFunction.hashInt(key).asInt();
    if (!circle.containsKey(hash)) {
      SortedMap<Integer, CacheServiceInterface> tailMap = circle.tailMap(hash);
      hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
    }
    return circle.get(hash);
  } 


}
package main;


public class HashTable<K, V> {
	

	private static final int TABLE_SIZE = (int) Math.pow(2, 15);
	private LinkedList<K, V>[] table;
	private double PHI = (1 + Math.sqrt(5)) / 2;
	private int numKeys;
	private int numProbes;
	
	public HashTable() {
		table = new LinkedList[TABLE_SIZE];
		numKeys = 0;
		numProbes = 0;
	}
	
	public int goldenCompression(int f) {
		return (int) (TABLE_SIZE * ((f / PHI) -  (int) (f / PHI)));
		//return (int) (TABLE_SIZE * f / PHI);
	}
	

	public int polynomialHash(String key) {		
		int f = 0;
		int index = 0;
		for (int k = key.length() - 1; k > -1; k--) {
			f += key.charAt(index) * Math.pow(128, k);
			index++;
		}
		return f;
	}
	
	public int hashValue(K key) {
		if (key instanceof String) {
			String keyString = (String) key;
			return goldenCompression(polynomialHash(keyString));
		} 		
		return goldenCompression(key.hashCode());
	}
	
	
	public void insert(K key, V value) {
		if (table[hashValue(key)] == null) {
			table[hashValue(key)] = new LinkedList<K,V>();
		}
		table[hashValue(key)].add(key, value);
		numKeys++;
	}
	
	public V lookUp(K key) {
		if (table[hashValue(key)] != null) {
			V value = table[hashValue(key)].find(key);
			numProbes += table[hashValue(key)].getProbed();
			table[hashValue(key)].deProbe();
			return value;
		}
		return null;
	}
	
	public V delete(K key) {
		if (table[hashValue(key)] != null) {
			return table[hashValue(key)].remove(key);
		}
		return null;
	}
	
	public double loadFactor() {
		return Math.round((double) numKeys / TABLE_SIZE * 10000.0) / 10000.0 ;
	}
	
	public int numKeys() {
		return numKeys;
	}
	
	public int getProbed(K key) {
		return table[hashValue(key)].getProbed();				
	}
	
	public void deProbe(K key) {
		table[hashValue(key)].deProbe();
	}

	


}

/**
 * 
 */
package main;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

/**
 * @author Matthew
 *
 */
public class HashTableTest {
	
	private final static String TEST_STRING_1 = "ABC";
	private final static String TEST_STRING_2 = "apple"; 
	private final static String TEST_STRING_3 = "cooke"; 
	private static final String dictionaryTextFile = "docs/dict.txt";

	/**
	 * Test method for {@link main.HashTable#HashTable()}.
	 */
	@Test
	public void testHashTable() {
		fail("Not yet implemented");
	}


	/**
	 * Test method for {@link main.HashTable#polynomialHash(java.lang.String)}.
	 */
	@Test
	public void testPolynomialHash() {
		HashTable<String, String> table = new HashTable<String, String>();
		assertEquals(817652, table.polynomialHash("1st"));
		//assertEquals(2147483647, table.polynomialHash("zygote"));
		assertEquals(2147483647, table.polynomialHash("Zurich"));
		assertEquals(65 * 128 * 128 + 66 * 128 + 67, table.polynomialHash("ABC"));
		
		
	}
	
	@Test
	public void testGoldenCompression() {
		HashTable<String, String> table = new HashTable<String, String>();
		assertEquals((int) (table.TABLE_SIZE / table.PHI * 1), table.goldenCompression(1));
		assertEquals((int) (table.TABLE_SIZE * ((2 * table.PHI) -  Math.floor(2 * table.PHI))), table.goldenCompression(2));
	}
	
	@Test
	public void testHashValueKey() {
		HashTable<String, String> dict = new HashTable<String, String>();
		
		assertEquals(4278, dict.hashValue("zygote"));
		assertEquals(4278, dict.hashValue("Zuriuch"));
		assertEquals(4278, dict.hashValue("zucchini"));
	}

	/**
	 * Test method for {@link main.HashTable#insert(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	public void testInsert() {
		HashTable<String, String> table = new HashTable<String, String>();
		table.insert(TEST_STRING_1, TEST_STRING_1);
		assertEquals(1, table.numKeys());
		table.insert(TEST_STRING_2, TEST_STRING_2);
		assertEquals(2, table.numKeys());
		assertEquals(TEST_STRING_1, table.lookup(TEST_STRING_1));
	}

	/**
	 * Test method for {@link main.HashTable#lookup(double)}.
	 */
	@Test
	public void testLookUp() {
		HashTable<String, String> table = new HashTable<String, String>();
		table.insert(TEST_STRING_1, TEST_STRING_1);
		assertEquals(1, table.numKeys());
		table.insert(TEST_STRING_2, TEST_STRING_2);
		assertEquals(2, table.numKeys());
		assertEquals(TEST_STRING_1, table.lookup(TEST_STRING_1));
		assertEquals(TEST_STRING_2, table.lookup(TEST_STRING_2));
	}

	/**
	 * Test method for {@link main.HashTable#delete(double)}.
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link main.HashTable#loadFactor()}.
	 */
	@Test
	public void testLoadFactor() {
		HashTable<String, String> table = new HashTable<String, String>();
		table.insert(TEST_STRING_1, TEST_STRING_1);
		assertEquals(0.0001, table.loadFactor(), 0.001);
	}
	
	@Test
	public void testProbe() {		
		HashTable<String, String> dict = new HashTable<String, String>();
		String line;
		try {
			Scanner input = new Scanner(new File(dictionaryTextFile));
			while (input.hasNextLine()) {
				line = input.nextLine();
				//System.out.println(line);
				dict.insert(line, line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//assertEquals(null, dict.lookup(TEST_STRING_3));
		assertEquals("mark", dict.lookup("mark"));

	}
	


}

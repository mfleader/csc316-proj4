/**
 * 
 */
package main;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Matthew
 *
 */
public class HashTableTest {

	/**
	 * Test method for {@link main.HashTable#HashTable()}.
	 */
	@Test
	public void testHashTable() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link main.HashTable#goldenHash(java.lang.Object)}.
	 */
	@Test
	public void testGoldenHash() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link main.HashTable#polynomialHash(java.lang.String)}.
	 */
	@Test
	public void testPolynomialHash() {
		HashTable table = new HashTable<String, String>();
		assertEquals(65 * 128 * 128 + 66 * 128 + 67, table.polynomialHash("ABC"));

	}

	/**
	 * Test method for {@link main.HashTable#insert(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link main.HashTable#lookUp(double)}.
	 */
	@Test
	public void testLookUp() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}

}

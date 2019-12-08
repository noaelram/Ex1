package Ex1TestingJUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import Ex1.Monom;
import Ex1.Polynom;

public class MonomTest {

	private void checkMonomAux1(String monom, boolean isZero, double x, double y) {
		Monom m = new Monom(monom);
		String s = m.toString();
		m = new Monom(s);
		assertEquals(isZero, m.isZero());
		assertEquals(y, m.f(x));
	}

	private void checkMonomAux2(Monom monom, boolean isZero, boolean equals) {
		Monom m = new Monom(monom);
		String s = m.toString();
		Monom m1 = new Monom(s);
		assertEquals(isZero, m1.isZero());
		assertEquals(equals, m.equals(m1));
	}

	@Test
	public void test1() {
		checkMonomAux1("4x^4", false, 1, 4);
		checkMonomAux1("2", false, 80, 2);
		checkMonomAux1("-x", false, -2, 2);
		checkMonomAux1("-x", false, 2, -2);
		checkMonomAux1("-3.2x^2", false, 5, -3.2 * 5 * 5);
		checkMonomAux1("0", true, 10, 0);
	}

	@Test
	public void test2() {
		checkMonomAux2(new Monom(0, 5), true, true);
		checkMonomAux2(new Monom(-1, 0), false, true);
		checkMonomAux2(new Monom(-1.3, 1), false, true);
		checkMonomAux2(new Monom(-2.2, 2), false, true);
	}

	@Test
	public void testThrows() {
		String[] bad = new String[] { "", "xx", "2+4", "x^x", "0+0x^2", "x^--2", null };

		for (final String m : bad) {
			assertThrows(RuntimeException.class, () -> {
				new Monom(m);
			});
		}
	}

	@Test
	public void testCopy() {
		assertEquals(new Monom(0, 5).copy(), new Monom(0, 5));
		assertEquals(new Monom(3.3, 7).copy(), new Monom("3.3x^7"));
	}

	@Test
	public void testEquals() {
		assertFalse(new Monom(0, 5).equals(null));
		assertFalse(new Monom(0, 5).equals(1));
		assertFalse(new Monom(0, 5).equals("1"));
		assertTrue(new Monom(0, 5).equals(new Monom(0, 5)));
		assertFalse(new Monom(0, 5).equals(new Monom(1, 5)));
		Polynom p = new Polynom("3x^7");
		assertTrue(new Monom(3, 7).equals(p));
		assertFalse(new Monom(3, 8).equals(p));
	}
}

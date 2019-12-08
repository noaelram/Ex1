package Ex1TestingJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import Ex1.Monom;
import Ex1.Polynom;

public class PolynomTest {

	@Test
	public void test1() {
		Polynom p1 = new Polynom();
		String[] monoms = { "1", "x", "x^2", "0.5x^2", "5x^3" };

		for (int i = 0; i < monoms.length; i++) {
			Monom m = new Monom(monoms[i]);
			p1.add(m);
		}

		assertEquals(3.2504750149995396, p1.area(0, 1, 0.0001));
		assertEquals("+5.0x^3+1.5x^2+1.0x+1.0", p1.toString());
	}

	@Test
	public void test2() {
		Polynom p1 = new Polynom(), p2 = new Polynom();
		String[] monoms1 = { "2", "-x", "-3.2x^2", "4", "-1.5x^2" };
		String[] monoms2 = { "5", "1.7x", "3.2x^2", "-3", "-1.5x^2" };

		for (int i = 0; i < monoms1.length; i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}

		for (int i = 0; i < monoms2.length; i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}

		assertEquals("-4.7x^2-1.0x+6.0", p1.toString());
		assertEquals("+1.7000000000000002x^2+1.7x+2.0", p2.toString());

		p1.add(p2);

		assertEquals("-3.0x^2+0.7x+8.0", p1.toString());

		p1.multiply(p2);

		assertEquals("-5.1000000000000005x^4-3.9099999999999997x^3+8.790000000000001x^2+15.0x+16.0", p1.toString());
	}

	@Test
	public void testThrows() {
		String[] bad = new String[] { "", "xx", "x^x", "x^--2", null, "x+x-x-x", "x^1-x^2-2*2", "x^4-x^-1+2-5x" };

		for (final String m : bad) {
			assertThrows(RuntimeException.class, () -> {
				new Polynom(m);
			}, m);
		}
	}

	@Test
	public void testNotThrows() {
		String[] bad = new String[] { "2+4", "x", "0+0x^2" };

		for (final String m : bad) {
			new Polynom(m);
		}
	}

	@Test
	public void test3() {
		Polynom p1 = new Polynom(), p2 = new Polynom();
		String[] monoms1 = { "7x^5", "5x^4", "-x", "17x^2", "-2x", "4", "-9x^2", "6x^3" };
		String[] monoms2 = { "7", "3", "10x^2", "x^2", "-8", "2x^5" };

		for (int i = 0; i < monoms1.length; i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}

		assertEquals("+7.0x^5+5.0x^4+6.0x^3+8.0x^2-3.0x+4.0", p1.toString());

		for (int i = 0; i < monoms2.length; i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}

		assertEquals("+2.0x^5+11.0x^2+2.0", p2.toString());

		p1.substract(p2);

		assertEquals("+5.0x^5+5.0x^4+6.0x^3-3.0x^2-3.0x+2.0", p1.toString());

		p1.multiply(p2);

		assertEquals("+10.0x^10+10.0x^9+12.0x^8+49.0x^7+49.0x^6+80.0x^5-23.0x^4-21.0x^3+16.0x^2-6.0x+4.0",
				p1.toString());

		Polynom newP = (Polynom) p1.derivative();
		assertEquals("+100.0x^9+90.0x^8+96.0x^7+343.0x^6+294.0x^5+400.0x^4-92.0x^3-63.0x^2+32.0x-6.0", newP.toString());

		Polynom r1 = new Polynom("4x^2+7x^5+3x+2+0x");
		Polynom r2 = new Polynom("4x^2+7x^5+3x+2+0x^8");
		assertTrue(r1.equals(r2));
	}
}

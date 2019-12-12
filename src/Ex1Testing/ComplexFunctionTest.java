package Ex1Testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import Ex1.ComplexFunction;
import Ex1.Monom;
import Ex1.Polynom;

public class ComplexFunctionTest {
	
	
	@Test
	public void testMonom() {
		Monom m = new Monom("3x^2");
		ComplexFunction c = new ComplexFunction("plus", m, m);
		assertEquals(c.f(2), 24);
	}

	@Test
	public void testPolynomPlus() {
		Monom m = new Monom("3x^1");
		Polynom p = new Polynom("x^1+3x^2");
		ComplexFunction c = new ComplexFunction("plus", p, m);
		assertEquals(c.f(1), 7);
	}

	@Test
	public void testPolynomMul() {
		Monom m = new Monom("3x^1");
		Polynom p = new Polynom("x^1+3x^2");
		ComplexFunction c = new ComplexFunction("mul", p, m);
		assertEquals(c.f(1), 12);
	}

	@Test
	public void testPolynomDiv() {
		Polynom p1 = new Polynom("3x^3");
		Polynom p2 = new Polynom("2x^2");
		ComplexFunction c = new ComplexFunction("div", p1, p2);
		assertEquals(c.f(2), 3);
	}

	@Test
	public void testPolynomMax() {
		Polynom p1 = new Polynom("3x^2");
		Polynom p2 = new Polynom("2x^2");
		ComplexFunction c = new ComplexFunction("max", p1, p2);
		assertEquals(c.f(2), 12);
	}

	@Test
	public void testPolynomMin() {
		Polynom p1 = new Polynom("3x^2");
		Polynom p2 = new Polynom("2x^2");
		ComplexFunction c = new ComplexFunction("min", p1, p2);
		assertEquals(c.f(2), 8);
	}

	@Test
	public void testPolynomComp() {
		Polynom p1 = new Polynom("3x^2");
		Polynom p2 = new Polynom("2x^2");
		ComplexFunction c = new ComplexFunction("comp", p1, p2);
		assertEquals(c.f(1), 12);
	}

	@Test
	public void testPolynomInitFromString() {
		String[] operations = new String[] { "plus", "div", "mul", "max", "min", "comp" };
		for (String op : operations) {
			ComplexFunction c = new ComplexFunction(new Monom("x"));
			ComplexFunction c2 = (ComplexFunction) c.initFromString(op + "(2,4)");
			ComplexFunction expected = new ComplexFunction(op, new ComplexFunction(new Monom("2")),
					new ComplexFunction(new Monom("4")));
			assertEquals(expected, c2);
		}
	}

	@Test // should not throw an exception
	public void testComplexInitFromString() {
		String s = "min(min(min(min(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)),div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)),-1.0x^4 +2.4x^2 +3.1),+0.1x^5 -1.2999999999999998x +5.0)";
		s = s.replaceAll(" ", "");
		ComplexFunction c = new ComplexFunction(s);
		assertEquals(s, c.toString());
	}

	@Test
	public void testPolynomInitFromString2() {
		String[] operations = new String[] { "plus", "div", "mul", "max", "min", "comp" };
		for (String op : operations) {
			ComplexFunction c = new ComplexFunction(new Monom("x"));
			ComplexFunction c2 = (ComplexFunction) c.initFromString(op + "(2x,4x^2)");
			ComplexFunction expected = new ComplexFunction(op, new ComplexFunction(new Monom(2, 1)),
					new ComplexFunction(new Monom(4, 2)));
			assertEquals(expected, c2);
		}
	}

	@Test
	public void testBadInput() {
		assertThrows(RuntimeException.class, () -> {
			new ComplexFunction("noop", new Monom("x"), new Monom("x"));
		}, "1");
		assertThrows(RuntimeException.class, () -> {
			new ComplexFunction("", new Monom("x"), new Monom("x"));
		}, "2");
		assertThrows(RuntimeException.class, () -> {
			new ComplexFunction(null, new Monom("x"), new Monom("x"));
		}, "3");
		assertThrows(RuntimeException.class, () -> {
			new ComplexFunction("plus", null, new Monom("x"));
		}, "4");
		assertThrows(RuntimeException.class, () -> {
			new ComplexFunction("plus", null, null);
		}, "5");
		assertThrows(RuntimeException.class, () -> {
			new ComplexFunction("plus", null, new Monom("x"));
		}, "6");
	}

	@Test
	public void testGoodInput() {
		new ComplexFunction("plus", new Monom("x"), null);
		new ComplexFunction("none", new Monom("x"), null);
	}
}

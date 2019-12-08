package myMath;
import java.util.Scanner;

import Ex1.Monom;
import Ex1.Polynom;

public class PolynomTest {
	public static void main(String[] args) {
		//test1();
		//test2();
		test3();
	}

	public static void test1() {
		Polynom p1 = new Polynom();
		String[] monoms = {"1","x","x^2","0.5x^2","5x^3"};

		for (int i = 0; i < monoms.length; i++) {
			Monom m = new Monom(monoms[i]);
			p1.add(m);
		}

		double aa = p1.area(0, 1, 0.0001);
		System.out.println("aa = " +aa);
		System.out.println(p1);
	}

	public static void test2() {
		Polynom p1 = new Polynom(), p2 = new Polynom();
		String[] monoms1 = {"2","-x","-3.2x^2","4","-1.5x^2"};
		String[] monoms2 = {"5", "1.7x","3.2x^2","-3","-1.5x^2"};

		for (int i = 0; i < monoms1.length; i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}

		for (int i = 0; i < monoms2.length; i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}

		System.out.println("p1 = " +p1);
		System.out.println("p2 = " +p2);
		p1.add(p2);

		System.out.println("p1+p2 = " +p1);
		p1.multiply(p2);

		System.out.println("(p1+p2)*p2 = " +p1);
		String s1 = p1.toString();
		//Polynom_able pp1 = Polynom.parse(s1);
		//System.out.println("from string: "+pp1);
	}
	
	public static void test3() {
		System.out.println("**** My Test ****");
		System.out.println("**** This one confirms our functions correctness ****");
		
		Polynom p1 = new Polynom(), p2 = new Polynom();
		String[] monoms1 = {"7x^5","5x^4","-x","17x^2","-2x","4","-9x^2","6x^3"};
		String[] monoms2 = {"7","3","10x^2","x^2","-8","2x^5"};
		
		for (int i = 0; i < monoms1.length; i++) {
			try {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
			} catch (Exception e) {
				System.out.println("Invalid monom has been inserted! Refer it as 0");
			}
		}
		
		System.out.println("p1 = " +p1);

		for (int i = 0; i < monoms2.length; i++) {
			try {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
			} catch (Exception e) {
				System.out.println("Invalid input. Refer the invalid Monom as 0");
			}
		}
		
		System.out.println("p2 = " +p2);
		p1.substract(p2);
		
		System.out.println("p1-p2 = " +p1);
		p1.multiply(p2);

		System.out.println("(p1-p2)*p2 = " +p1);
		
		Polynom P = new Polynom("x^3");
		System.out.println("P.Root = " +P.root(-3.14, 2.72, Monom.EPSILON));
		System.out.println("P.Area = " +P.area(0, 2, Monom.EPSILON));

		Polynom newP = (Polynom)p1.derivative();
		System.out.println("(p1-p2)*p2 derivative = " +newP);
		
		System.out.println("Given 2 polynoms");
		Polynom r1 = new Polynom("4x^2+7x^5+3x+2+0x");
		System.out.println("r1 = " +r1);
		Polynom r2 = new Polynom("4x^2+7x^5+3x+2+0x^8");
		System.out.println("r2 = " +r2);
		
		System.out.println("r1 equals to r2? " +r1.equals(r2));
	}
}

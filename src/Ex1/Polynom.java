package Ex1;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class represents a Polynom with add, multiply functionality, it also
 * should support the following: 1. Riemann's Integral:
 * https://en.wikipedia.org/wiki/Riemann_integral 2. Finding a numerical value
 * between two values (currently support root only f(x)=0). 3. Derivative
 * 
 * @author Boaz
 *
 */

public class Polynom implements Polynom_able {
	private LinkedList<Monom> LL;

	/**
	 * Zero (empty polynom)
	 */

	public Polynom() {
		LL = new LinkedList<Monom>();
		LL.add(new Monom(0, 0));
	}

	/**
	 * init a Polynom from a String such as: {"x", "3+1.4X^3-34x",
	 * "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * 
	 * @param s: is a string represents a Polynom
	 */

	public Polynom(String s) {
		LL = new LinkedList<Monom>();
		Monom_Comperator MC = new Monom_Comperator();

		if (s.contains("^-") || s.contains("-^") || s.contains("+^")) // Check input validity.
			throw new RuntimeException("Error");

		while (s.contains("++")) // Fix input validity.
			s = s.replace("++", "+");

		while (s.contains("--")) // Fix input validity.
			s = s.replace("--", "-");

		while (s.contains("^+")) // Fix input validity.
			s = s.replace("^+", "^");

		String[] arr = s.split("(?=\\+|-)");

		for (int i = 0; i < arr.length; i++) {
			Monom m = new Monom(arr[i]);
			if (m.get_coefficient() != 0)
				LL.add(m);
		}

		LL.sort(MC);
	}

	@Override
	public double f(double x) {
		double Sum = 0;

		for (int i = 0; i < LL.size(); i++)
			Sum += LL.get(i).f(x);

		return Sum;
	}

	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> It = ((Polynom_able) p1).iteretor();

		while (It.hasNext())
			this.add(It.next());
	}

	@Override
	public void add(Monom m1) {
		Iterator<Monom> Iter = this.iteretor();
		int Flag = 0;

		while (Iter.hasNext()) {
			Monom newM = Iter.next();
			if (newM.get_power() == m1.get_power()) {
				newM.add(m1);
				Flag = 1;
			}
		}

		if (Flag == 0) {
			LL.add(m1);
			LL.sort(Monom._Comp);
		}
	}

	@Override
	public void substract(Polynom_able p1) {
		Iterator<Monom> Iter = ((Polynom_able) p1).iteretor();
		Polynom newP = new Polynom();

		while (Iter.hasNext()) {
			Monom M = Iter.next();
			Monom newM = new Monom(M.get_coefficient() * -1, M.get_power());
			newP.add(newM);
		}

		this.add(newP);
	}

	@Override
	public void multiply(Polynom_able p1) {
		Iterator<Monom> Iter = ((Polynom_able) p1).iteretor();
		Polynom newP = this.copy();
		Polynom newP2 = newP.copy();
		this.substract(this);

		while (Iter.hasNext()) {
			newP.multiply(Iter.next());
			this.add(newP);
			newP = newP2.copy();
		}
	}

	@Override
	public boolean equals(Object p1) {
		if (this == p1)
			return true;
		if (p1 == null)
			return false;

		if (p1.getClass() == Monom.class) {
			Monom m = (Monom) p1;
			if (this.LL.size() != 1)
				return false;

			return m.equals(this.LL.get(0));
		}
		if (p1.getClass() == ComplexFunction.class) {
			ComplexFunction m = (ComplexFunction) p1;
			return m.equals(this);
		}

		if (Polynom.class != p1.getClass())
			return false;

		Iterator<Monom> Iter1 = ((Polynom_able) p1).iteretor();
		Iterator<Monom> Iter2 = this.iteretor();

		while (Iter1.hasNext() && Iter2.hasNext()) {
			Monom newM1 = Iter1.next();
			Monom newM2 = Iter2.next();

			if (newM1.get_coefficient() != newM2.get_coefficient())
				return false;

			if (newM1.get_power() != newM2.get_power())
				return false;
		}

		if (!Iter1.hasNext() && !Iter2.hasNext())
			return true;

		return false;
	}

	@Override
	public boolean isZero() {
		for (int i = 0; i < LL.size(); i++)
			if (LL.get(i).get_coefficient() != 0)
				return false;

		return true;
	}

	@Override
	public double root(double x0, double x1, double eps) {
		if (this.f(x0) * this.f(x1) < 0) {
			double newX = (x0 + x1) / 2;
			while (Math.abs(newX) > eps) {
				if (this.f(newX) == 0)
					return newX;

				else {
					if (this.f(newX) > 0) {
						if (this.f(x1) > 0)
							x1 = newX;
						else
							x0 = newX;
					}

					else {
						if (this.f(x0) < 0)
							x0 = newX;
						else
							x1 = newX;
					}
				}

				newX = (x0 + x1) / 2;
			}

			return newX;
		}

		else {
			if (this.f(x0) * this.f(x1) == 0) {
				if (this.f(x0) == 0)
					return x0;
				else
					return x1;
			}

			else
				throw new RuntimeException("Root doesn't exist");
		}
	}

	public Polynom copy() {
		Iterator<Monom> Iter = this.iteretor();
		Polynom newP = new Polynom();

		while (Iter.hasNext()) {
			Monom newM = Iter.next();
			newP.add(new Monom(newM.get_coefficient(), newM.get_power()));
		}

		return newP;
	}

	@Override
	public Polynom_able derivative() {
		Iterator<Monom> Iter = this.iteretor();
		Polynom newP = new Polynom();

		while (Iter.hasNext())
			newP.add(Iter.next().derivative());

		return (Polynom_able) newP;
	}

	@Override
	public double area(double x0, double x1, double eps) {
		if (x0 >= x1) // definition.
			return 0;

		double sum = 0;
		for (double i = x0; i <= x1; i += eps)
			if (this.f(i) > 0)
				sum += eps * this.f(i);

		return sum;
	}

	@Override
	public Iterator<Monom> iteretor() {
		Iterator<Monom> It = LL.listIterator();
		return It;
	}

	@Override
	public void multiply(Monom m1) {
		Iterator<Monom> Iter = this.iteretor();

		while (Iter.hasNext())
			Iter.next().multipy(m1);
	}

	public String toString() {
		Iterator<Monom> It = this.iteretor();
		String ans = "";

		while (It.hasNext()) {
			Monom newM = It.next();
			if (newM.get_coefficient() != 0) {
				if (newM.get_coefficient() > 0)
					ans += "+" + newM.toString();
				else
					ans += newM.toString();
			}
		}

		if (ans.equals(""))
			return "0";
		else
			return ans;
	}

	@Override
	public function initFromString(String s) {
		return new Polynom(s);
	}
}

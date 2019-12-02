
package myMath;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import Ex1.Range;
import Ex1.function;
import Ex1.functions;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */

public class Monom implements functions{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}

	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}

	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}

	public int get_power() {
		return this._power;
	}

	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */

	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}

	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	}

	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************

	public Monom(String s) {
		int Flag = 0;
		double FlagM = 0;

		if (s == null)
			throw new RuntimeException("Error! String is null");

		if (s.contains("x"))
			Flag = 1;

		s = s.replaceAll(" ", ""); // Check input validity.

		switch(Flag) {
		case 1 :
			if (Check(s) > 1)
				throw new RuntimeException("Error! String contains more than one x");

			else {
				while (s.contains("^+")) // Fix input validity.
					s = s.replace("^+", "^");
				
				while (s.contains("++")) // Fix input validity.
					s = s.replace("++", "+");
				
				while (s.contains("--")) // Fix input validity.
					s = s.replace("--", "-");
				
				String[] Parts = s.split("x");

				if (Parts.length == 0) { // Coefficient and power equal 0.
					this.set_coefficient(1);
					this.set_power(1);
				}

				else {
					if (Parts.length == 1) { // Power equals 1.
						if (Parts[0].length() == 1) {
							if (Parts[0].equals("-"))
								FlagM = -1;

							if (FlagM == -1) {
								this.set_coefficient(-1);
								this.set_power(1);
								FlagM = 0;
							}

							else {
								try {
									this.set_coefficient(Double.parseDouble(Parts[0]));
									this.set_power(1);
								} catch (Exception e) {
									throw new RuntimeException("Invalid input");
								}
							}
						}

						else {
							try {
								this.set_coefficient(Double.parseDouble(Parts[0]));
								this.set_power(1);
							} catch (Exception e) {
								throw new RuntimeException("Invalid input");
							}
						}
					}

					else { // Power is greater than 1.
						if (Parts[0].length() == 0) // Coefficient equals 1.
							this.set_coefficient(1);

						else {
							if (Parts[0].length() == 1) {
								if (Parts[0].equals("-"))
									FlagM = -1;

								if (FlagM == -1)
									this.set_coefficient(-1);

								else {
									try {
										this.set_coefficient(Double.parseDouble(Parts[0]));
									} catch (Exception e) {
										throw new RuntimeException("Invalid input");
									}
								}
							}

							else {
								try {
									this.set_coefficient(Double.parseDouble(Parts[0]));
								} catch (Exception e) {
									throw new RuntimeException("Invalid input");
								}
							}
						}

						if (Parts[1].length() != 0) {
							if (Parts[1].contains("^")) {
								if (Parts[1].length() > 1) {
									try {
										this.set_power(Integer.parseInt(Parts[1].substring(1)));
									} catch (Exception e) {
										throw new RuntimeException("Invalid input");
									}
								}

								else {
									this.set_coefficient(0);
									this.set_power(0);
									throw new RuntimeException("Invalid input");
								}
							}

							else {
								this.set_coefficient(0);
								this.set_power(0);
								throw new RuntimeException("Invalid input");
							}
						}
					}
				}
			} break;

		default :
			while (s.contains("++")) // Fix input validity.
				s = s.replace("++", "+");
			
			while (s.contains("--")) // Fix input validity.
				s = s.replace("--", "-");
			
			try {
				this.set_coefficient(Double.parseDouble(s));
				this.set_power(0);
			} catch(Exception e) {
				throw new RuntimeException("Invalid input");
			} break;
		}
	}

	public void add(Monom m) {
		if (this.get_power() == m.get_power())
			this.set_coefficient(this.get_coefficient() + m.get_coefficient());
		else
			throw new RuntimeException("Monoms can't be added");
	}

	public void multipy(Monom d) {
		this.set_coefficient(this.get_coefficient() * d.get_coefficient());
		this.set_power(this.get_power() + d.get_power());
	}

	public String toString() {
		if (this.get_coefficient() == 0)
			return "0";

		else {
			if (this.get_power() == 0)
				return "" +this.get_coefficient();

			else {
				if (this.get_power() == 1)
					return "" +this.get_coefficient() + "x";

				else
					return "" +this.get_coefficient() + "x^" +this.get_power();
			}
		}
	}

	// you may (always) add other methods.
	/**
	 * 
	 * @param m
	 * @return
	 */

	public boolean equals(Monom m) {
		if (this.get_coefficient() == 0 && m.get_coefficient() == 0)
			return true;

		else {
			if (this.get_coefficient() == m.get_coefficient() && this.get_power() == m.get_power())
				return true;
			else
				return false;
		}
	}

	public function initFromString(String s) {
		return null;
		
	}
	
	public Monom copy() {
		Monom copy=new Monom(this);
		return copy;
		
	}

	private int Check(String s) {
		int Sum = 0;

		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) == 'x')
				Sum++;

		return Sum;
	}

	//****************** Private Methods and Data *****************
	
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<function> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(function e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends function> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initFromFile(String file) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveToFile(String file) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawFunctions(String json_file) {
		// TODO Auto-generated method stub
		
	}
}

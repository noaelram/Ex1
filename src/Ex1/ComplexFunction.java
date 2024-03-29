package Ex1;

public class ComplexFunction implements complex_function {
	private function left; // can't be null
	private function right; // can be null
	private Operation op;

	private boolean isRightExist() {
		return right() != null;
	}

	@Override
	public double f(double x) {
		switch (op) {
		case Plus:
			return left().f(x) + right().f(x);
		case Times:
			return left().f(x) * right().f(x);
		case Comp:
			return left().f(right().f(x));
		case Divid:
			return left().f(x) / right().f(x);
		case Max:
			return Math.max(left().f(x), right().f(x));
		case Min:
			return Math.min(left().f(x), right().f(x));
		case None:
			return left().f(x);
		default:
			throw new RuntimeException("Error! Bad opetaion in f ->" + op.toString());
		}
	}

	public ComplexFunction(String s) {
		if (s == null)
			throw new RuntimeException("Error! String is null");

		s = s.replaceAll(" ", ""); // Check input validity.

		int index1 = s.indexOf('(');
		int index2 = s.lastIndexOf(')');

		if (index1 == -1 && index2 == -1) {
			try {
				this.left = new Monom(s);
				this.right = null;
				this.op = Operation.None;
				return;
			} catch (Exception e) {
			}
			try {
				this.left = new Polynom(s);
				this.right = null;
				this.op = Operation.None;
				return;
			} catch (Exception e) {
			}
			throw new RuntimeException("Bad input : " + s);
		}

		if ((index1 == -1 && index2 != -1) || (index1 != -1 && index2 == -1)) {
			throw new RuntimeException("Bad input 2 : " + s);
		}

		if (index2 != s.length() - 1) {
			throw new RuntimeException("Closing parenthesis must be at the end of the string");
		}

		String middle = s.substring(index1 + 1, index2);

		int index4 = middle.indexOf(',');

		if (index4 == -1) {
			throw new RuntimeException("Bad input 3 : " + middle);
		}

		int middleCommaIndex = findMiddleComma(middle);
		if (middleCommaIndex == -1) {
			throw new RuntimeException("Can't find middle comma : " + middle);

		}

		String left = middle.substring(0, middleCommaIndex);
		String right = middle.substring(middleCommaIndex + 1);

		function l = new ComplexFunction(left);
		function r = new ComplexFunction(right);

		Operation op = parseOp(s.substring(0, index1));

		this.left = l;
		if (this.left == null) {
			throw new RuntimeException("left side can't be null");
		}

		this.right = r;
		this.op = op;
	}

	private int findMiddleComma(String middle) {
		int counter = 0;
		for (int i = 0; i < middle.length(); i++) {
			char c = middle.charAt(i);
			if (c == ',' && counter == 0) {
				return i;
			}
			if (c == '(') {
				counter++;
			}
			if (c == ')') {
				counter--;
			}

		}
		return -1;
	}

	@Override
	public function initFromString(String s) {
		try {
			return new Monom(s);
		} catch (Exception e) {
		}
		try {
			return new Polynom(s);
		} catch (Exception e) {
		}
		return new ComplexFunction(s);
	}

	private Operation parseOp(String op) {
		op = op.toLowerCase();
		if (op.equals("plus")) {
			return Operation.Plus;
		} else if (op.equals("mul")) {
			return Operation.Times;

		} else if (op.equals("div")) {
			return Operation.Divid;

		} else if (op.equals("max")) {
			return Operation.Max;

		} else if (op.equals("min")) {
			return Operation.Min;

		} else if (op.equals("comp")) {
			return Operation.Comp;
		} else if (op.equals("none")) {
			return Operation.None;
		} else {
			throw new RuntimeException("Bad operation in parseOp -> " + op);
		}
	}

	private String getStringOp() {
		switch (op) {
		case Plus:
			return "plus";
		case Times:
			return "mul";
		case Comp:
			return "comp";
		case Divid:
			return "div";
		case Max:
			return "max";
		case Min:
			return "min";
		case None:
			return "none";
		default:
			throw new RuntimeException("Error! Bad opetaion in getStringOp");
		}
	}

	private ComplexFunction(function left, function right, Operation op) {
		if (left == null) {
			throw new RuntimeException("left side can't be null");
		}
		this.left = left;
		this.right = right;
		this.op = op;
	}

	public ComplexFunction(function left) {
		if (left == null) {
			throw new RuntimeException("left side can't be null");
		}
		this.left = left;
		this.right = null;
		this.op = Operation.None;
	}

	public ComplexFunction(String op, function left, function right) {
		if (left == null) {
			throw new RuntimeException("left side can't be null");
		}
		this.left = left;
		this.right = right;
		this.op = parseOp(op);
	}

	@Override
	public function copy() {
		return new ComplexFunction(left().copy(), isRightExist() ? right().copy() : null, getOp());
	}

	/*
	 * This implementation rely on function.equals() method, such that two complex
	 * functions are equals if the operation is the same operation and
	 * left.equals(left) and right.equals(right). The problem is that we can't check
	 * for every x that this.f(x) == obj.f(x) because this is not feasible.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		if (obj.getClass() == Polynom.class || obj.getClass() == Monom.class) {
			if (this.right != null || this.op != Operation.None) {
				return false;
			}
			return this.left.equals(obj);
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ComplexFunction other = (ComplexFunction) obj;
		if (this.op == Operation.None && other.op != Operation.None) {
			return this.left.equals(other);
		} else if (other.op == Operation.None && this.op != Operation.None) {
			return other.left.equals(this);
		}
		if (left == null) {
			if (other.left != null) {
				return false;
			}
		} else if (!left.equals(other.left)) {
			return false;
		}
		if (op != other.op) {
			return false;
		}
		if (right == null) {
			if (other.right != null) {
				return false;
			}
		} else if (!right.equals(other.right)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		if (this.op == Operation.None) {
			return left.toString().trim();
		}
		return getStringOp() + "(" + left.toString().trim() + "," + right.toString().trim() + ")";
	}

	private void shift(function f1, Operation op) {
		this.left = this.copy();
		this.right = f1;
		this.op = op;
	}

	@Override
	public void plus(function f1) {
		shift(f1, Operation.Plus);
	}

	@Override
	public void mul(function f1) {
		shift(f1, Operation.Times);

	}

	@Override
	public void div(function f1) {
		shift(f1, Operation.Divid);

	}

	@Override
	public void max(function f1) {
		shift(f1, Operation.Max);

	}

	@Override
	public void min(function f1) {
		shift(f1, Operation.Min);
	}

	@Override
	public void comp(function f1) {
		shift(f1, Operation.Comp);

	}

	@Override
	public function left() {
		return this.left;
	}

	@Override
	public function right() {
		return this.right;
	}

	@Override
	public Operation getOp() {
		return this.op;
	}

}
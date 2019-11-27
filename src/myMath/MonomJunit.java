package myMath;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MonomJunit {

	 Monom a;
	 Monom	b;

		@BeforeEach
		public  void all() {
			
					
				a=new Monom(3,2);
				b=new Monom(2,2);
		}
		
	@Test
void addTest() {
		a.add(b);
	
	Monom c=new Monom(5,2);

	if(!(a.equals(c))) {
		fail("Bug Add Function Monom");
		}
		
}
	

	@Test
	void stringInputTest() {
		
		String b1="2x^2";
		
		Monom b_test = new Monom(b1);
	
		
		if(!(b_test.equals(b))) {
			fail("Bug Constractor Monom");

		}
		
	}

	
	@Test
	public void myTestMethod()
	{
	

	  try
	  {
	 final String b1="2asdx^2";
	    new Monom(b1);
	    fail("Should have thrown SomeException but did not!");
	  }
	  catch( final RuntimeException except)
	  {
	    final String msg = "Invalid input";
	    assertEquals(msg, except.getMessage());
	  }
	}

	
	@Test
	public void  stringto() {
		
		String result="3.0x^2";
		if(!result.equals(a.toString())) {
			fail("Error in Function ToString");
		}
	}

	@Test
	public void testEqual() {
		
		Monom x=new Monom(3,2);
		
		if(!(x.equals(a))) {
			
			fail("Error equal function");
		}
	}
}


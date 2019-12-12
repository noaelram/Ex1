package Ex1Testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Functions_GUI;
import Ex1.Monom;
import Ex1.Polynom;
import Ex1.function;

class Functions_GUITest {
	private static final String JSON_FILE = "conf.json";
	private static final String TEMP_FILE = "temp.txt";

	@Test
	void completeFlowTest() throws IOException {
		Functions_GUI a = FunctionsFactory();
		a.saveToFile(TEMP_FILE);
		Functions_GUI b = new Functions_GUI();
		b.initFromFile(TEMP_FILE);
		assertTrue(a.size() == b.size());
		for (int i = 5; i < a.size(); i++) {
			function f1 = a.get(i);
			function f2 = b.get(i);
			assertTrue(f1.equals(f2), f1.toString() + "@" + f2.toString() + "@" + i);
		}
	}

	@AfterEach
	void cleanUp() {
		try {
			new File(JSON_FILE).delete();
		} catch (Exception e) {
		}
		try {
			new File(TEMP_FILE).delete();
		} catch (Exception e) {
		}
	}

	@Test
	void readJsonTest() throws IOException, ParseException {
		String str = "{\"Width\":1,\"Height\":2,\"Range_X\":[3,4],\"Range_Y\":[5,6], \"Resolution\":7}";
		BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE));
		writer.write(str);
		writer.close();
		Functions_GUI f = new Functions_GUI();
		f.parseJson(JSON_FILE);
		assertEquals(f.getWidth(), 1);
		assertEquals(f.getHeight(), 2);
		assertEquals(f.getRx().get_min(), 3);
		assertEquals(f.getRx().get_max(), 4);
		assertEquals(f.getRy().get_min(), 5);
		assertEquals(f.getRy().get_max(), 6);
		assertEquals(f.getResolution(), 7);
	}

	@Test
	void shouldThrow1() throws IOException, ParseException {
		String str = "{}";
		BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE));
		writer.write(str);
		writer.close();
		Functions_GUI f = new Functions_GUI();
		assertThrows(RuntimeException.class, () -> {
			f.parseJson(JSON_FILE);
		});
	}

	@Test
	void shouldThrow2() throws IOException, ParseException {
		String str = "{\"width\":1,}";
		BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE));
		writer.write(str);
		writer.close();
		Functions_GUI f = new Functions_GUI();
		assertThrows(RuntimeException.class, () -> {
			f.parseJson(JSON_FILE);
		});
	}

	public static Functions_GUI FunctionsFactory() {
		Functions_GUI ans = new Functions_GUI();
		String s1 = "3.1 +2.4x^2 -x^4";
		String s2 = "5 +2x -3.3x +0.1x^5";
		String[] s3 = { "x +3", "x -2", "x -4" };
		Polynom p1 = new Polynom(s1);
		Polynom p2 = new Polynom(s2);
		Polynom p3 = new Polynom(s3[0]);
		ComplexFunction cf3 = new ComplexFunction(p3);
		for (int i = 1; i < s3.length; i++) {
			cf3.mul(new Polynom(s3[i]));
		}

		ComplexFunction cf = new ComplexFunction("plus", p1, p2);
		ComplexFunction cf4 = new ComplexFunction("div", new Polynom("x +1"), cf3);
		cf4.plus(new Monom("2"));
		ans.add(cf.copy());
		ans.add(cf4.copy());
		cf.div(p1);
		ans.add(cf.copy());
		String s = cf.toString();
		function cf5 = cf4.initFromString(s1);
		function cf6 = cf4.initFromString(s2);
		ans.add(cf5.copy());
		ans.add(cf6.copy());
		ComplexFunction max = new ComplexFunction(ans.get(0).copy());
		ComplexFunction min = new ComplexFunction(ans.get(0).copy());
		for (int i = 1; i < ans.size(); i++) {
			max.max(ans.get(i));
			min.min(ans.get(i));
		}
		ans.add(max);
		ans.add(min);

		return ans;
	}
}

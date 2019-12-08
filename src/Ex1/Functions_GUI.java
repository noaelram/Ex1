package Ex1;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Functions_GUI extends LinkedList<function> implements functions {
	private static final int DEFAULT_WIDTH = 1000;
	private static final int DEFAULT_HEIGHT = 600;
	private static final Range DEFAULT_RX = new Range(-10, 10);
	private static final Range DEFAULT_RY = new Range(-5, 15);
	private static final int DEFAULT_RESOLUTION = 200;
	private static Color[] Colors = { Color.blue, Color.cyan, Color.MAGENTA, Color.ORANGE, Color.red, Color.GREEN,
			Color.PINK };

	private int width;
	private int height;
	private Range rx;
	private Range ry;
	private int resolution;

	@Override
	public void initFromFile(String file) throws IOException {
		FileInputStream fstream = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			function f = null;
			// try Monom
			try {
				f = new Monom(strLine);
				this.add(f);
				continue;
			} catch (Exception e) {
			}
			// try Polynom
			try {
				f = new Polynom(strLine);
				this.add(f);
				continue;
			} catch (Exception e) {
			}

			// try ComplexFunction
			try {
				f = new ComplexFunction(strLine);
				this.add(f);
				continue;
			} catch (Exception e) {
			}

			// else throw bad format
			throw new IOException("bad file format -> " + file);
		}
		fstream.close();
	}

	@Override
	public void saveToFile(String file) throws IOException {
		File fout = new File(file);
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		for (function f : this) {
			bw.write(f.toString());
			bw.newLine();
		}
		bw.close();
	}

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());
		final int n = resolution * 2;
		//////// vertical lines
		StdDraw.setPenColor(Color.LIGHT_GRAY);
		for (double x = rx.get_min(); x <= rx.get_max(); x++) {
			StdDraw.line(x, ry.get_min(), x, ry.get_max());
		}
		//////// horizontal lines
		for (double y = ry.get_min(); y <= ry.get_max(); y++) {
			StdDraw.line(rx.get_min(), y, rx.get_max(), y);
		}
		//////// x axis
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.005);
		StdDraw.line(rx.get_min(), 0, rx.get_max(), 0);
		StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 15));
		for (double x = rx.get_min(); x <= rx.get_max(); x++) {
			StdDraw.text(x - 0.07, -0.07, Double.toString(x));
		}
		//////// y axis
		StdDraw.line(0, ry.get_min(), 0, ry.get_max());
		for (double y = ry.get_min(); y <= ry.get_max(); y++) {
			StdDraw.text(0 - 0.07, y + 0.07, Double.toString(y));
		}

		for (int j = 0; j < this.size(); j++) {
			function f = this.get(j);

			double[] x = new double[n + 1];
			double[] y = new double[n + 1];
			for (int i = 0; i <= n; i++) {
				x[i] = rx.get_min() + (((double) (rx.get_max() - rx.get_min())) * (i * 1.0) / ((double) n));
				y[i] = f.f(x[i]);
			}

			Color c = Colors[j % Colors.length];

			System.out.println(j + ") " + c + " f(x)= " + f.toString());

			StdDraw.setPenColor(c);
			// plot the approximation to the function
			for (int i = 0; i < n; i++) {
				StdDraw.line(x[i], y[i], x[i + 1], y[i + 1]);
			}

		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Range getRx() {
		return rx;
	}

	public Range getRy() {
		return ry;
	}

	public int getResolution() {
		return resolution;
	}

	public void drawFunction() {
		drawFunctions(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_RX, DEFAULT_RY, DEFAULT_RESOLUTION);
	}

	public void parseJson(String json_file) throws IOException, ParseException {
		Reader reader = new FileReader(json_file);
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(reader);
		int width = ((Long) jsonObject.get("Width")).intValue();
		int height = ((Long) jsonObject.get("Height")).intValue();
		int resolution = ((Long) jsonObject.get("Resolution")).intValue();
		JSONArray rxo = (JSONArray) jsonObject.get("Range_X");
		JSONArray ryo = (JSONArray) jsonObject.get("Range_Y");
		Iterator<Long> i1 = rxo.iterator();
		Iterator<Long> i2 = ryo.iterator();
		Range rx = new Range(i1.next(), i1.next());
		Range ry = new Range(i2.next(), i2.next());
		this.width = width;
		this.height = height;
		this.rx = rx;
		this.ry = ry;
		this.resolution = resolution;
	}

	@Override
	public void drawFunctions(String json_file) {
		try {
			parseJson(json_file);
			drawFunctions(width, height, rx, ry, resolution);
		} catch (Exception e) {
			drawFunction();
		}
	}
}
package com.github.okamumu.jmtrandom;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.PrintWriter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TestMT32 {
	private static String path1 = "mt19937ar-cok.out.txt";
	private static File outfile;

	@ClassRule
	public static TemporaryFolder tempFolder = new TemporaryFolder();

	@BeforeClass
	public static void before() throws IOException {
		outfile = tempFolder.newFile();
		BufferedWriter bw = Files.newBufferedWriter(Paths.get(outfile.getPath()), StandardCharsets.UTF_8);
		PrintWriter out = new PrintWriter(bw, true);

		int[] key = new int [] {0x123, 0x234, 0x345, 0x456};
		MT32 mt = new MT32(key);

		out.println("1000 outputs of genrand_int32()");
		for (int i=0; i<1000; i++) {
			out.printf("%10s ", Integer.toUnsignedString(mt.genRand32()));
			if (i % 5 == 4) {
				out.println();
			}
		}

		out.println();

		out.println("1000 outputs of genrand_real2()");
		for (int i=0; i<1000; i++) {
			out.printf("%10.8f ", mt.genRandRealc0o1());
			if (i % 5 == 4) {
				out.println();
			}
		}

		out.close();
	}

	@Test
	public void test() throws IOException {
		String s1 = Files.lines(Paths.get(path1), StandardCharsets.UTF_8).collect(Collectors.joining(System.getProperty("line.separator")));
		String s2 = Files.lines(Paths.get(outfile.getPath()), StandardCharsets.UTF_8).collect(Collectors.joining(System.getProperty("line.separator")));
		assertEquals("Compare the outputs", s1, s2);
	}
}

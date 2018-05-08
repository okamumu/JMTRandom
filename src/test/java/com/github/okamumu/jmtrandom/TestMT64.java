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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TestMT64 {
	private static String path1 = "mt19937-64.out.txt";
	private static File outfile;

	@ClassRule
	public static TemporaryFolder tempFolder = new TemporaryFolder();

	@BeforeClass
	public static void before() throws IOException {
		outfile = tempFolder.newFile();
		BufferedWriter bw = Files.newBufferedWriter(Paths.get(outfile.getPath()), StandardCharsets.UTF_8);
		PrintWriter out = new PrintWriter(bw, true);

		long[] key = new long [] {0x12345L, 0x23456L, 0x34567L, 0x45678L};
		MT64 mt = new MT64(key);

		out.println("1000 outputs of genrand64_int64()");
		for (int i=0; i<1000; i++) {
			out.printf("%20s ", Long.toUnsignedString(mt.genRand64()));
			if (i % 5 == 4) {
				out.println();
			}
		}

		out.println();
		out.println("1000 outputs of genrand64_real2()");
		for (int i=0; i<1000; i++) {
			out.printf("%10.8f ", mt.genRandRealc0o1());
			if (i % 5 == 4) {
				out.println();
			}
		}
	}

	@Test
	public void test() throws IOException {
		String s1 = Files.lines(Paths.get(path1), StandardCharsets.UTF_8).collect(Collectors.joining(System.getProperty("line.separator")));
		String s2 = Files.lines(Paths.get(outfile.getPath()), StandardCharsets.UTF_8).collect(Collectors.joining(System.getProperty("line.separator")));
		assertEquals("Compare the outputs", s1, s2);
	}
}

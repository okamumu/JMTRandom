package com.github.okamumu.jmtrandom;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Rule;

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

public class TestSFMT32 {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	public void test(SFMTParams p, PrintWriter out) {
		out.printf("%s\n32 bit generated randoms\n", p.SFMT_IDSTR);

		SFMT32 mt = new SFMT32(1234, p);
		out.println("init_gen_rand__________");
		for (int i=0; i<1000; i++) {
			out.printf("%10s ", Integer.toUnsignedString(mt.genRand32()));
			if (i % 5 == 4) {
				out.println();
			}
		}
		out.println();

		int[] key = new int [] {0x1234, 0x5678, 0x9abc, 0xdef0};
		mt = new SFMT32(key, p);
		out.println("init_by_array__________");
		for (int i=0; i<1000; i++) {
			out.printf("%10s ", Integer.toUnsignedString(mt.genRand32()));
			if (i % 5 == 4) {
				out.println();
			}
		}
	}

	public void test64(SFMTParams p, PrintWriter out) {
		out.printf("%s\n64 bit generated randoms with SFMT32\n", p.SFMT_IDSTR);

		SFMT32 mt = new SFMT32(1234, p);
		out.println("1000 outputs of genrand_int64() from a seed 1234");
		for (int i=0; i<1000; i++) {
			out.printf("%20s ", Long.toUnsignedString(mt.genRand64()));
			if (i % 5 == 4) {
				out.println();
			}
		}
		out.println();

		int[] key = new int [] {0x1234, 0x5678, 0x9abc, 0xdef0};
		mt = new SFMT32(key, p);
		out.println("1000 outputs of genrand_int64() from an array {0x1234, 0x5678, 0x9abc, 0xdef0}");
		for (int i=0; i<1000; i++) {
			out.printf("%20s ", Long.toUnsignedString(mt.genRand64()));
			if (i % 5 == 4) {
				out.println();
			}
		}
	}

	@Test
	public void test607() throws IOException {
		File outfile = tempFolder.newFile();
		BufferedWriter bw = Files.newBufferedWriter(Paths.get(outfile.getPath()), StandardCharsets.UTF_8);
		PrintWriter out = new PrintWriter(bw, true);
		test(SFMTParams.Params607, out);

		String s1 = Files.lines(Paths.get("SFMT.607.out.txt"), StandardCharsets.UTF_8).collect(Collectors.joining(System.getProperty("line.separator")));
		String s2 = Files.lines(Paths.get(outfile.getPath()), StandardCharsets.UTF_8).collect(Collectors.joining(System.getProperty("line.separator")));
		assertEquals("Compare the outputs", s1, s2);
	}

	@Test
	public void test1279() throws IOException {
		File outfile = tempFolder.newFile();
		BufferedWriter bw = Files.newBufferedWriter(Paths.get(outfile.getPath()), StandardCharsets.UTF_8);
		PrintWriter out = new PrintWriter(bw, true);
		test(SFMTParams.Params1279, out);

		String s1 = Files.lines(Paths.get("SFMT.1279.out.txt"), StandardCharsets.UTF_8).collect(Collectors.joining(System.getProperty("line.separator")));
		String s2 = Files.lines(Paths.get(outfile.getPath()), StandardCharsets.UTF_8).collect(Collectors.joining(System.getProperty("line.separator")));
		assertEquals("Compare the outputs", s1, s2);
	}
}

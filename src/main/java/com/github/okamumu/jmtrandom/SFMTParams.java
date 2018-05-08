package com.github.okamumu.jmtrandom;

public enum SFMTParams {

	Params607(607,
			2, 15, 3, 13, 3,
			0xfdff37ff, 0xef7f3f7d, 0xff777b7d, 0x7ff7fb2f,
			0x00000001, 0x00000000, 0x00000000, 0x5986f054,
			"SFMT-607:2-15-3-13-3:fdff37ff-ef7f3f7d-ff777b7d-7ff7fb2f"
	),
	Params1279(1279,
			7, 14, 3, 5, 1,
			0xf7fefffd, 0x7fefcfff, 0xaff3ef3f, 0xb5ffff7f,
			0x00000001, 0x00000000, 0x00000000, 0x20000000,
			"SFMT-1279:7-14-3-5-1:f7fefffd-7fefcfff-aff3ef3f-b5ffff7f"
	),
	Params2281(2281,
			12, 19, 1, 5, 1,
			0xbff7ffbf, 0xfdfffffe, 0xf7ffef7f, 0xf2f7cbbf,
			0x00000001, 0x00000000, 0x00000000, 0x41dfa600,
			"SFMT-2281:12-19-1-5-1:bff7ffbf-fdfffffe-f7ffef7f-f2f7cbbf"
	),
	Params4253(4253,
			17, 20, 1, 7, 1,
			0x9f7bffff, 0x9fffff5f, 0x3efffffb, 0xfffff7bb,
			0xa8000001, 0xaf5390a3, 0xb740b3f8, 0x6c11486d,
			"SFMT-4253:17-20-1-7-1:9f7bffff-9fffff5f-3efffffb-fffff7bb"
	),
	Params11213(11213,
			68, 14, 3, 7, 3,
			0xeffff7fb, 0xffffffef, 0xdfdfbfff, 0x7fffdbfd,
			0x00000001, 0x00000000, 0xe8148000, 0xd0c7afa3,
			"SFMT-11213:68-14-3-7-3:effff7fb-ffffffef-dfdfbfff-7fffdbfd"
	),
	Params19937(19937,
			122, 18, 1, 11, 1,
			0xdfffffef, 0xddfecb7f, 0xbffaffff, 0xbffffff6,
			0x00000001, 0x00000000, 0x00000000, 0x13c9e684,
			"SFMT-19937:122-18-1-11-1:dfffffef-ddfecb7f-bffaffff-bffffff6"
	),
	Params44497(44497,
			330, 5, 3, 9, 3,
			0xeffffffb, 0xdfbebfff, 0xbfbf7bef, 0x9ffd7bff,
			0x00000001, 0x00000000, 0xa3ac4000, 0xecc1327a,
			"SFMT-44497:330-5-3-9-3:effffffb-dfbebfff-bfbf7bef-9ffd7bff"
	),
	Params86243(86243,
			366, 6, 7, 19, 1,
			0xfdbffbff, 0xbff7ff3f, 0xfd77efff, 0xbf9ff3ff,
			0x00000001, 0x00000000, 0x00000000, 0xe9528d85,
			"SFMT-86243:366-6-7-19-1:fdbffbff-bff7ff3f-fd77efff-bf9ff3ff"
	),
	Params132049(132049,
			110, 19, 1, 21, 1,
			0xffffbb5f, 0xfb6ebf95, 0xfffefffa, 0xcff77fff,
			0x00000001, 0x00000000, 0xcb520000, 0xc7e91c7d,
			"SFMT-132049:110-19-1-21-1:ffffbb5f-fb6ebf95-fffefffa-cff77fff"
	),
	Params216091(216091,
			627, 11, 3, 10, 1,
			0xbff7bff7, 0xbfffffff, 0xbffffa7f, 0xffddfbfb,
			0xf8000001, 0x89e80709, 0x3bd2b64b, 0x0c64b1e4,
			"SFMT-216091:627-11-3-10-1:bff7bff7-bfffffff-bffffa7f-ffddfbfb"
	),
	;

	public final int SFMT_MEXP;

	public final int SFMT_POS1;
	public final int SFMT_SL1;
	public final int SFMT_SL2;
	public final int SFMT_SR1;
	public final int SFMT_SR2;

	public final int SFMT_MSK1;
	public final int SFMT_MSK2;
	public final int SFMT_MSK3;
	public final int SFMT_MSK4;

	public final int SFMT_PARITY1;
	public final int SFMT_PARITY2;
	public final int SFMT_PARITY3;
	public final int SFMT_PARITY4;

	public final int SFMT_N;
	public final int SFMT_N32;
	public final int SFMT_N64;
	
	public final String SFMT_IDSTR;

	private SFMTParams(int mexp, int pos1, int sl1, int sl2, int sr1, int sr2,
			int msk1, int msk2, int msk3, int msk4,
			int parity1, int parity2, int parity3, int parity4,
			String id) {
		SFMT_MEXP = mexp;
		SFMT_N = SFMT_MEXP / 128 + 1;
		SFMT_N32 = SFMT_N * 4;
		SFMT_N64 = SFMT_N * 2;
		SFMT_POS1 = pos1;
		SFMT_SL1 = sl1;
		SFMT_SL2 = sl2;
		SFMT_SR1 = sr1;
		SFMT_SR2 = sr2;
		SFMT_MSK1 = msk1;
		SFMT_MSK2 = msk2;
		SFMT_MSK3 = msk3;
		SFMT_MSK4 = msk4;
		SFMT_PARITY1 = parity1;
		SFMT_PARITY2 = parity2;
		SFMT_PARITY3 = parity3;
		SFMT_PARITY4 = parity4;
		SFMT_IDSTR = id;
	}
	
}

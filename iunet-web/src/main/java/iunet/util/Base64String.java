package iunet.util;

class Base64String
{	
	static String S = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
	static char[] CA = new char[S.length()];
	static char[] IA = new char[256];
    static
	{
		char c = '0';
        for (char i = 0; i < S.length(); i++) {
            c = S.charAt(i);
            CA[i] = c;
            IA[c] = i;
        }
	}
    
	public static String toBase64String(byte[] b,boolean wrap) {
	  
	    // Check special case
	    int bLen = (b!=null) ? b.length : 0;
	    if (bLen == 0) { return ""; }
	    // Length of even 24-bits.
	    int eLen = (int)(Math.floor(bLen / 3) * 3);
	    // Returned character count.
	    int cCnt = ((bLen - 1) / 3 + 1) << 2;
	    int dLen = cCnt + (wrap ? (cCnt - 1) / 76 << 1 : 0); // Length of returned array
	    char[] dArr = new char[dLen];
	    // Encode even 24-bits.
	    for (int s = 0, d = 0, cc = 0; s < eLen; ) {
	        // Copy next three bytes into lower 24 bits of int, paying attension to sign.
	    	int i = (b[s++] & 0xff) << 16 | (b[s++] & 0xff) << 8 | (b[s++] & 0xff);
	        // Encode the int into four chars.
	        dArr[d++] = CA[(i >>> 18) & 0x3f];
	        dArr[d++] = CA[(i >>> 12) & 0x3f];
	        dArr[d++] = CA[(i >>> 6) & 0x3f];
	        dArr[d++] = CA[i & 0x3f];
	        // Add optional line separator as specified in RFC 2045.
	        if (wrap && ++cc == 19 && d < dLen - 2) {
	            dArr[d++] = '\r';
	            dArr[d++] = '\n';
	            cc = 0;
	        }
	    }
	    // Pad and encode last bits if source isn't even 24 bits.
	    int left = bLen - eLen; // 0 - 2.
	    if (left > 0) {
	        // Prepare the int.
	    	int i = ((b[eLen] & 0xff) << 10) | (left == 2 ? ((b[bLen - 1] & 0xff) << 2) : 0);
	        // Set last four chars.
	        dArr[dLen - 4] = CA[i >> 12];
	        dArr[dLen - 3] = CA[(i >>> 6) & 0x3f];
	        dArr[dLen - 2] = (left == 2) ? CA[i & 0x3f] : '=';
	        dArr[dLen - 1] = '=';
	    }
	    return StringUtil.join(dArr,"");
	}
	public static byte[] fromBase64String(String s) {
	   	    
	    int sLen = s.length();
	    if (sLen == 0) return new byte[0];
	    // Start and end index after trimming.
	    int sIx = 0, eIx = sLen - 1;
	    // Get the padding count (=) (0, 1 or 2).
	    int pad = s.charAt(eIx) == '=' ? (s.charAt(eIx - 1) == '=' ? 2 : 1) : 0;  // Count '=' at end.
	    // Content count including possible separators.
	    int cCnt = eIx - sIx + 1;
	    int sepLn = (sLen > 76 && s.charAt(76) == '\r') ? (cCnt / 78) : 0;
	    int sepCnt = sLen > 76 ? (sepLn << 1) : 0;
	    // The number of decoded bytes.
	    int len = ((cCnt - sepCnt) * 6 >> 3) - pad;
	    // Preallocate byte[] of exact length.
	    byte[] bytes = new byte[len];
	    // Decode all but the last 0 - 2 bytes.
	    int d = 0;
	    int eLen = (int)(Math.floor(len / 3) * 3);
	    for (int cc = 0; d < eLen; ) {
	        // Assemble three bytes into an var from four "valid" characters.
	        int i = IA[s.charAt(sIx++)] << 18 |
				IA[s.charAt(sIx++)] << 12 |
				IA[s.charAt(sIx++)] << 6 |
				IA[s.charAt(sIx++)];
	        // Add the bytes
	        bytes[d++] = (byte)(i >> 16);
	        bytes[d++] = (byte)((i & 0xFFFF) >> 8);
	        bytes[d++] = (byte)(i & 0xFF);
	        // If line separator, jump over it.
	        if (sepCnt > 0 && ++cc == 19) {
	            sIx += 2;
	            cc = 0;
	        }
	    }
	    if (d < len) {
	        // Decode last 1-3 bytes (incl '=') into 1-3 bytes.
	    	int i = 0;
	        for (int j = 0; sIx <= (eIx - pad); j++) {
	            i |= IA[s.charAt(sIx++)] << (18 - j * 6);
	        }
	        for (int r = 16; d < len; r -= 8) {
	        	int cropBits = (int)(Math.pow(2, r + 8) - 1);
	            bytes[d++] = (byte)((i & cropBits) >> r);
	        }
	    }
	    return bytes;
	}
}
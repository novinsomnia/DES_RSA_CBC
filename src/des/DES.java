package des;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class DES {
	
	/*-------------general functions--------------*/
	
	int[] XOR(int d1[], int d2[]) {
		int[] newdata = new int[d1.length];
		for (int i = 0; i < d1.length; i++) {
			newdata[i] = (d1[i] + d2[i]) % 2;
		}
		return newdata;
	}
	
	int[] stringToBits(String data) {// from 8 chars to 64 bits
		byte[] test = data.getBytes();
		int[] IntVa = new int[64];
		int[] IntDa = new int[8];
		
		for (int i = 0; i < 8; i++) {
			IntDa[i] = test[i];
			if (IntDa[i] < 0) {
				IntDa[i] += 256;
				IntDa[i] %= 256;
			}
		}
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				IntVa[i*8+7-j] = IntDa[i] % 2;
				IntDa[i] = IntDa[i] / 2;
			}
		}
		return IntVa;
	}
	
	int[] binstrToArray(String data) {
		int[] newdata = new int[data.length()];
		for (int i = 0; i < data.length(); i++){
			newdata[i] = Integer.parseInt(String.valueOf(data.charAt(i)));
		}
		return newdata;
	}
	
	String arrayToBinstr(int[] data) {
		String newdata = "";
		for (int i = 0; i < data.length; i++) {
			newdata += String.valueOf(data[i]);
		}
		return newdata;
	}
	
	int[] intToArray(int n) {
		int[] result = new int[8];
		for (int i = 0; i < 8; i++) {
			result[i] = (n >> (7-i)) % 2;
		}
		return result;
	}
	
	int arrayToInt(int[] data) {
		int result = 0;
		for (int i = 0; i < 8; i++) {
			result += data[i] * (1 << (7-i));
		}
		return result;
	}
	
	/*-------------DES functions--------------*/
	
	int[] IPtable = {57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 
			61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7,
			56, 48, 40, 32, 24, 16, 8, 0, 58, 50, 42, 34, 26, 18, 10, 2, 
			60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6};
	
	int[] IP1table = {39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 
			37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 
			35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 
			33, 1, 41, 9, 49, 17, 57, 25, 32, 0, 40, 8, 48, 16, 56, 24};

	int[] IPCtable = {56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33, 25, 17, 9, 1, 
			58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, 
			30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 60, 52, 44, 36, 
			28, 20, 12, 4, 27, 19, 11, 3};
	
	int[] LStable = {1, 1, 2, 2, 2, 2};
	
	int[] PCtable = {13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18, 11, 3, 
			25, 7, 15, 6, 26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 
			50, 44, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31};
	
	int[][][] SBox = {
			{//SBox0
				{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
				{0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
				{4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
				{15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 }},
			{//SBox1
				{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
				{3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 }, 
				{0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 }, 
				{13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 }},
			{//SBox2
				{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 }, 
				{13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 }, 
				{13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 }, 
				{1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 }},
			{//SBox3
				{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 }, 
				{13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 }, 
				{10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },  
				{3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 }},
			{//SBox4
				{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 }, 
				{14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 }, 
				{4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 }, 
				{11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 }},
			{//SBox5
				{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 }, 
				{10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 }, 
				{9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 }, 
				{4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 }},
			{//SBox6
				{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 }, 
				{13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 }, 
				{1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 }, 
				{6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 }},
			{//SBox7
				{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 }, 
				{1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 }, 
				{7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 }, 
				{2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 }}
	};
	
	int[] Ptable = {15, 6, 19, 20, 28, 11, 27, 16, 0, 14, 22, 25, 4, 17, 30, 9, 
			1, 7, 23, 13, 31, 26, 2, 8, 18, 12, 29, 5, 21, 10, 3, 24};
	
	int[] Etable = {31, 0, 1, 2, 3, 4, 3, 4, 5, 6, 7, 8, 7, 8, 9, 10, 
			11, 12, 11, 12, 13, 14, 15, 16, 15, 16, 17, 18, 19, 20, 19, 20, 
			21, 22, 23, 24, 23, 24, 25, 26, 27, 28, 27, 28, 29, 30, 31, 0};
	
	int[] IPTransform(int data[]) {
		int[] newdata = new int[64];
		for (int i = 0; i < 64; i++) {
			newdata[i] = data[IPtable[i]];
		}
		return newdata;
	}
	
	int[] IP1Transform(int data[]) {
		int[] newdata = new int[64];
		for (int i = 0; i < 64; i++) {
			newdata[i] = data[IP1table[i]];
		}
		return newdata;
	}
	
	int[] ETransform(int data[]) {
		int[] newdata = new int[48];
		for (int i = 0; i < 48; i++) {
			newdata[i] = data[Etable[i]];
		}
		return newdata;
	}
	
	int[] SBoxTransform(int data[], int box_num) {//6-bit input
		int i = (data[0] << 1) + data[5];
		int j = (data[1] << 3) + (data[2] << 2) + (data[3] << 1) + data[4];
		int result = SBox[box_num][i][j];
		
		int[] newdata = new int[4];
		for (int k = 0; k < 4; k++) {
			newdata[3-k] = (result >> k) % 2;
		}
		return newdata;
	}
	
	int[] PTransform(int data[]) {
		int[] newdata = new int[32];
		for (int i = 0; i < 32; i++) {
			newdata[i] = data[Ptable[i]];
		}
		return newdata;
	}
	
	int[] FFunction(int data[], int key[]) {//32-bit data & 48-bit key
		int[] e = new int[48];
		int[] sboxinput = new int[48];
		int[] newdata = new int[32];
		int[] sboxresult = new int[32];
		int[] temp6 = new int[6];
		int[] temp4 = new int[4];
		
		e = ETransform(data);
		sboxinput = XOR(e, key);
		
		for (int i = 0; i < 8; i++) {
			for (int k = 0; k < 6; k++) {
				temp6[k] = sboxinput[i*6+k];
			}
			temp4 = SBoxTransform(temp6, i);
			for (int k = 0; k < 4; k++) {
				sboxresult[i*4+k] = temp4[k];
			}
		}
		
		newdata = PTransform(sboxresult);
		return newdata;
	}
	
	int[] IPCTransform(int data[]) {
		int[] newdata = new int[56];
		for (int i = 0; i < 56; i++) {
			newdata[i] = data[IPCtable[i]];
		}
		return newdata;
	}
	
	int[] PCTransform(int data[]) {
		int[] newdata = new int[48];
		for (int i = 0; i < 48; i++) {
			newdata[i] = data[PCtable[i]];
		}
		return newdata;
	}
	
	int[] leftShift(int data[], int n) {//56-bit input
		int[] newdata = new int[56];
		if (n == 1) {
			for (int i = 0; i < 27; i++) {
				newdata[i] = data[i+1];
				newdata[i+28] = data[i+28+1];
			}
			newdata[27] = data[0];
			newdata[55] = data[28];
		}
		else {
			for (int i = 0; i < 26; i++) {
				newdata[i] = data[i+2];
				newdata[i+28] = data[i+28+2];
			}
			newdata[26] = data[0];
			newdata[27] = data[1];
			newdata[54] = data[28];
			newdata[55] = data[29];
		}
		return newdata;
	}
	
	int[][] generateSubkey(int[] key) {//64-bit key
		int[][] subkey = new int[6][48];
		int[][] temp = new int[6][56];
		int[] k0 = new int[56];
		
		k0 = IPCTransform(key);
		temp[0] = leftShift(k0, LStable[0]);
		
		for (int i = 1; i < 6; i++) {
			temp[i] = leftShift(temp[i-1], LStable[i]);
		}
		
		for (int i = 0; i < 6; i++) {
			subkey[i] = PCTransform(temp[i]);
		}
		
		return subkey;
	}
	
	
	/*-------------encode and decode--------------*/
	
	int[] encode(int[] data, int[][] subkey) {
		int[] newdata = new int[64];
		int[][] L = new int[7][32];
		int[][] R = new int[7][32];
		int[] temp64 = new int[64];
		int[] temp32 = new int[32];
		
		temp64 = IPTransform(data);
		
		System.arraycopy(temp64, 0, L[0], 0, 32);
		System.arraycopy(temp64, 32, R[0], 0, 32);
		
		
		for (int i = 0; i < 6; i++) {
			System.arraycopy(R[i], 0, L[i+1], 0, 32);
			temp32 = FFunction(R[i], subkey[i]);
			temp32 = XOR(L[i], temp32);
			System.arraycopy(temp32, 0, R[i+1], 0, 32);
		}
		
		System.arraycopy(L[6], 0, temp64, 0, 32);
		System.arraycopy(R[6], 0, temp64, 32, 32);
		newdata = IP1Transform(temp64);
		
		return newdata;
	}
	
	int[] decode(int[] data, int[][] subkey) {
		int[] newdata = new int[64];
		int[][] L = new int[7][32];
		int[][] R = new int[7][32];
		int[] temp64 = new int[64];
		int[] temp32 = new int[32];
		
		temp64 = IPTransform(data);
		
		System.arraycopy(temp64, 0, L[0], 0, 32);
		System.arraycopy(temp64, 32, R[0], 0, 32);
				
		for (int i = 0; i < 6; i++) {
			System.arraycopy(L[i], 0, R[i+1], 0, 32);
			temp32 = FFunction(L[i], subkey[5-i]);
			temp32 = XOR(R[i], temp32);
			System.arraycopy(temp32, 0, L[i+1], 0, 32);
		}
		
		System.arraycopy(L[6], 0, temp64, 0, 32);
		System.arraycopy(R[6], 0, temp64, 32, 32);
		newdata = IP1Transform(temp64);
		
		return newdata;
	}
	
	
	/*-------------execute--------------*/
	
	int[] generateIV() {
		int[] iv = new int[64];
		String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
	    StringBuffer sb = new StringBuffer();  
	    Random random = new Random();  
	    for (int i = 0; i < 8; i++) {  
	        sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));  
	    }  
	    String ivstr = sb.toString(); 
		iv = stringToBits(ivstr);
		
		return iv;
	}
	
	String execute(String datastr, String keystr, boolean e) {
		String output = "";
		
		// generate the subkeys
		int[] key = stringToBits(keystr);
		int[][] subkey = generateSubkey(key);
		
		if (e) {
			// set the IV
			int[] iv = generateIV();
			
			output += arrayToBinstr(encode(iv, subkey));
			
			int[] data = binstrToArray(datastr);
			int[] desinput = new int[64];
			int[] temp = iv;
			
			int i;
			for (i = 0; (i+63) < data.length; i += 64) {
				System.arraycopy(data, i, desinput, 0, 64);
				desinput = XOR(temp, desinput);
				temp = encode(desinput, subkey);
				output +=arrayToBinstr(temp);
			}
			
			// add some bits
			int index = data.length - i;
			if (index > 0) {
				System.arraycopy(data, i, desinput, 0, index);
				int n = 8 - (data.length - i) / 8;
				int[] add = intToArray(n); // 8 bits
				for (int j = 0; j < n; j++) {
					System.arraycopy(add, 0, desinput, index, 8);
					index += 8;
				}
				desinput = XOR(temp, desinput);
				temp = encode(desinput, subkey);
				output += arrayToBinstr(temp);
			}
		}
		else {
			int[] data = binstrToArray(datastr);
			int[] desinput = new int[64];
			int[] temp = new int[64];
			
			// get iv
			System.arraycopy(data, 0, temp, 0, 64);
			temp = decode(temp, subkey); // iv
			
			int i;
			for (i = 64; (i+64) < data.length; i+=64) {
				System.arraycopy(data, i, desinput, 0, 64);
				desinput = decode(desinput, subkey);
				desinput = XOR(temp, desinput);
				output += arrayToBinstr(desinput);
				System.arraycopy(data, i, temp, 0, 64);
			}
			
			System.arraycopy(data, i, desinput, 0, 64);
			desinput = decode(desinput, subkey);
			desinput = XOR(temp, desinput);
			
			int[] add = new int[8];
			int[] addconfirm = new int[8];
			System.arraycopy(desinput, 56, add, 0, 8);
			System.arraycopy(desinput, 48, addconfirm, 0, 8);
			int n = arrayToInt(add);
			if (n < 8 && n > 0) {
				int j;
				for (j = 0; j < 8; j++) {
					if (add[j] != addconfirm[j]) 
						break;
				}
				if (j == 8) {
					int[] subarray = new int[64-n*8];
					System.arraycopy(desinput, 0, subarray, 0, 64-n*8);
					output += arrayToBinstr(subarray);
				}
				else {
					output += arrayToBinstr(desinput);
				}
			}
			else {
				output += arrayToBinstr(desinput);
			}
		}
		return output;
	}
}

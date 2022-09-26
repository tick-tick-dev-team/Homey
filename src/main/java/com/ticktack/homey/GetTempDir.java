package com.ticktack.homey;

public class GetTempDir {
	
	public static void main(String[] args) {
		String tempDir = System.getProperty("java.io.tmpdir");
		System.out.println("tempDir = " + tempDir);
	}
}
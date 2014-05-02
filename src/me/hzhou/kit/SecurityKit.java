package me.hzhou.kit;

import me.hzhou.config.Const;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SecurityKit {

	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random rnd = new Random();
	/**
	 * 
	 * @param rawStr
	 * @return md5 encryption of rawStr
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5(String rawStr) {
		
		String original = rawStr;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(original.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 
	 * @param rawStr
	 * @return
	 */
	public static String encryption(String rawStr){
		return getMD5(getMD5(Const.SALT_FOR_MD5)+getMD5(rawStr));
	}
	
	/**
	 * 
	 * @param len
	 * @return
	 */
	public static String randomString( int len ) {
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
	public static String randomString( ) {
		return randomString(6);
	}
	
	
	public static void main(String[] args){
		System.out.println(SecurityKit.encryption("zhouhao"));
		System.out.println(SecurityKit.randomString());
		
	}
	
}

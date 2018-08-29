package feng.app.service.util;

import java.security.MessageDigest;

import feng.app.repository.user.entity.ApplicationUser;

public abstract class PasswordUtil {

	public static boolean vaild(ApplicationUser user, String password) {
		String md5Password = md5(user.getUsername() + md5(password));
		return md5Password.equals(user.getPassword());
	}

	private static String md5(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(text.getBytes("utf-8"));
			return toHex(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String toHex(byte[] bytes) {
		final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			ret.append(HEX_DIGITS[(b >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[b & 0x0f]);
		}
		return ret.toString();
	}
}

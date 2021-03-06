package org.oneCable.java.utils;



import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;



@SuppressWarnings(value = { "rawtypes" })
public class AppUtility {

	private static ResourceBundle resourceBundle = null;
	public static final int defaultDecimalPrecision = 2;

	private static int decimalPercision = defaultDecimalPrecision;

	private static String DATE_FORMAT = "dd/MM/yyyy";
	private static String  READ_DATE_FORMAT= "EEE MMM dd yyyy HH:mm:ss z";
	private static String DATE_DB_FORMAT = "yyyy-MM-dd";

	public static String formatedDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(date);
	}

	public static Date formatedStringToDate(String date) throws ParseException {
		SimpleDateFormat readFormate = new SimpleDateFormat(READ_DATE_FORMAT);
		SimpleDateFormat writeFormat = new SimpleDateFormat(DATE_DB_FORMAT);
		Date returnDate = null;
		if (!AppUtility.isEmpty(date)) {
			Date readendDate = readFormate.parse(date);
			String formatedDate = writeFormat.format(readendDate);
			returnDate = writeFormat.parse(formatedDate);
		}
		return returnDate;
	}
	
	public static Date formatDateStringToDate(String date) throws ParseException {
		SimpleDateFormat writeFormat = new SimpleDateFormat(DATE_FORMAT);
		Date returnDate = null;
		if (!AppUtility.isEmpty(date)) {
			//Date readendDate = writeFormat.parse(date);
		//	String formatedDate = writeFormat.format(date);
			returnDate = writeFormat.parse(date);
		}
		return returnDate;
	}

	public static Timestamp getCurrentTimeStamp() {

		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public static String getDeleteStamp() {
		return "_Del_" + Calendar.getInstance().getTimeInMillis();
	}

	public static int getDecimalPercision() {
		return decimalPercision;
	}

	public static void setDecimalPercision(int decimalPercision) {
		AppUtility.decimalPercision = decimalPercision;
	}

	public static ResourceBundle getResourceBundle() {
		if (null == resourceBundle) {
			resourceBundle = ResourceBundle.getBundle("GUIConstants.MESSAGE_BUNDLE", Locale.US);
		}
		return resourceBundle;
	}

	public static BigDecimal covertToBigDecimal(String val) {
		if (isEmpty(val) && isBigDecimal(val)) {
			return new BigDecimal(val);
		}
		return BigDecimal.ZERO;
	}

	public static Long covertToLong(String val) {
		if (isEmpty(val) && isNumeric(val)) {
			return new Long(val);
		}
		return new Long("0");
	}

	public static BigInteger covertToBigInteger(String val) {
		if (isEmpty(val) && isNumeric(val)) {
			return new BigInteger(val);
		}
		return BigInteger.ZERO;
	}

	public static Double covertToDouble(String val) {
		if (isEmpty(val) && isDouble(val)) {
			return new Double(val);
		}
		return new Double("0");
	}

	public static Integer covertToInteger(String val) {
		if (isEmpty(val) && isNumeric(val)) {
			return new Integer(val);
		}
		return new Integer("0");
	}

	public static String formatDoubleToStringWithPrecision(Double d) {
		String str = "###,###,###.####";
		if (AppUtility.getDecimalPercision() == 2) {
			str = "###,###,###.##";
		} else if (AppUtility.getDecimalPercision() == 3) {
			str = "###,###,###.###";
		}
		DecimalFormat df = new DecimalFormat(str);
		df.setRoundingMode(RoundingMode.FLOOR);
		return df.format(d);
	}

	public static String formatDoubleToStringWithoutCommasWithPrecision(Double d) {
		String str = "#########.####";
		if (AppUtility.getDecimalPercision() == 2) {
			str = "#########.##";
		} else if (AppUtility.getDecimalPercision() == 3) {
			str = "#########.###";
		}
		DecimalFormat df = new DecimalFormat(str);
		df.setRoundingMode(RoundingMode.FLOOR);
		// String str = "%1$."+UtilityFunctions.getDecimalPercision()+"f";
		// return String.format(str,d);
		return df.format(d);
	}

	public static String formatBigDecimalToStringWithoutCommasWithPrecision(BigDecimal d) {
		String str = "%1$." + AppUtility.getDecimalPercision() + "f";
		return String.format(str, d);
	}

	public static String formatDoubleToString(Double d) {
		return String.format("%1$,.4f", d);
	}

	public static String formatDoubleToStringWithoutCommas(Double d) {
		return String.format("%1$.4f", d);
	}

	public static String formatBigDecimalToString(BigDecimal d) {
		return String.format("%1$,.4f", d);
	}

	public static String formatIntegerToString(Integer d) {
		return String.format("%,d", d);
	}

	public static String formatLongToString(Long d) {
		return String.format("%,d", d);
	}

	public static String formatBigIntegerToString(BigInteger d) {
		return String.format("%,d", d);
	}

	public static boolean isEmpty(Object object) {
		if (object == null) {
			return true;
		}
		if (object.equals("undefined")) {
			return true;
		}

		if (object instanceof Optional<?> && !((Optional<?>) object).isPresent()) {
			return true;
		}

		if (object instanceof String) {
			String objString = object.toString();
			if (objString.trim().length() <= 0) {
				return true;
			}
		}
		if (object instanceof StringBuilder) {
			StringBuilder stringBuilder = (StringBuilder) object;
			if (stringBuilder.toString().trim().length() <= 0) {
				return true;
			}
		}
		if (object instanceof ArrayList<?> || object instanceof List<?>) {
			if (((List) object).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNumeric(String value) {
		try {
			new BigInteger(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean isBigDecimal(String value) {
		try {
			new BigDecimal(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean isDouble(String value) {
		try {
			new Double(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public static boolean isDate(String value) {
		try {
			Date date = new Date(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/*
	 * public static String encrypt(String input) { MessageDigest mdEnc = null; try
	 * { mdEnc = MessageDigest.getInstance("MD5");/* Encryption } catch
	 * (NoSuchAlgorithmException ex) {
	 * Logger.getLogger(AppUtitlity2.class.getName()).log(Level.SEVERE, null, ex); }
	 * // algorithm mdEnc.update(input.getBytes(), 0, input.length()); String
	 * encryptedPassword = new BigInteger(1, mdEnc.digest()).toString(16); return
	 * encryptedPassword; }
	 * 
	 * 
	 * public static String formatWrappersToString(Object obj) { String str = ""; if
	 * (obj != null) { if (obj instanceof Double) { str =
	 * formatDoubleToString((Double) obj); } else if (obj instanceof BigDecimal) {
	 * str = formatBigDecimalToString((BigDecimal) obj); } else if (obj instanceof
	 * Integer) { str = formatIntegerToString((Integer) obj); } else if (obj
	 * instanceof BigInteger) { str = formatBigIntegerToString((BigInteger) obj); }
	 * else if (obj instanceof Long) { str = formatLongToString((Long) obj); } else
	 * if(obj instanceof Date){ str =
	 * DateTimeUtils.SIMPLE_DATE_TIME_FORMAT.format(obj); }else { str =
	 * obj.toString(); } } return str; }
	 * 
	 * public static String formatDateToString(Object obj, boolean isTimeAlso) {
	 * String str = ""; if (obj != null) { if (obj instanceof Timestamp ) { str =
	 * DateTimeUtils.SIMPLE_DATE_TIME_FORMAT.format(obj); } else if(obj instanceof
	 * Date){ str = isTimeAlso ? DateTimeUtils.SIMPLE_DATE_TIME_FORMAT.format(obj) :
	 * DateTimeUtils.SIMPLE_DATE_FORMAT.format(obj); } } return str; }
	 * 
	 * /** Change the provided date to the server's default time zone - GMT.
	 * 
	 * @param date
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public static Date convertDateToDefaultTimeZone(Date date) throws Exception {
		TimeZone zone = TimeZone.getTimeZone("GMT");
		Calendar first = Calendar.getInstance(zone);
		first.setTimeInMillis(date.getTime());

		Calendar output = Calendar.getInstance();
		output.set(Calendar.YEAR, first.get(Calendar.YEAR));
		output.set(Calendar.MONTH, first.get(Calendar.MONTH));
		output.set(Calendar.DAY_OF_MONTH, first.get(Calendar.DAY_OF_MONTH));
		output.set(Calendar.HOUR_OF_DAY, first.get(Calendar.HOUR_OF_DAY));
		output.set(Calendar.MINUTE, first.get(Calendar.MINUTE));
		output.set(Calendar.SECOND, first.get(Calendar.SECOND));

		output.set(Calendar.SECOND, first.get(Calendar.SECOND));
		output.set(Calendar.MILLISECOND, first.get(Calendar.MILLISECOND));

		return output.getTime();
	}

	public static String encodeUTF(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Nullify time in Calander object
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
//	public static Calendar nullifyTime(Calendar c) throws CustomException {
//		Calendar cal = c;
//
//		cal.set(Calendar.HOUR_OF_DAY, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//		cal.set(Calendar.MILLISECOND, 0);
//
//		return cal;
//	}

	public static String generateRandomUniqString() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	/**
	 * Private Methods
	 * 
	 * @param x
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private static byte[] computeHash(String x) throws Exception {
		java.security.MessageDigest d = null;
		d = java.security.MessageDigest.getInstance("SHA-1");
		d.reset();
		d.update(x.getBytes());
		return d.digest();
	}

	@SuppressWarnings("unused")
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

	public static Long getRoleId123(int accountType) {

		Long roleId = null;

		/*
		 * switch (accountType) { case AppConstants.ACCOUNT_TYPE_ADMIN.intValue():
		 * roleId = AppConstants.INSTITUTE_ADMIN_ROLE_ID; break; case
		 * AppConstants.ACCOUNT_TYPE_STAFF.intValue(): roleId =
		 * AppConstants.STAFF_ROLE_ID; break; case
		 * AppConstants.ACCOUNT_TYPE_PARENT.intValue(): roleId =
		 * AppConstants.PARENT_ROLE_ID; break; case
		 * AppConstants.ACCOUNT_TYPE_TEACHER.intValue(): roleId =
		 * AppConstants.TEACHER_ROLE_ID; break; case
		 * AppConstants.ACCOUNT_TYPE_STUDENT.intValue(): roleId =
		 * AppConstants.STUDENT_ROLE_ID; break; }
		 */
		return roleId;
	}

//	public static String getTokenFromAuthorizationHeader(String tokenHeader) {
//
//		return tokenHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
//	}

	public enum EnumAdmissionStatus {

		NEW(1l), // calls constructor with value 1
		APPROVED(2l), // calls constructor with value 2
		DECLINED(3l), UNDER_CONSIDERATION(4l);

		private final Long statusCode;

		private EnumAdmissionStatus(Long statusCode) {
			this.statusCode = statusCode;
		}

		public Long getStatusCode() {
			return this.statusCode;
		}
	}

}
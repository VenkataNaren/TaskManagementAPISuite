package nz.co.solnet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilsHelper {
	
	// method to convert String year to sql date
		public static java.sql.Date convertStringToDate(String date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			java.sql.Date sqlDate = null;
			try {
				Date javaDate = sdf.parse(date);
				sqlDate = new java.sql.Date(javaDate.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return sqlDate;
		}

}

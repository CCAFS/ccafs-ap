package org.cgiar.ccafs.ap.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.conversion.TypeConversionException;
import org.apache.struts2.util.StrutsTypeConverter;


public class DateConverter extends StrutsTypeConverter {

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (values != null && values.length > 0 && values[0] != null && values[0].length() > 0) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      try {
        return sdf.parse(values[0]);
      } catch (ParseException e) {
        throw new TypeConversionException(e);
      }
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    Date date = (Date) o;
    if (date == null) {
      // If there isn't an object return an empty string
      return "";
    }
    return new SimpleDateFormat("yyyy-MM-dd").format(date);
  }

}

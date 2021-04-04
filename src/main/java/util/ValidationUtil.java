package main.java.util;

import main.java.enums.Rating;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ValidationUtil {

    private static final String dateFormat = "dd-MM-yyyy";

    public static boolean isNullOrEmpty(String entity) {
        if(entity == null || entity.isEmpty())
            return true;
        else
            return false;
    }

    public static boolean isNullOrEmpty(Collection<String> entityList) {
        if(entityList == null || entityList.size()==0)
            return true;
        else
            return false;
    }

    public static <T extends Enum<T>> T parseEnum(Class<T> enumeration, String value) {
        if(isNullOrEmpty(value))
            throw new IllegalArgumentException(String.format("Value supposed to be of type %s is null", enumeration));
        for(T enumConstant : enumeration.getEnumConstants()) {
            if(enumConstant.name().equalsIgnoreCase(value))
                return enumConstant;
        }
        throw new IllegalArgumentException(String.format("Invalid value: [%s] for enum: %s", value, enumeration));
    }

    public static void parseRating(int value) {
        boolean found = false;
        Rating[] ratings = Rating.values();
        for(Rating rating : ratings) {
            if(rating.getRating()==value) {
                found = true;
                break;
            }
        }
        if(!found)
            throw new IllegalArgumentException(String.format("Rating should be in between: %s and %s", ratings[0], ratings[ratings.length-1]));
    }

    public static <T extends Enum<T>> List<T> parseEnum(Class<T> enumeration, Collection<String> values) {
        if(isNullOrEmpty(values))
            throw new IllegalArgumentException(String.format("Collection supposed to be having values of type %s is null", enumeration));
        List<T> res = new ArrayList<>();
        for(String value : values) {
            res.add(parseEnum(enumeration, value));
        }
        return res;
    }

    public static Date parseDate(String date) {
        if(isNullOrEmpty(date))
            throw new IllegalArgumentException("Date cannot be null or empty");
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try{
            return format.parse(date);
        } catch(ParseException ex) {
            throw new IllegalArgumentException("Release date entered is not in format dd-MM-yyyy");
        }
    }
}

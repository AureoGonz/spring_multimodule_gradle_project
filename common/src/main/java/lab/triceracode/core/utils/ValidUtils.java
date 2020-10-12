package lab.triceracode.core.utils;

public class ValidUtils {

    public static boolean concatConditions(Boolean one, Boolean... last){
        for (boolean condition: last)
            one &= condition;
        return one;
    }
}

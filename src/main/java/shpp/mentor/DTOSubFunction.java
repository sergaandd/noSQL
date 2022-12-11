package shpp.mentor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DTOSubFunction {
    static final int MAX_LETTERS = 15;
    static final int MIN_LETTERS = 19;
    static ObjectMapper om;
    private DTOSubFunction() {
    }
    public static String getName() {//Generate of random string for test
        int rndInt;
        String alph = "abcdefjhigklmnostupqrvwxyz";
        String randomString =(""+ alph.charAt((int) Math.round(Math.random() * 100) % alph.length())).toUpperCase();
        for (int i = 0; i < ((Math.random() * (MAX_LETTERS - MIN_LETTERS)) + MIN_LETTERS); i++) {
            rndInt = (int) Math.round(Math.random() * 100) % alph.length();
            randomString += alph.charAt(rndInt);
        }
        return randomString;
    }
    public static int getNumeric(int min,int max) {//Generate of random numeric in range
        return (int)((Math.random() * (max - min)) + min);
    }
    public static float getNumericFloat(int min,int max) {//Generate of random numeric in range
        return (float) ((Math.random() * (max - min)) + min);
    }

    public static String getJsonString(DTO p) {
        ObjectMapper oM = new ObjectMapper();
        try {
            return oM.writeValueAsString(p);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

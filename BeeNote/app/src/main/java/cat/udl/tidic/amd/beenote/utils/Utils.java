package cat.udl.tidic.amd.beenote.utils;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {


    private static String getEncodedHash(String password, String salt, int iterations) {
        PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA256Digest());
        gen.init(password.getBytes(StandardCharsets.UTF_8), salt.getBytes(), iterations);
        byte[] dk = ((KeyParameter) gen.generateDerivedParameters(256)).getKey();
        byte[] hashBase64 = Base64.getEncoder().encode(dk);
        return new String(hashBase64);
    }

    public static String encode(String password, String salt, int iterations) {
        String algorithm = "pbkdf2-sha256";
        String hash = getEncodedHash(password, salt, iterations);
        hash = hash.substring(0,hash.length()-1);
        hash =  hash.replaceAll("\\+",".");

        String salt_hash = Base64.getEncoder().encodeToString(salt.getBytes());
        salt_hash = salt_hash.substring(0,salt_hash.length()-1);
        salt_hash = salt_hash.replaceAll("\\+",".");

        return String.format(Locale.ENGLISH,
                "$%s$%d$%s$%s", algorithm, iterations, salt_hash, hash);
    }

    public static int get_day(String date) {
        Calendar calendar = Calendar.getInstance();
        Date start = stringToDate(date);
        calendar.setTime(start);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String get_month(String date) {
        Date start = stringToDate(date);
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("MMM",
                java.util.Locale.getDefault());
        return simpleDateformat.format(start);
    }

    private static Date stringToDate(String date) {
        Date d = null;
        try {
            d = new java.sql.Date(
                    new SimpleDateFormat("yyyy-MM-dd",
                            java.util.Locale.getDefault()).parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
}

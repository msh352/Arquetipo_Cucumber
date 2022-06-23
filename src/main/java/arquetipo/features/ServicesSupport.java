package arquetipo.features;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Assert;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ServicesSupport {

    private ServicesSupport(){
    }

    public static void verifyStatusCode (int expectedStatusCode, int currentStatusCode){
        Assert.assertEquals("Status code must be: " + expectedStatusCode, expectedStatusCode, currentStatusCode);
    }

    public static String parseOldFormatToNewFormatDate (String time, String oldFormat, String newFormat) throws ParseException {
        String newTimeStamp;
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat, Locale.ENGLISH);
        Date d = sdf.parse(time);
        sdf.applyPattern(newFormat);
        newTimeStamp= sdf.format(d);
        return newTimeStamp;
    }



    public static String jsonFileToJsonString(File file) throws Exception {

        InputStream is = new FileInputStream(file);
        return IOUtils.toString(is, StandardCharsets.UTF_8);

    }

    public static JSONObject StringJsonToJson(String json) throws ParseException {

        JSONObject jsonObj = new JSONObject(json);
        return jsonObj;
    }
}

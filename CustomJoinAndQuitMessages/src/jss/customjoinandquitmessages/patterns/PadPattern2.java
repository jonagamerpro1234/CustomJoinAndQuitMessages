package jss.customjoinandquitmessages.patterns;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import com.iridium.iridiumcolorapi.patterns.IPattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PadPattern2 implements IPattern {

    Pattern pattern = Pattern.compile("<#:([0-9A-Fa-f]{6})>");

    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String color = matcher.group(1);
            string = string.replace(matcher.group(), IridiumColorAPI.getColor(color) + "");
        }
        return string;
    }

}

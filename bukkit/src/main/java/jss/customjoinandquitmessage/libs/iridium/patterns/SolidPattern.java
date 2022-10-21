package jss.customjoinandquitmessage.libs.iridium.patterns;

import jss.customjoinandquitmessage.libs.iridium.IridiumColorAPI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolidPattern implements IPattern {

    final Pattern pattern = Pattern.compile("<#([\\dA-Fa-f]{6})>");

    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String color = matcher.group(1);
            string = string.replace(matcher.group(), IridiumColorAPI.getColor(color) + "");
        }
        return string;
    }

}

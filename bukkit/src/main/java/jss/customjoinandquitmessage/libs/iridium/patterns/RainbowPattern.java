package jss.customjoinandquitmessage.libs.iridium.patterns;

import jss.customjoinandquitmessage.libs.iridium.IridiumColorAPI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RainbowPattern implements IPattern {

    final Pattern pattern = Pattern.compile("<RAINBOW:(\\d{1,3})>(.*?)</RAINBOW>");

    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String saturation = matcher.group(1);
            String content = matcher.group(2);
            string = string.replace(matcher.group(), IridiumColorAPI.rainbow(content, Float.parseFloat(saturation)));
        }
        return string;
    }

}

package jss.customjoinandquitmessages.patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jss.customjoinandquitmessages.utils.IridiumColorAPI;

public class RainbowPattern implements IPattern {

	Pattern pattern = Pattern.compile("<RGB:([0-9]{1,3})>(.*?)</RGB>");

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

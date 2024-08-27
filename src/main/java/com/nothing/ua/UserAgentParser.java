package com.nothing.ua;

import com.nothing.model.ModelData;
import com.nothing.model.ModelDataManager;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgentParser {
    // Update regex to match more device information formats
    private static final Pattern ANDROID_PATTERN = Pattern.compile("Android ([^;]+)");
    private static final Pattern ADDITIONAL_PATTERN = Pattern.compile(" Safari/\\d+\\.\\d+(.*)");

    private static final List<Pattern> devicePatterns = List.of(
            Pattern.compile("\\(Linux; (?:U; )?Android [^;]+; [^;]+; ([^\\s;]+) Build/"),
            Pattern.compile("\\(Linux; (?:U; )?Android [^;]+; ([^;\\s]+) Build/"),
            Pattern.compile("\\(Linux; (?:U; )?Android [^;]+; ([^;]+);"),
            Pattern.compile("\\(Android|Linux; U; (.*?); zh-cn; (.*?) Build"),
            Pattern.compile("\\(Linux; Android [^;]+; ([^;]+); HMSCore"),
            Pattern.compile("\\(Linux; Android [^;]+; ([^;]+); wv"),
            Pattern.compile("\\(Linux; Android [^;]+; Build/([^;]+); wv")
    );

    private static String parseDevice(String userAgent) {
        for (Pattern pattern : devicePatterns) {
            Matcher matcher = pattern.matcher(userAgent);
            if (matcher.find()) {
                String data = matcher.group(1).trim();
                if (!data.isBlank()) {
                    return data;
                }
            }
        }
        return null;
    }

    public static ParsedData parse(String userAgent) throws FileNotFoundException {
        ParsedData data = new ParsedData();

        Matcher androidMatcher = ANDROID_PATTERN.matcher(userAgent);
        Matcher additionalMatcher = ADDITIONAL_PATTERN.matcher(userAgent);

        String device = parseDevice(userAgent);
        if (device == null) {
            data.setDeviceInfo("parseError");
//            System.err.println("device parse error: " + userAgent);
        } else {
            ModelData modelData = ModelDataManager.getModelData(device);
            if (modelData != null) {
                data.setDeviceInfo(modelData.getBrandTitle());
            } else {
                data.setDeviceInfo("unknown");
//                System.err.println("device not found in database: " + device);
            }
        }

        if (androidMatcher.find()) {
            data.setAndroidVersion(androidMatcher.group(1));
        } else {
            data.setAndroidVersion("unknown");
            System.err.println("androidVersion not found: " + userAgent);
        }

        if (additionalMatcher.find()) {
            data.setAdditionalInfo(additionalMatcher.group(1).trim());
        } else {
            data.setAdditionalInfo("unknown");
            System.err.println("additionalInfo not found: " + userAgent);
        }
        return data;
    }
}
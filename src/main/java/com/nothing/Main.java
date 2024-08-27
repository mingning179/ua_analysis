package com.nothing;

import com.nothing.model.ModelData;
import com.nothing.model.ModelDataManager;
import com.nothing.ua.ParsedData;
import com.nothing.ua.UserAgentParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, String> urlFileMap = new HashMap<>();
        urlFileMap.put("0827", "src/main/resources/2024-08-27.csv");
        for (Map.Entry<String, String> entry : urlFileMap.entrySet()) {
            doCount(entry.getKey(), entry.getValue());
        }
    }

    private static void doCount(String url, String fileName) {
        Map<String, Integer> deviceInfoCount = new HashMap<>();
        Map<String, Integer> androidVersionCount = new HashMap<>();
        Map<String, Integer> additionalInfoCount = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Skip the first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                ParsedData data = UserAgentParser.parse(line);
                deviceInfoCount.put(data.getDeviceInfo(), deviceInfoCount.getOrDefault(data.getDeviceInfo(), 0) + 1);
                androidVersionCount.put(data.getAndroidVersion(), androidVersionCount.getOrDefault(data.getAndroidVersion(), 0) + 1);
                additionalInfoCount.put(data.getAdditionalInfo(), additionalInfoCount.getOrDefault(data.getAdditionalInfo(), 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("fileName: " + fileName);
        // Print device info count
        printDeviceInfoCount(fileName, deviceInfoCount);
        // Print Android version count
        printAndroidVersionCount(fileName, androidVersionCount);
        // Print additional info count
        printAdditionalInfo(fileName, additionalInfoCount);
    }

    private static void printDeviceInfoCount(String fileName, Map<String, Integer> deviceInfoCount) {
        int sumType = deviceInfoCount.size();
        final int sumCount[]= {0};
        for (Map.Entry<String, Integer> entry : deviceInfoCount.entrySet()) {
            sumCount[0] += entry.getValue();
        }
        //排序输出
        System.out.println("device info count:");
        System.out.printf("总计类型: %d, 总计数量: %d\n", sumType, sumCount[0]);
        deviceInfoCount.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(entry -> {
            System.out.printf("%s: %d, 占比 %.2f%%\n", entry.getKey(), entry.getValue(), (double) entry.getValue() / sumCount[0] * 100);
        });
        System.out.println("----------------------------------------");
    }

    private static void printAndroidVersionCount(String fileName, Map<String, Integer> androidVersionCount) {
        int sumType = androidVersionCount.size();
        final int sumCount[]= {0};
        for (Map.Entry<String, Integer> entry : androidVersionCount.entrySet()) {
            sumCount[0] += entry.getValue();
        }
        //排序输出
        System.out.println("Android version count:");
        System.out.println("fileName: " + fileName);
        System.out.printf("总计类型: %d, 总计数量: %d\n", sumType, sumCount[0]);
        androidVersionCount.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(entry -> {
            System.out.printf("%s: %d, 占比 %.2f%%\n", entry.getKey(), entry.getValue(), (double) entry.getValue() / sumCount[0] * 100);
        });
        System.out.println("----------------------------------------");
    }

    private static void printAdditionalInfo(String fileName, Map<String, Integer> additionalInfoCount) {
        int sumType = additionalInfoCount.size();
        int sumCount = 0;

        int heyTypeCount = 0;
        int sumHeyTap = 0;

        int ksadTypeCount = 0;
        int ksadCount = 0;

        int yodaTypeCount = 0;
        int yodaCount = 0;

        int hapTypeCount = 0;
        int hapCount = 0;

        int normalTypeCount = 0;
        int normalCount = 0;

        int otherTypeCount = 0;
        int otherCount = 0;

        int miuiTypeCount = 0;
        int miuiCount = 0;

        int mobadsTypeCount = 0;
        int mobadsCount = 0;

        for (Map.Entry<String, Integer> entry : additionalInfoCount.entrySet()) {
            sumCount += entry.getValue();
            String key = entry.getKey();
            if (key != null) {
                if (key.contains("HeyTap")) {
                    sumHeyTap += entry.getValue();
                    heyTypeCount++;
                } else if (key.contains("KSADSDK")) {
                    ksadCount += entry.getValue();
                    ksadTypeCount++;
                } else if (key.contains("Yoda/")) {
                    yodaCount += entry.getValue();
                    yodaTypeCount++;
                } else if (key.contains("Mobads")) {
                    mobadsCount += entry.getValue();
                    mobadsTypeCount++;
                } else if (key.contains("hap/")) {
                    hapCount += entry.getValue();
                    hapTypeCount++;
                } else if (key.equals("\"")) {
                    normalCount += entry.getValue();
                    normalTypeCount++;
                } else if (key.contains("XiaoMi/MiuiBrowser/")) {
                    miuiCount += entry.getValue();
                    miuiTypeCount++;
                } else {
                    otherCount += entry.getValue();
                    otherTypeCount++;
                }
            }
        }

        System.out.println("fileName: " + fileName);
        System.out.printf("总计类型: %d , 总计数量: %d\n", sumType, sumCount);
        System.out.printf("oppo浏览器版本数量: %d ,oppo浏览器用户总体数量: %d ,占比 %.2f%%\n", heyTypeCount, sumHeyTap, (double) sumHeyTap / sumCount * 100);
        System.out.printf("小米浏览器版本数量: %d,小米浏览器用户总体数量: %d,占比 %.2f%%\n", miuiTypeCount, miuiCount, (double) miuiCount / sumCount * 100);
        System.out.printf("百度联盟版本数量: %d ,百度联盟用户总体数量: %d ,占比 %.2f%%\n", mobadsTypeCount, mobadsCount, (double) mobadsCount / sumCount * 100);
        System.out.printf("快手SDK版本数量: %d ,快手SDK用户总体数量: %d ,占比 %.2f%%\n", ksadTypeCount, ksadCount, (double) ksadCount / sumCount * 100);
        System.out.printf("快手APP版本数量: %d ,快手APP用户总体数量: %d ,占比 %.2f%%\n", yodaTypeCount, yodaCount, (double) yodaCount / sumCount * 100);
        System.out.printf("快应用版本数量: %d ,快应用用户总体数量: %d ,占比 %.2f%%\n", hapTypeCount, hapCount, (double) hapCount / sumCount * 100);
        System.out.printf("普通设备Agent版本数量: %d ,普通设备Agent总体数量: %d ,占比 %.2f%%\n", normalTypeCount, normalCount, (double) normalCount / sumCount * 100);
        System.out.printf("其他版本数量: %d ,其他用户总体数量: %d ,占比 %.2f%%\n", otherTypeCount, otherCount, (double) otherCount / sumCount * 100);
    }
}
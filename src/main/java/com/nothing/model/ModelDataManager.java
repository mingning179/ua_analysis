package com.nothing.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModelDataManager {
    public static final String modeCsvFile = "src/main/resources/models.csv";
    private static final Map<String, ModelData> modelDataMap = new HashMap<>();

    static {
        loadModelData();
    }

    private static void loadModelData() {
        try (BufferedReader br = new BufferedReader(new FileReader(modeCsvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 8) {
                    ModelData modelData = new ModelData(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
                    modelDataMap.put(values[0], modelData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ModelData getModelData(String model) {
        return modelDataMap.get(model);
    }

    public static void main(String[] args) {
        ModelData modelData = ModelDataManager.getModelData("A83");
        System.out.println(modelData);
    }
}
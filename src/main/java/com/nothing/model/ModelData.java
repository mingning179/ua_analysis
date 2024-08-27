package com.nothing.model;

public class ModelData {
    private String model;
    private String dtype;
    private String brand;
    private String brandTitle;
    private String code;
    private String codeAlias;
    private String modelName;
    private String verName;

    public ModelData(String model, String dtype, String brand, String brandTitle, String code, String codeAlias, String modelName, String verName) {
        this.model = model;
        this.dtype = dtype;
        this.brand = brand;
        this.brandTitle = brandTitle;
        this.code = code;
        this.codeAlias = codeAlias;
        this.modelName = modelName;
        this.verName = verName;
    }

    // Getters
    public String getModel() { return model; }
    public String getDtype() { return dtype; }
    public String getBrand() { return brand; }
    public String getBrandTitle() { return brandTitle; }
    public String getCode() { return code; }
    public String getCodeAlias() { return codeAlias; }
    public String getModelName() { return modelName; }
    public String getVerName() { return verName; }
}
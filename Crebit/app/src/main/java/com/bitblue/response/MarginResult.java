package com.bitblue.response;

public class MarginResult {
    private String count;
    private String type;
    private String name;
    private String margin;

    public MarginResult(String count, String type, String name, String margin) {
        this.count = count;
        this.type = type;
        this.name = name;
        this.margin = margin;
    }

    public String getCount() {
        return count;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getMargin() {
        return margin;
    }
}

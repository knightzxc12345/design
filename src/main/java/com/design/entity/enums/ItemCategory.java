package com.design.entity.enums;

public enum ItemCategory {

    TABLE("桌子"),

    CHAIR("椅子"),

    CABINET("櫃子"),

    ACCESSORY("配件"),

    SCREEN("屏風"),

    MOVING("搬運"),

    DISMANTLING("拆除"),

    OTHER("其他")

    ;

    private final String label;

    ItemCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}

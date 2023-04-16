package com.project.ecommerce.enums;

public enum ExceptionMessage {

    SOMETHING_WENT_WRONG("Something went wrong"),
    PRODUCT_NOT_FOUND("Product not found"),

    CATEGORY_NOT_FOUND("category not found");

    private String message;
    ExceptionMessage(String message) {
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}

package com.helmes.task.exception;

public enum Errors {
    INVALID_USER_ID("INVALID_USER_ID", "invalid user id, unable to find user by provided id"),
    NAME_IS_USED("NAME_IS_USED", "name is already used"),
    UNKNOWN_EXCEPTION("UNKNOWN_EXCEPTION", "unknown exception occurred on server side");

    private String keyword;
    private String description;

    Errors(String keyword, String description) {
        this.keyword = keyword;
        this.description = description;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

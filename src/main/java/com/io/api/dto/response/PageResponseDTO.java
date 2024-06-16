package com.io.api.dto.response;

public class PageResponseDTO extends ResponseDTO {
    private Object pagination;

    public PageResponseDTO() {}

    public PageResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Object getPagination() {
        return pagination;
    }

    public PageResponseDTO setPagination(Object pagination) {
        this.pagination = pagination;
        return this;
    }
}

package com.assignment.employee.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdatePhoneRequest {
    @NotBlank(message = "Phone is required")
    private String phone;

    public UpdatePhoneRequest() {
    }

    public UpdatePhoneRequest(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


package com.jwt.authentication.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private LocalDateTime date;
    private String details;

    public ErrorResponse(HttpStatus status, String message, LocalDateTime date, String details) {
        this.status = status;
        this.message = message;
        this.date = date;
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

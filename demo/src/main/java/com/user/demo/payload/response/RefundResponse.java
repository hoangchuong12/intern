package com.user.demo.payload.response;

public class RefundResponse {
    private String id;
    private Long amount;
    private String status;
    private String chargeId;

    public RefundResponse(String id, Long amount, String status, String chargeId) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.chargeId = chargeId;
    }

    // Getters và setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }
}

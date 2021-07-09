package com.fdm.server.project.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fdm.server.project.server.entity.Trade;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class TradeResponse implements Serializable {

    private String tradeId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private String time;

    private Long applicationUserId;

    private Trade trade;

    private String status;

    public TradeResponse() {
    }

    public TradeResponse(String tradeId, Trade trade, String time, Long applicationUserId, String status) {
        this.tradeId = tradeId;
        this.trade = trade;
        this.time = time;
        this.applicationUserId = applicationUserId;
        this.status = status;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(Long applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    @Override
    public String toString() {
        return "TradeResponse{" +
                "tradeId='" + tradeId + '\'' +
                ", time='" + time + '\'' +
                ", applicationUserId=" + applicationUserId +
                ", trade=" + trade +
                ", status='" + status + '\'' +
                '}';
    }
}

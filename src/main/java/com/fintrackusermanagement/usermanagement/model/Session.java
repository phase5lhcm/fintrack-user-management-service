package com.fintrackusermanagement.usermanagement.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Session {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLoginTimestamp() {
        return loginTimestamp;
    }

    public void setLoginTimestamp(LocalDateTime loginTimestamp) {
        this.loginTimestamp = loginTimestamp;
    }

    public LocalDateTime getLastActivityTimestamp() {
        return lastActivityTimestamp;
    }

    public void setLastActivityTimestamp(LocalDateTime lastActivityTimestamp) {
        this.lastActivityTimestamp = lastActivityTimestamp;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime loginTimestamp;
    private LocalDateTime lastActivityTimestamp;
    private Boolean isActive;

}

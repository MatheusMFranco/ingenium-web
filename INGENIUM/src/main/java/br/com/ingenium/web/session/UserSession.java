package br.com.ingenium.web.session;

import java.io.Serializable;

import br.com.ingenium.domain.model.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("userSession")
@SessionScoped
public class UserSession implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String username;
    private String fullName;

    public void signIn(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
    }

    public void signOut() {
        this.userId = null;
        this.username = null;
        this.fullName = null;
    }

    public boolean isAuthenticated() {
        return userId != null;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

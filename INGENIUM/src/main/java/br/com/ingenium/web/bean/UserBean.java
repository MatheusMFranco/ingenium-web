package br.com.ingenium.web.bean;

import java.io.Serializable;

import br.com.ingenium.application.exception.BusinessException;
import br.com.ingenium.application.service.UserService;
import br.com.ingenium.domain.model.User;
import br.com.ingenium.web.session.UserSession;
import br.com.ingenium.web.util.FacesMessages;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("userBean")
@ViewScoped
public class UserBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UserService userService;

    @Inject
    private UserSession userSession;

    private User user = new User();
    private String confirmEmail;
    private String confirmPassword;

    public void register() {
        try {
            validateRegistrationConfirmation();
            userService.register(user);
            FacesMessages.info("Cadastro realizado com sucesso!");
            this.user = new User();
            this.confirmEmail = null;
            this.confirmPassword = null;
        } catch (BusinessException e) {
            FacesMessages.error(e.getMessage());
        }
    }

    public void loadProfile() {
        if (!userSession.isAuthenticated()) {
            return;
        }
        this.user = userService.findById(userSession.getUserId()).orElseGet(User::new);
        this.user.setPassword(null);
        this.confirmPassword = null;
    }

    public void updateProfile() {
        if (!userSession.isAuthenticated()) {
            FacesMessages.error("Usuário não autenticado.");
            return;
        }

        try {
            validatePasswordConfirmationWhenRequired();
            User updatedUser = userService.updateProfile(userSession.getUserId(), user);
            userSession.setFullName(updatedUser.getFullName());
            this.user = updatedUser;
            this.user.setPassword(null);
            this.confirmPassword = null;
            FacesMessages.info("Conta alterada com sucesso!");
        } catch (BusinessException e) {
            FacesMessages.error(e.getMessage());
        }
    }

    public String deleteProfile() {
        if (!userSession.isAuthenticated()) {
            return "/login.xhtml?faces-redirect=true";
        }

        try {
            userService.deleteAccount(userSession.getUserId());
            userSession.signOut();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "/login.xhtml?faces-redirect=true";
        } catch (BusinessException e) {
            FacesMessages.error(e.getMessage());
            return null;
        }
    }

    private void validateRegistrationConfirmation() {
        if (confirmEmail == null || !confirmEmail.equalsIgnoreCase(user.getEmail())) {
            throw new BusinessException("A confirmação de e-mail deve ser igual ao e-mail informado.");
        }
        if (confirmPassword == null || !confirmPassword.equals(user.getPassword())) {
            throw new BusinessException("A confirmação de senha deve ser igual à senha informada.");
        }
    }

    private void validatePasswordConfirmationWhenRequired() {
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return;
        }
        if (confirmPassword == null || !confirmPassword.equals(user.getPassword())) {
            throw new BusinessException("A confirmação de senha deve ser igual à senha informada.");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

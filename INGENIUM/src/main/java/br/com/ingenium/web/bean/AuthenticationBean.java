package br.com.ingenium.web.bean;

import java.io.Serializable;
import java.util.Optional;
import java.util.logging.Logger;

import br.com.ingenium.application.service.AuthenticationService;
import br.com.ingenium.domain.model.User;
import br.com.ingenium.web.session.UserSession;
import br.com.ingenium.web.util.FacesMessages;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("authBean")
@SessionScoped
public class AuthenticationBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AuthenticationBean.class.getName());

    @Inject
    private transient AuthenticationService authenticationService;

    @Inject
    private UserSession userSession;

    private String username;
    private String password;

    public String login() {
        logger.info(() -> "Tentativa de login para o usuário: " + username);
        Optional<User> maybeUser = authenticationService.authenticate(username, password);
        if (maybeUser.isEmpty()) {
            logger.warning(() -> "Falha de autenticação para o usuário: " + username);
            FacesMessages.error("Não foi possível efetuar o login.");
            return null;
        }

        User authenticatedUser = maybeUser.get();
        userSession.signIn(authenticatedUser);
        logger.info(() -> "Usuário autenticado com sucesso: " + authenticatedUser.getUsername());
        
        return "/uc/objetivo/consultaObjetivo.xhtml?faces-redirect=true";
    }

    public String logout() {
        userSession.signOut();
        return "/login.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

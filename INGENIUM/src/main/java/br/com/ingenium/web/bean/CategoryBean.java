package br.com.ingenium.web.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.ingenium.application.exception.BusinessException;
import br.com.ingenium.application.service.CategoryService;
import br.com.ingenium.domain.model.Category;
import br.com.ingenium.web.session.UserSession;
import br.com.ingenium.web.util.FacesMessages;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("categoryBean")
@ViewScoped
public class CategoryBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(CategoryBean.class.getName());
    private static final String NOT_AUTHENTICATED = "Usuário não autenticado.";
    private static final String INTERNAL_ERROR = "Erro interno ao processar a requisição.";

    @Inject
    private transient CategoryService categoryService;

    @Inject
    private UserSession userSession;

    private Category category = new Category();
    private String action;
    private Long categoryId;

    public void loadCategory() {
        if (!userSession.isAuthenticated() || categoryId == null) {
            return;
        }
        this.category = categoryService.findById(userSession.getUserId(), categoryId)
            .orElseGet(Category::new);
    }

    public void create() {
        if (!userSession.isAuthenticated()) {
            FacesMessages.error(NOT_AUTHENTICATED);
            return;
        }

        try {
            categoryService.create(userSession.getUserId(), category);
            logger.info(() -> "Categoria criada com sucesso pelo usuário " + userSession.getUserId());
            this.category = new Category();
            FacesMessages.info("Categoria incluída com sucesso!");
        } catch (BusinessException e) {
            logger.warning(() -> "Falha ao criar categoria: " + e.getMessage());
            FacesMessages.error(e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao criar categoria", e);
            FacesMessages.error(INTERNAL_ERROR);
        }
    }

    public void update() {
        if (!userSession.isAuthenticated()) {
            FacesMessages.error(NOT_AUTHENTICATED);
            return;
        }

        try {
            categoryService.update(userSession.getUserId(), categoryId, category);
            logger.info(() -> "Categoria " + categoryId + " atualizada pelo usuário " + userSession.getUserId());
            FacesMessages.info("Categoria alterada com sucesso!");
        } catch (BusinessException e) {
            logger.warning(() -> "Falha ao atualizar categoria " + categoryId + ": " + e.getMessage());
            FacesMessages.error(e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e, () -> "Erro inesperado ao atualizar categoria " + categoryId);
            FacesMessages.error(INTERNAL_ERROR);
        }
    }

    public void delete() {
        if (!userSession.isAuthenticated()) {
            FacesMessages.error(NOT_AUTHENTICATED);
            return;
        }

        try {
            categoryService.delete(userSession.getUserId(), categoryId);
            logger.info(() -> "Categoria " + categoryId + " excluída pelo usuário " + userSession.getUserId());
            this.category = new Category();
            FacesMessages.info("Categoria excluída com sucesso!");
        } catch (BusinessException e) {
            logger.warning(() -> "Falha ao excluir categoria " + categoryId + ": " + e.getMessage());
            FacesMessages.error(e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e, () -> "Erro inesperado ao excluir categoria " + categoryId);
            FacesMessages.error(INTERNAL_ERROR);
        }
    }

    public String deleteAndRedirect() {
        delete();
        return "/uc/categoria/consultaCategoria.xhtml?faces-redirect=true";
    }

    public List<Category> list() {
        if (!userSession.isAuthenticated()) {
            return Collections.emptyList();
        }
        return categoryService.listByOwner(userSession.getUserId());
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}

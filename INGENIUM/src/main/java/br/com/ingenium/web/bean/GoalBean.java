package br.com.ingenium.web.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.model.LazyDataModel;

import br.com.ingenium.application.exception.BusinessException;
import br.com.ingenium.application.service.CategoryService;
import br.com.ingenium.application.service.GoalService;
import br.com.ingenium.domain.model.Category;
import br.com.ingenium.domain.model.DurationType;
import br.com.ingenium.domain.model.Goal;
import br.com.ingenium.web.session.UserSession;
import br.com.ingenium.web.util.FacesMessages;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("goalBean")
@ViewScoped
public class GoalBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(GoalBean.class.getName());
    private static final String NOT_AUTHENTICATED = "Usuário não autenticado.";
    private static final String INTERNAL_ERROR = "Erro interno ao processar a requisição.";

    @Inject
    private transient GoalService goalService;

    @Inject
    private transient CategoryService categoryService;

    @Inject
    private UserSession userSession;

    private Goal goal = new Goal();
    private Long selectedCategoryId;
    private String action;
    private Long goalId;
    
    private transient LazyDataModel<Goal> lazyModel;

    @PostConstruct
    public void init() {
        if (userSession.isAuthenticated()) {
            this.lazyModel = new LazyGoalDataModel(goalService, userSession.getUserId());
        }
    }

    public void loadGoal() {
        if (!userSession.isAuthenticated() || goalId == null) {
            return;
        }

        this.goal = goalService.findById(userSession.getUserId(), goalId)
            .orElseGet(Goal::new);

        if (goal.getCategory() != null) {
            this.selectedCategoryId = goal.getCategory().getId();
        }
    }

    public void create() {
        if (!userSession.isAuthenticated()) {
            FacesMessages.error(NOT_AUTHENTICATED);
            return;
        }

        try {
            goalService.create(userSession.getUserId(), goal, selectedCategoryId);
            logger.info(() -> "Objetivo criado com sucesso pelo usuário " + userSession.getUserId());
            this.goal = new Goal();
            this.selectedCategoryId = null;
            FacesMessages.info("Objetivo incluído com sucesso!");
        } catch (BusinessException e) {
            logger.warning(() -> "Falha ao criar objetivo: " + e.getMessage());
            FacesMessages.error(e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro inesperado ao criar objetivo", e);
            FacesMessages.error(INTERNAL_ERROR);
        }
    }

    public void update() {
        if (!userSession.isAuthenticated()) {
            FacesMessages.error(NOT_AUTHENTICATED);
            return;
        }

        try {
            goalService.update(userSession.getUserId(), goalId, goal, selectedCategoryId);
            logger.info(() -> "Objetivo " + goalId + " atualizado pelo usuário " + userSession.getUserId());
            FacesMessages.info("Objetivo alterado com sucesso!");
        } catch (BusinessException e) {
            logger.warning(() -> "Falha ao atualizar objetivo " + goalId + ": " + e.getMessage());
            FacesMessages.error(e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e, () -> "Erro inesperado ao atualizar objetivo " + goalId);
            FacesMessages.error(INTERNAL_ERROR);
        }
    }

    public void delete() {
        if (!userSession.isAuthenticated()) {
            FacesMessages.error(NOT_AUTHENTICATED);
            return;
        }

        try {
            goalService.delete(userSession.getUserId(), goalId);
            logger.info(() -> "Objetivo " + goalId + " excluído pelo usuário " + userSession.getUserId());
            this.goal = new Goal();
            this.selectedCategoryId = null;
            FacesMessages.info("Objetivo excluído com sucesso!");
        } catch (BusinessException e) {
            logger.warning(() -> "Falha ao excluir objetivo " + goalId + ": " + e.getMessage());
            FacesMessages.error(e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e, () -> "Erro inesperado ao excluir objetivo " + goalId);
            FacesMessages.error(INTERNAL_ERROR);
        }
    }

    public String deleteAndRedirect() {
        delete();
        return "/uc/objetivo/consultaObjetivo.xhtml?faces-redirect=true";
    }

    public LazyDataModel<Goal> getLazyModel() {
        return lazyModel;
    }

    public List<Category> getAvailableCategories() {
        if (!userSession.isAuthenticated()) {
            return Collections.emptyList();
        }
        return categoryService.listByOwner(userSession.getUserId());
    }

    public DurationType[] getDurationTypes() {
        return DurationType.values();
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Long getSelectedCategoryId() {
        return selectedCategoryId;
    }

    public void setSelectedCategoryId(Long selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }
}

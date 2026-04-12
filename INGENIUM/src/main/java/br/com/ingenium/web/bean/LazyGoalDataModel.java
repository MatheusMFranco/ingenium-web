package br.com.ingenium.web.bean;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.ingenium.application.service.GoalService;
import br.com.ingenium.domain.model.Goal;

public class LazyGoalDataModel extends LazyDataModel<Goal> {

    private static final long serialVersionUID = 1L;
    
    private final GoalService goalService;
    private final Long userId;

    public LazyGoalDataModel(GoalService goalService, Long userId) {
        this.goalService = goalService;
        this.userId = userId;
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) goalService.countByOwner(userId);
    }

    @Override
    public List<Goal> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        List<Goal> goals = goalService.listByOwnerPaginated(userId, first, pageSize);
        setRowCount((int) goalService.countByOwner(userId));
        return goals;
    }

    @Override
    public Goal getRowData(String rowKey) {
        return goalService.findById(userId, Long.valueOf(rowKey)).orElse(null);
    }

    @Override
    public String getRowKey(Goal goal) {
        return String.valueOf(goal.getId());
    }
}

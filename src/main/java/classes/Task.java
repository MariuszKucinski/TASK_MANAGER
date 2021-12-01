package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Class which represent task
 * @class Task
 *
 */
public class Task {
    private String inscription;
    private LocalDate dueToDate;
    private boolean isImportant;

    public Task(String inscription, LocalDate dueToDate, boolean isImportant) {
        this.inscription = inscription;
        this.dueToDate = dueToDate;
        this.isImportant = isImportant;
    }

    public String getInscription() {
        return inscription;
    }

    public void setInscription(String inscription) {
        this.inscription = inscription;
    }

    public LocalDate getDueToDate() {
        return dueToDate;
    }

    public void setDueToDate(LocalDate dueToDate) {
        this.dueToDate = dueToDate;
    }

    public boolean getIsImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    @Override
    public String toString() {
        return inscription + ',' + dueToDate +
                ","+ isImportant;
    }
}

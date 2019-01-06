import java.util.Date;

public class Car {

    private String carReg;
    private Boolean active;
    private Date due;
    private Date lastAction;

    public Car (String carReg, Boolean active, Date due, Date lastAction) {
        this.carReg = carReg;
        this.active = active;
        this.due = due;
        this.lastAction = lastAction;
    }

    public String getCarReg() {
        return carReg;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public Date getLastAction() {
        return lastAction;
    }

    public void setLastAction(Date lastAction) {
        this.lastAction = lastAction;
    }
}

package tn.esprit.livrable.DTO;

public class GanttItemDTO {
    private String id;
    private String text;
    private String startDate;
    private String endDate;
    private double progress;
    private String parent;
    private String type; // "project", "task", etc.


    public GanttItemDTO() {
    }

    public GanttItemDTO(String text, String startDate, String endDate, double progress, String parent, String type) {
        this.text = text;
        this.startDate = startDate;
        this.endDate = endDate;
        this.progress = progress;
        this.parent = parent;
        this.type = type;
    }

    public GanttItemDTO(String id, String text, String startDate, String endDate, double progress, String parent, String type) {
        this.id = id;
        this.text = text;
        this.startDate = startDate;
        this.endDate = endDate;
        this.progress = progress;
        this.parent = parent;
        this.type = type;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

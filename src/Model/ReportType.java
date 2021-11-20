package Model;

public class ReportType {
    private String type;
    private int month;
    private int total;

    public ReportType(String type, int month, int total) {
        this.type = type;
        this.month = month;
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public int getMonth() {
        return month;
    }

    public int getTotal() {
        return total;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setTotal(int total) {
        this.total = total;
    }


}



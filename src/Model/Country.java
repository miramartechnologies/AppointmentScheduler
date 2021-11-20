package Model;

public class Country {
    private int id;
    private String countryName;


    public Country(
        int id,
        String countryName

    )
    {
        this.id =  id;
                this.countryName = countryName;
                /*
        this.createDate =  createDate;
                this.createdBy =   createdBy;
        this.lastUpdate =  lastUpdate;
                this.lastUpdatedBy =   lastUpdatedBy;

                 */
    }
/*
    public String getCreateDate(){
        return createDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

 */

    public String getCountryName() {
        return countryName;
    }

    public int getId() {
        return id;
    }
    /*

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return (countryName);
    }
}

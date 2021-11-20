package Model;
import java.time.LocalDateTime;

public class Contact {
    private int contactId;
    private String contactName;
    private String contactEmail;

    public Contact(int contactId, String contactName){

        this.contactId=contactId;
        this.contactName=contactName;
    }

    public int getContactId() {
        return contactId;
    }



    public String getContactName() {
        return contactName;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }



    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Override
    public String toString(){
        return (contactName);
    }

}

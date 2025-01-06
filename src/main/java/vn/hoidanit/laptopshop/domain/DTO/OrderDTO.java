package vn.hoidanit.laptopshop.domain.DTO;

public class OrderDTO {
    private String recieverName;
    private String recieverMobile;
    private String recieverAddress;

    public String getRecieverName() {
        return recieverName;
    }

    public void setRecieverName(String recieverName) {
        this.recieverName = recieverName;
    }

    public String getRecieverMobile() {
        return recieverMobile;
    }

    public void setRecieverMobile(String recieverMobile) {
        this.recieverMobile = recieverMobile;
    }

    public String getRecieverAddress() {
        return recieverAddress;
    }

    public void setRecieverAddress(String recieverAddress) {
        this.recieverAddress = recieverAddress;
    }

}

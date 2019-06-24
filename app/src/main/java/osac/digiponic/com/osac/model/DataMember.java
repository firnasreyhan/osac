package osac.digiponic.com.osac.model;

public class DataMember {

    private String alpabhetName, memberName, memberEmail, memberPhone;

    public DataMember(String alpabhetName, String memberName, String memberEmail, String memberPhone) {
        this.alpabhetName = alpabhetName;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberPhone = memberPhone;
    }

    public String getAlpabhetName() {
        return alpabhetName;
    }

    public void setAlpabhetName(String alpabhetName) {
        this.alpabhetName = alpabhetName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }
}

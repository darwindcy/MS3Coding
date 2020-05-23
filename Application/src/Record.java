public class Record {
    private String fName;
    private String lName;
    private String email;
    private String gender;
    private String image;  // suppose
    private String ccCompany;
    private Double money;
    private Boolean bool1;
    private Boolean bool2;
    private String city;

    public Record(){

    }

    public Record(String fName, String lName, String email, String gender, String image,
                  String ccCompany, Double money, boolean bool1, boolean bool2, String city){
        setfName(fName);
        setlName(lName);
        setEmail(email);
        setGender(gender);
        setImage(image);
        setCcCompany(ccCompany);
        setMoney(money);
        setBool1(bool1);
        setBool2(bool2);
        setCity(city);
    }

    public void printRecord(){
        System.out.println("Record Detail");
        System.out.println(fName + " " + lName );
    }

    public boolean isValid(){
        if(fName.isEmpty() || lName.isEmpty() || email.isEmpty() || gender.isEmpty() || image.isEmpty() ||
            image.isEmpty() || ccCompany.isEmpty() || city.isEmpty() || bool1 == null || bool2 == null || money == null)
            return false;

        return true;
    }

    public void setBool1(boolean bool1) {
        this.bool1 = bool1;
    }

    public void setBool2(boolean bool2) {
        this.bool2 = bool2;
    }

    public void setCcCompany(String ccCompany) {
        this.ccCompany = ccCompany;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean getBool1(){
        return bool1;
    }

    public boolean getBool2(){
        return bool2;
    }

    public double getMoney() {
        return money;
    }

    public String getCcCompany() {
        return ccCompany;
    }

    public String getEmail() {
        return email;
    }

    public String getfName() {
        return fName;
    }

    public String getCity() {
        return city;
    }

    public String getGender() {
        return gender;
    }

    public String getImage() {
        return image;
    }

    public String getlName() {
        return lName;
    }
}

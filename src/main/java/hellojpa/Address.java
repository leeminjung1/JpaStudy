package hellojpa;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    /*
        생성자로만 값을 넣고 setter는 모두 지워 불변객체로 만들어줘야 인스턴스 공유가되더라도 변경할 수 없게됨
     */
//    public void setCity(String city) {
//        this.city = city;
//    }

    public String getStreet() {
        return street;
    }

//    public void setStreet(String street) {
//        this.street = street;
//    }

    public String getZipcode() {
        return zipcode;
    }

//    public void setZipcode(String zipcode) {
//        this.zipcode = zipcode;
//    }
}

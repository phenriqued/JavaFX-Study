package p_henriqued.javafxstudy.Entities.UserEntity;

public class UserEntity {

    private String name;
    private String email;
    private String numberPhone;

    public UserEntity() {
    }

    public UserEntity(String name, String email, String numberPhone) {
        this.name = name;
        this.email = email;
        this.numberPhone = numberPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    @Override
    public String toString() {
        return "Nome: " + name +
                " - email: " + email +
                "\nNúmero para Contato: " + numberPhone;
    }
}

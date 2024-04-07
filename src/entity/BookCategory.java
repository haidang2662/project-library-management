package entity;

public class BookCategory {

    private int idCategory;
    private String nameCategory;

    public BookCategory(int id) {
        this.idCategory = id;
    }

    public BookCategory(int id, int idCategory, String nameCategory) {
        this.idCategory = id;
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public String toString() {
        return "BookCategory{" +
                "idCategory=" + idCategory +
                ", nameCategory='" + nameCategory + '\'' +
                '}';
    }
}

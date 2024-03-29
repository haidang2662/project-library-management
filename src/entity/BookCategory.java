package entity;

public class BookCategory {

    private int idCategory;
    private String nameCategory;

    public BookCategory() {

    }

    public BookCategory( String name) {
        this.idCategory = idCategory;
        this.nameCategory = name;
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
                "id=" + idCategory +
                ", name='" + nameCategory + '\'' +
                '}';
    }
}

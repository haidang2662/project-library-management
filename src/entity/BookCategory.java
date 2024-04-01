package entity;

public class BookCategory {

    private static int AUTO_ID = 0;
    private int idCategory;
    private String nameCategory;

    public BookCategory() {
        this.idCategory = AUTO_ID++;
    }

    public BookCategory(String name) {
        this.idCategory = AUTO_ID++;
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

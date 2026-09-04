public class Equipment {
    public static int idCounter = 1;
    private final int id;
    private String name;
    private boolean available;

    // Constructor
    public Equipment(String name, boolean available) {
        this.id = idCounter;
        this.name = name;
        this.available = available;
        idCounter++;
    }

    @Override
    public String toString() {
        return "Equipment{id=" + id + ",name=" + name + ",available=" + available + "}";
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    //Setters


    public void setName(String name) {
        this.name = name;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

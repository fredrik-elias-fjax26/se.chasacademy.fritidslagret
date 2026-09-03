public class Equipment {
    int id;
    String name;
    boolean available;

    // Constructor
    public Equipment(int id, String name, boolean available) {
        this.id = id;
        this.name = name;
        this.available = available;
    }

    @Override
    public String toString() {
        return "Equipment:\nid: " + id + "\nname: " + name + "\navailable: " + available;
    }
}

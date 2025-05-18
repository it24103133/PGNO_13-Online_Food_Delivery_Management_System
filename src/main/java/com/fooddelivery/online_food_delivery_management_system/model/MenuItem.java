public class MenuItem {
    private int id;
    private String name;
    private double price;
    private String description;
    private String category;
    private boolean available;
    private int restaurantId;
    private String imageUrl;

    public MenuItem(int id, String name, double price, String description, String category, boolean available, int restaurantId, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.available = available;
        this.restaurantId = restaurantId;
        this.imageUrl = imageUrl != null ? imageUrl : "images/menuitems/food.png";
    }

    public MenuItem(int id, String name, double price, String description, String appetizer, boolean available, int restaurantId) {

    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }


    public double calculateFinalPrice() {
        switch (category.toLowerCase()) {
            case "main course":
                return price * 1.1;
            case "dessert":
                return price * 0.9;
            default:
                return price;
        }
    }
}
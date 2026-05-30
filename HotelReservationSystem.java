import java.util.ArrayList;
import java.util.Scanner;

// Class representing an individual Hotel Room
class Room {
    private int roomNumber;
    private String category;
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getCategory() { return category; }
    public double getPricePerNight() { return pricePerNight; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }
}

// Class representing a Booking Transaction
class Booking {
    private String guestName;
    private Room room;
    private int nights;
    private double totalAmount;

    public Booking(String guestName, Room room, int nights) {
        this.guestName = guestName;
        this.room = room;
        this.nights = nights;
        this.totalAmount = room.getPricePerNight() * nights;
        room.setAvailable(false); // Mark the room as occupied
    }

    public void displayBookingDetails() {
        System.out.println("\n--- Booking Confirmation ---");
        System.out.println("Guest Name: " + guestName);
        System.out.println("Room Number: " + room.getRoomNumber() + " (" + room.getCategory() + ")");
        System.out.println("Duration: " + nights + " nights");
        System.out.printf("Total Charge (Simulated Paid): $%.2f\n", totalAmount);
        System.out.println("Booking Status: Confirmed");
    }
}

// Main Application Class
public class HotelReservationSystem {
    private static ArrayList<Room> rooms = new ArrayList<>();
    private static ArrayList<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize hotel inventory with standard, deluxe, and suite rooms
        rooms.add(new Room(101, "Standard", 50.0));
        rooms.add(new Room(102, "Standard", 50.0));
        rooms.add(new Room(201, "Deluxe", 95.0));
        rooms.add(new Room(202, "Deluxe", 95.0));
        rooms.add(new Room(301, "Suite", 160.0));

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== CodeAlpha Hotel Reservation System ===");

        while (true) {
            System.out.println("\n1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline

            if (choice == 1) {
                System.out.println("\n--- Available Rooms ---");
                for (Room room : rooms) {
                    if (room.isAvailable()) {
                        System.out.println("Room " + room.getRoomNumber() + " [" + room.getCategory() + "] - $" + room.getPricePerNight() + "/night");
                    }
                }
            } else if (choice == 2) {
                System.out.print("Enter guest name: ");
                String name = scanner.nextLine();
                
                System.out.print("Enter category preferred (Standard, Deluxe, Suite): ");
                String categoryInput = scanner.nextLine();

                Room selectedRoom = null;
                for (Room room : rooms) {
                    if (room.isAvailable() && room.getCategory().equalsIgnoreCase(categoryInput)) {
                        selectedRoom = room;
                        break;
                    }
                }

                if (selectedRoom != null) {
                    System.out.print("Enter number of nights to stay: ");
                    int nights = scanner.nextInt();
                    
                    // Generate booking and simulate payment process
                    Booking newBooking = new Booking(name, selectedRoom, nights);
                    bookings.add(newBooking);
                    newBooking.displayBookingDetails();
                } else {
                    System.out.println("No available rooms found matching that category.");
                }
            } else if (choice == 3) {
                System.out.println("Exiting system. Have a great day!");
                break;
            } else {
                System.out.println("Invalid selection. Please try again.");
            }
        }
        scanner.close();
    }
}
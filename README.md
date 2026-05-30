# CodeAlpha - Hotel Reservation System

A clean, object-oriented Java console application developed for the CodeAlpha Internship program to simulate a real-world hotel booking experience.

## 🚀 Features
* **Room Search:** Dynamically scans inventory to list all available rooms, showing their numbers, types (Standard, Deluxe, Suite), and price per night.
* **Reservation Engine:** Enables guests to pick a room tier, inputs custom stay durations, and dynamically assigns a room.
* **Simulated Payment Gateway:** Instantly calculates the total billing and outputs a secure "Paid & Confirmed" summary receipt upon booking.
* **State Management:** Once a room is successfully reserved, its availability toggle updates to prevent duplicate bookings.

## 🛠️ Technologies Used
* **Language:** Java (JDK 8 or higher)
* **Concepts:** Object-Oriented Programming (Classes & Objects), Custom Data Models, List Frameworks (`ArrayList`), Console Input Management (`Scanner`).

## 📋 Execution Preview
```text
=== CodeAlpha Hotel Reservation System ===

1. Search Available Rooms
2. Make a Reservation
3. Exit
Choose an option: 2
Enter guest name: nabia
Enter category preferred (Standard, Deluxe, Suite): Deluxe
Enter number of nights to stay: 1

--- Booking Confirmation ---
Guest Name: nabia
Room Number: 201 (Deluxe)
Duration: 1 nights
Total Charge (Simulated Paid): $95.00
Booking Status: Confirmed

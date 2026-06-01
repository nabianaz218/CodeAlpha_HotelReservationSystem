import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class HotelReservationGUI extends JFrame {
    // Parallel arrays for runtime database storage
    private ArrayList<String> guestNames = new ArrayList<>();
    private ArrayList<String> roomTypes = new ArrayList<>();
    private ArrayList<Integer> roomNumbers = new ArrayList<>();
    private ArrayList<Integer> stayNights = new ArrayList<>();
    private ArrayList<Integer> totalBills = new ArrayList<>();
    
    private final String FILE_NAME = "hotel_bookings.txt";
    
    // Constant limits for room availability
    private final int MAX_ROOMS_PER_TYPE = 5;
    private final int START_STANDARD = 101; 
    private final int START_DELUXE = 201;   
    private final int START_SUITE = 301;    

    private JComboBox<String> roomComboBox;
    private JTextField nameField, nightsField;
    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private JTextArea invoiceDisplayArea;
    
    private JLabel standardAvailLabel, deluxeAvailLabel, suiteAvailLabel;
    private JLabel totalRevenueLabel;

    // BRAND NEW: Premium Luxury Resort "Château Slate & Champagne Gold" Palette
    private final Color COLOR_BG = new Color(34, 38, 41);          // Warm Charcoal/Château Gray Base
    private final Color COLOR_CARD = new Color(44, 49, 53);        // Elevated Matte Slate Core Panel
    private final Color COLOR_GOLD = new Color(212, 175, 55);      // Radiant Champagne Gold Accent
    private final Color COLOR_MINT = new Color(129, 199, 132);     // Soft Sage Green for Success Vacancy
    private final Color COLOR_CRIMSON = new Color(229, 115, 115);  // muted Coral Crimson for Warning Occupied
    private final Color COLOR_TEXT = new Color(245, 245, 240);     // Crisp Ivory/Off-White Typography
    private final Color COLOR_MUTED = new Color(176, 190, 197);    // Soft Platinum Slate Secondary text
    private final Color COLOR_INPUT_BG = new Color(55, 62, 67);    // Deep Recessed Input Well

    public HotelReservationGUI() {
        setTitle("CodeAlpha | Grand Ritz Concierge Management & Room Matrix Architecture");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Launch in full-screen workspace
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout(25, 25));
        mainPanel.setBackground(COLOR_BG);
        mainPanel.setBorder(new EmptyBorder(25, 35, 25, 35));
        setContentPane(mainPanel);

        // ==========================================
        // 1. TOP HEADER BRANDING BANNER
        // ==========================================
        JPanel topPanel = new JPanel(new BorderLayout(0, 15));
        topPanel.setBackground(COLOR_BG);
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(COLOR_BG);
        JLabel mainTitle = new JLabel("GRAND RITZ OPERATIONAL MATRIX");
        mainTitle.setFont(new Font("Georgia", Font.BOLD, 26)); // Classy Serif Font for Hospitality Feel
        mainTitle.setForeground(COLOR_TEXT);
        JLabel subTitle = new JLabel("Property Management Core Interface — Live Room Diagnostics & Administrative Accounting");
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subTitle.setForeground(COLOR_GOLD);
        titlePanel.add(mainTitle, BorderLayout.NORTH);
        titlePanel.add(subTitle, BorderLayout.SOUTH);
        topPanel.add(titlePanel, BorderLayout.NORTH);

        // Luxury Status Room Counter Capsules
        JPanel availabilityPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        availabilityPanel.setBackground(COLOR_BG);
        standardAvailLabel = createStatusBadge("STANDARD WING AVAILABILITY (101-105)", "5 / 5 Available", COLOR_MINT, availabilityPanel);
        deluxeAvailLabel = createStatusBadge("DELUXE SUITES AVAILABILITY (201-205)", "5 / 5 Available", COLOR_MINT, availabilityPanel);
        suiteAvailLabel = createStatusBadge("PRESIDENTIAL SUITES AVAILABILITY (301-305)", "5 / 5 Available", COLOR_MINT, availabilityPanel);
        topPanel.add(availabilityPanel, BorderLayout.SOUTH);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ==========================================
        // 2. LEFT PANEL: FRONT DESK CHECK-IN ENGINE
        // ==========================================
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(COLOR_CARD);
        leftPanel.setPreferredSize(new Dimension(360, 0));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(69, 77, 83), 1, true),
            new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel panelTitle = new JLabel("Front Desk Processing");
        panelTitle.setFont(new Font("Georgia", Font.BOLD, 18));
        panelTitle.setForeground(COLOR_TEXT);
        panelTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(panelTitle);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Guest Entry Box
        JLabel nameLabel = new JLabel("PRIMARY GUEST PROFILE NAME");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        nameLabel.setForeground(COLOR_MUTED);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(nameLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        nameField = new JTextField();
        styleInputField(nameField);
        leftPanel.add(nameField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Suite Tier Dropdown
        JLabel roomLabel = new JLabel("SELECT ACCOMMODATION CLASS");
        roomLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        roomLabel.setForeground(COLOR_MUTED);
        roomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(roomLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        String[] rooms = {"Standard Room ($100 / Night)", "Deluxe Room ($180 / Night)", "Luxury Suite ($300 / Night)"};
        roomComboBox = new JComboBox<>(rooms);
        roomComboBox.setBackground(COLOR_INPUT_BG);
        roomComboBox.setForeground(COLOR_TEXT);
        roomComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roomComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        roomComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(roomComboBox);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Night Duration Box
        JLabel nightsLabel = new JLabel("LENGTH OF STAY (NIGHTS)");
        nightsLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        nightsLabel.setForeground(COLOR_MUTED);
        nightsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(nightsLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        nightsField = new JTextField("1");
        styleInputField(nightsField);
        leftPanel.add(nightsField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Luxury Polished Buttons
        JButton bookBtn = new JButton("CONFIRM ROOM ALLOCATION");
        styleButton(bookBtn, COLOR_GOLD, COLOR_BG);
        leftPanel.add(bookBtn);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        JButton cancelBtn = new JButton("RELEASE OCCUPANCY (CHECKOUT)");
        styleButton(cancelBtn, COLOR_CRIMSON, COLOR_TEXT);
        leftPanel.add(cancelBtn);

        mainPanel.add(leftPanel, BorderLayout.WEST);

        // ==========================================
        // 3. CENTER PANEL: LIVE METRIC GRIDS & LEDGER
        // ==========================================
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        centerPanel.setBackground(COLOR_BG);

        // Live Room Registry Table
        String[] columnNames = {"Room Number", "Tier Class", "Registered Guest", "Stay Duration", "Gross Tariffs"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        bookingTable = new JTable(tableModel);
        bookingTable.setBackground(COLOR_CARD);
        bookingTable.setForeground(COLOR_TEXT);
        bookingTable.setRowHeight(35);
        bookingTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bookingTable.setGridColor(new Color(58, 65, 71));
        bookingTable.setSelectionBackground(new Color(69, 77, 83));
        bookingTable.setSelectionForeground(COLOR_GOLD);

        JTableHeader tableHeader = bookingTable.getTableHeader();
        tableHeader.setBackground(new Color(55, 62, 67));
        tableHeader.setForeground(COLOR_TEXT);
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableHeader.setPreferredSize(new Dimension(0, 38));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        bookingTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        bookingTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        bookingTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        bookingTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        JScrollPane tableScrollPane = new JScrollPane(bookingTable);
        tableScrollPane.getViewport().setBackground(COLOR_BG);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(69, 77, 83)), "Live Concierge Room Status Registry", 0, 0, null, COLOR_MUTED
        ));
        centerPanel.add(tableScrollPane);

        // Champagne Text Terminal Output for Invoices
        invoiceDisplayArea = new JTextArea();
        invoiceDisplayArea.setEditable(false);
        invoiceDisplayArea.setBackground(COLOR_CARD);
        invoiceDisplayArea.setForeground(COLOR_GOLD);
        invoiceDisplayArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        invoiceDisplayArea.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JScrollPane invoiceScrollPane = new JScrollPane(invoiceDisplayArea);
        invoiceScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(69, 77, 83)), "Corporate Statement Ledger Summary", 0, 0, null, COLOR_MUTED
        ));
        centerPanel.add(invoiceScrollPane);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // ==========================================
        // 4. BOTTOM PANEL: REVENUE METRICS OVERVIEW
        // ==========================================
        JPanel bottomDashboard = new JPanel(new GridLayout(1, 1));
        bottomDashboard.setBackground(COLOR_BG);
        bottomDashboard.setPreferredSize(new Dimension(0, 90));

        JPanel revenueCard = new JPanel(new BorderLayout());
        revenueCard.setBackground(COLOR_CARD);
        revenueCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(69, 77, 83), 1, true),
            new EmptyBorder(12, 25, 12, 25)
        ));
        JLabel revenueMeta = new JLabel("TOTAL PIPELINE INCOMING TURNOVER (CUMULATIVE POOL)");
        revenueMeta.setFont(new Font("Segoe UI", Font.BOLD, 11));
        revenueMeta.setForeground(COLOR_MUTED);
        totalRevenueLabel = new JLabel("$0.00 USD");
        totalRevenueLabel.setFont(new Font("Georgia", Font.BOLD, 26));
        totalRevenueLabel.setForeground(COLOR_GOLD);
        revenueCard.add(revenueMeta, BorderLayout.NORTH);
        revenueCard.add(totalRevenueLabel, BorderLayout.SOUTH);
        bottomDashboard.add(revenueCard);

        mainPanel.add(bottomDashboard, BorderLayout.SOUTH);

        // Sync and draw persistent cache files
        loadBookingsFromFile();

        // ==========================================
        // ACTIONS & EVENT HOOK CONTROLLERS
        // ==========================================
        bookBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String nightsStr = nightsField.getText().trim();
            int selectedIdx = roomComboBox.getSelectedIndex();
            
            if (name.isEmpty() || nightsStr.isEmpty()) {
                showSystemAlert("Processing Error: All guest profiles and duration data strings must be filled.");
                return;
            }

            int nights;
            try {
                nights = Integer.parseInt(nightsStr);
                if (nights <= 0) {
                    showSystemAlert("Numerical Domain Constraint: Overnight duration must scale at 1 night minimum.");
                    return;
                }
            } catch (NumberFormatException ex) {
                showSystemAlert("Data Alignment Exception: Nights text box requires a pure integer value.");
                return;
            }

            String roomType = "";
            int baseRate = 0;
            int nextRoomNum = 0;

            if (selectedIdx == 0) {
                roomType = "Standard Room"; baseRate = 100;
                if (getCountOfBookedType(roomType) >= MAX_ROOMS_PER_TYPE) { showSystemAlert("Inventory Warning: Standard Wing blocks are booked."); return; }
                nextRoomNum = findFirstAvailableRoom(START_STANDARD, roomType);
            } else if (selectedIdx == 1) {
                roomType = "Deluxe Room"; baseRate = 180;
                if (getCountOfBookedType(roomType) >= MAX_ROOMS_PER_TYPE) { showSystemAlert("Inventory Warning: Deluxe Room wings are booked."); return; }
                nextRoomNum = findFirstAvailableRoom(START_DELUXE, roomType);
            } else if (selectedIdx == 2) {
                roomType = "Luxury Suite"; baseRate = 300;
                if (getCountOfBookedType(roomType) >= MAX_ROOMS_PER_TYPE) { showSystemAlert("Inventory Warning: Presidential suites are fully allocated."); return; }
                nextRoomNum = findFirstAvailableRoom(START_SUITE, roomType);
            }

            int totalCost = baseRate * nights;

            guestNames.add(name);
            roomTypes.add(roomType);
            roomNumbers.add(nextRoomNum);
            stayNights.add(nights);
            totalBills.add(totalCost);

            saveBookingToFile(name, roomType, nextRoomNum, nights, totalCost);
            updateSystemDashboard();
            printInvoiceReceipt(name, roomType, nextRoomNum, nights, totalCost);

            nameField.setText("");
            nightsField.setText("1");
        });

        cancelBtn.addActionListener(e -> {
            int selectedRow = bookingTable.getSelectedRow();
            if (selectedRow == -1) {
                showSystemAlert("Instruction Alert: Please highlight an active row line from the Room Status Registry table to checkout.");
                return;
            }

            guestNames.remove(selectedRow);
            roomTypes.remove(selectedRow);
            roomNumbers.remove(selectedRow);
            stayNights.remove(selectedRow);
            totalBills.remove(selectedRow);

            rewriteEntireDatabaseFile();
            updateSystemDashboard();
            invoiceDisplayArea.setText("\n\n   >> [LEDGER SYNC COMPLETE] Key card unlinked. Folio balances settled. Room swept.");
        });
    }

    // Mathematical Inventory Core Calculations
    private int getCountOfBookedType(String type) {
        int count = 0;
        for (String t : roomTypes) {
            if (t.equals(type)) count++;
        }
        return count;
    }

    private int findFirstAvailableRoom(int startNum, String type) {
        for (int i = 0; i < MAX_ROOMS_PER_TYPE; i++) {
            int testRoom = startNum + i;
            if (!roomNumbers.contains(testRoom)) {
                return testRoom;
            }
        }
        return startNum;
    }

    private JLabel createStatusBadge(String titleText, String initValue, Color accent, JPanel target) {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(COLOR_CARD);
        container.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(69, 77, 83), 1),
            new EmptyBorder(10, 15, 10, 15)
        ));
        JLabel meta = new JLabel(titleText);
        meta.setFont(new Font("Segoe UI", Font.BOLD, 10));
        meta.setForeground(COLOR_MUTED);
        JLabel val = new JLabel(initValue);
        val.setFont(new Font("Segoe UI", Font.BOLD, 18));
        val.setForeground(accent);
        container.add(meta, BorderLayout.NORTH);
        container.add(val, BorderLayout.SOUTH);
        target.add(container);
        return val;
    }

    private void styleInputField(JTextField field) {
        field.setBackground(COLOR_INPUT_BG);
        field.setForeground(COLOR_TEXT);
        field.setCaretColor(COLOR_GOLD);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(90, 100, 108), 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleButton(JButton btn, Color bg, Color fg) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void updateSystemDashboard() {
        tableModel.setRowCount(0);
        int totalIncome = 0;

        int standardBooked = getCountOfBookedType("Standard Room");
        int deluxeBooked = getCountOfBookedType("Deluxe Room");
        int suiteBooked = getCountOfBookedType("Luxury Suite");

        updateBadgeState(standardAvailLabel, MAX_ROOMS_PER_TYPE - standardBooked);
        updateBadgeState(deluxeAvailLabel, MAX_ROOMS_PER_TYPE - deluxeBooked);
        updateBadgeState(suiteAvailLabel, MAX_ROOMS_PER_TYPE - suiteBooked);

        for (int i = 0; i < guestNames.size(); i++) {
            totalIncome += totalBills.get(i);
            tableModel.addRow(new Object[]{
                "Room " + roomNumbers.get(i), roomTypes.get(i), guestNames.get(i), stayNights.get(i) + " Night(s)", "$" + totalBills.get(i)
            });
        }
        totalRevenueLabel.setText(String.format("$%,.2f USD", (double) totalIncome));
    }

    private void updateBadgeState(JLabel label, int inventory) {
        label.setText(inventory + " / " + MAX_ROOMS_PER_TYPE + " Vacant");
        if (inventory == 0) {
            label.setForeground(COLOR_CRIMSON);
        } else {
            label.setForeground(COLOR_MINT);
        }
    }

    private void printInvoiceReceipt(String name, String type, int rNum, int nights, int total) {
        invoiceDisplayArea.setText(
            "\t=========================================================================\n" +
            "\t                  THE GRAND RITZ RESORT & CASINO FOLIO RECEIPT\n" +
            "\t=========================================================================\n\n" +
            "\t  SUITE IDENTIFICATION : Room " + rNum + " [" + type.toUpperCase() + "]\n" +
            "\t  REGISTERED PATRON    : " + name.toUpperCase() + "\n" +
            "\t  STAY DURATION TRACK  : " + nights + " Scheduled Invoice Night(s)\n" +
            "\t  ACCOUNT BALANCE LOGS : $" + total + ".00 USD Total (TRANSACTION GUARANTEED PAID)\n\n" +
            "\t=========================================================================\n" +
            "\t  Thank you for choosing the Grand Ritz hospitality network. Enjoy your stay."
        );
    }

    // File Pipeline Database Operations
    private void saveBookingToFile(String name, String type, int rNum, int nights, int total) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(name + "|" + type + "|" + rNum + "|" + nights + "|" + total);
        } catch (IOException e) {
            System.err.println("File Stream IO Anomaly: " + e.getMessage());
        }
    }

    private void loadBookingsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    guestNames.add(parts[0]);
                    roomTypes.add(parts[1]);
                    roomNumbers.add(Integer.parseInt(parts[2]));
                    stayNights.add(Integer.parseInt(parts[3]));
                    totalBills.add(Integer.parseInt(parts[4]));
                }
            }
            updateSystemDashboard();
        } catch (IOException | NumberFormatException e) {
            System.err.println("Database initialization parse error: " + e.getMessage());
        }
    }

    private void rewriteEntireDatabaseFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME, false))) {
            for (int i = 0; i < guestNames.size(); i++) {
                out.println(guestNames.get(i) + "|" + roomTypes.get(i) + "|" + roomNumbers.get(i) + "|" + stayNights.get(i) + "|" + totalBills.get(i));
            }
        } catch (IOException e) {
            System.err.println("Database rewriting synchronization breakdown: " + e.getMessage());
        }
    }

    private void showSystemAlert(String text) {
        UIManager.put("OptionPane.background", COLOR_CARD);
        UIManager.put("Panel.background", COLOR_CARD);
        UIManager.put("OptionPane.messageForeground", COLOR_TEXT);
        JOptionPane.showMessageDialog(this, text, "Concierge Intelligence Core", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new HotelReservationGUI().setVisible(true));
    }
}
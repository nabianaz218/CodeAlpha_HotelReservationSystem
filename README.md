# CodeAlpha - Grand Ritz Concierge Management Platform

A premium, enterprise-grade Java Graphical User Interface (GUI) application developed as part of the CodeAlpha Internship program. This software serves as an interactive property management system (PMS) and luxury concierge dashboard, enabling hospitality administrators to track room categories, manage real-time room inventories, process custom stay durations, and record booking statements with persistent data storage.

## 🚀 Features
* **Château Slate & Champagne Gold UI:** A tailored hospitality aesthetic featuring a warm matte slate base, polished gold highlights, soft sage indicators for vacancies, and crisp ivory typography.
* **Live Capsule Diagnostics:** High-visibility inventory blocks pinned to the header displaying real-time vacancies and automated room ranges (Standard Wing: 101-105, Deluxe Wards: 201-205, Presidential Suites: 301-305).
* **Flexible Stay Architecture:** Captures primary guest profiles and dynamic stay durations (nights) to evaluate continuous accommodation constraints and automate balance math.
* **Simulated Billing Ledger:** Instantly processes accounting metrics and displays clear ASCII transaction folios marked as *Guaranteed Paid*.
* **Interactive Checkout Core:** Administrators can clear a reservation and release room inventory simply by highlighting any line item on the registry table and confirming checkout.
* **Persistent Local Database:** Backed by an automated string-delimited file stream pipeline (`hotel_bookings.txt`) that safely backs up all active allocations and reloads them instantly upon startup.

## 🛠️ Technologies Used
* **Language:** Java (JDK 8 or higher)
* **GUI Architecture:** Java Swing Framework, Custom Component Painters (`UIManager`), Custom Layout Bounds (`BorderLayout`, `BoxLayout`, `GridLayout`), and Table Row Renderers (`JTable`).
* **Core Mechanics:** Regular Expression File I/O (`BufferedReader`, `PrintWriter`), Dynamic Collection Arrays (`ArrayList`), and Swing Look & Feel Standardization.

## 🖥️ Operational System Overview
The interface forces an automated **Maximized Full-Screen Mode** (`JFrame.MAXIMIZED_BOTH`) upon launch to provide an immersive desktop workspace.

### Core Architecture Panels:
1. **Front Desk Processing (Left Column):** Dedicated portal capturing guest registration profiles, tier classes, and scheduled overnight matrix boundaries.
2. **Concierge Room Status Registry (Center Upper Grid):** Live data layout presenting allocated room identifiers, class tags, active guest names, stay tracking, and gross tariffs.
3. **Corporate Statement Ledger (Center Lower Console):** Dedicated terminal window printing fully detailed accounting outputs and customer check-in slips.
4. **Pipeline Incoming Turnover (Footer Counter):** A grand high-visibility tracker compounding total active administrative revenue values dynamically.

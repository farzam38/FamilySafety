

# **SurakshaSetu - Family Safety App**

SurakshaSetu is a comprehensive family safety application designed to provide peace of mind through real-time location tracking, emergency alert systems, and secure group management features. This report presents the app's functionalities, technical design, implementation, and future plans, ensuring a clear understanding for developers and end-users alike.

---

## **Table of Contents**

1. **Introduction**  
2. **Features**  
3. **System Requirements**  
4. **Architecture Overview**  
   - High-Level Design  
   - Low-Level Design  
5. **Setup and Installation**  
6. **Code Walkthrough**  
   - MainActivity  
   - Database Implementation  
   - Maps Integration  
   - Invites System  
   - SOS Alerts  
7. **Detailed Flowcharts**  
   - High-Level Flowchart  
   - Low-Level Flowchart (SOS & Location Sharing)  
8. **Screenshots and User Interface**  
9. **Workflows**  
   - SOS Alerts  
   - Adding and Managing Contacts  
   - Viewing Locations on Map  
   - Invites System  
10. **Privacy and Security**  
    - Data Encryption  
    - User Permissions  
    - Data Retention Policy  
11. **Testing**  
    - Test Scenarios  
    - Sample Test Cases  
12. **Challenges and Solutions**  
13. **Performance Optimization**  
14. **Future Enhancements**  
15. **References**  
16. **Appendices**  

---

## **1. Introduction**

SurakshaSetu empowers families with tools to monitor, connect, and respond effectively during emergencies. The app integrates location tracking, secure communication, and user-friendly interfaces, making it an essential tool in today’s fast-paced world.  

**Primary Goals:**  
- Enhance personal and family safety.  
- Provide real-time data sharing with minimal latency.  
- Ensure privacy through encrypted data handling.  

**Key Highlights:**  
- Ola Maps SDK integration for robust location tracking.  
- Firebase for real-time database management and notifications.  
- Modular design for scalability and ease of maintenance.  

---

## **2. Features**

### **2.1 Real-Time Location Tracking**  
- **Description:** Allows users to track family members' locations in real time.  
- **Technical Details:**  
  - Utilizes Ola Maps SDK for GPS data.  
  - Smooth camera transitions using `moveCameraToLatLong` API.  

### **2.2 SOS Alerts**  
- **Description:** Sends emergency alerts with live location details.  
- **Workflow:**  
  - Fetches user location using `LocationManager`.  
  - Sends push notifications to pre-defined contacts.  

### **2.3 Contact Management**  
- **Description:** Provides options to add, edit, and delete trusted contacts.  
- **Technical Details:**  
  - Implements Room Database for local data storage.  
  - Supports two-way synchronization with Firebase.  

### **2.4 Invites System**  
- **Description:** Enables users to invite others to share their location securely.  
- **Implementation:**  
  - Uses Firestore for invite management.  
  - Displays invited members' locations on the map.  

### **2.5 Location History**  
- **Description:** Logs user locations over time for review and analysis.  
- **Technical Details:**  
  - Stored in Firestore with timestamps.  
  - Visualized using a polyline on the map.  

### **2.6 Privacy Settings**  
- Allows users to control visibility of their location data.  

---

## **3. System Requirements**

### **Minimum Requirements**  
- OS: Android 8.0 (Oreo) or above.  
- RAM: 2 GB or more.  
- Storage: Minimum 100 MB of free space.  

### **Dependencies**  
- Ola Maps SDK (API v3.0).  
- Firebase Authentication and Firestore.  
- Android Lifecycle components.  

---

## **4. Architecture Overview**

### **4.1 High-Level Design**  
The app follows an MVVM (Model-View-ViewModel) pattern, ensuring separation of concerns, improved testability, and ease of updates.  

```plaintext
[UI Layer]
   |
   --> Fragments & Activities  
   |
   --> ViewModel (Business Logic)  
   |
   --> Model (Repositories & Database Access)  
```

### **4.2 Low-Level Design**  
- **Fragments:**  
  - `HomeFragment`: Displays dashboard.  
  - `MapsFragment`: Handles location tracking.  
  - `GuardFragment`: Manages invites and contact lists.  

- **Databases:**  
  - Firestore for cloud data management.  
  - Room Database for offline support.  

---

## **5. Setup and Installation**

1. **Clone the Repository:**  
   ```bash
   git clone https://github.com/yourusername/suraksha-setu.git
   ```
2. **Add API Keys:**  
   - Obtain Ola Maps API Key.  
   - Add it to the project in `res/values/api_keys.xml`.  

3. **Configure Firebase:**  
   - Download `google-services.json` from Firebase Console.  
   - Place it in the app-level directory.  

4. **Build and Run:**  
   - Sync Gradle and run the app on an emulator or device.  

---

## **6. Code Walkthrough**

### **6.1 MainActivity**  

Handles permission checks and fragment transitions.  

```kotlin
private fun checkAndRequestPermissions() {
    val permissionsToRequest = permissions.filter {
        ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
    }
    if (permissionsToRequest.isNotEmpty()) {
        ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), requestCode)
    }
}
```

### **6.2 Maps Integration**  

Uses `OlaMap` to add markers dynamically for family members.  

```kotlin
val markerOptions = OlaMarkerOptions.Builder()
    .setMarkerId(userEmail)
    .setPosition(OlaLatLng(latitude, longitude))
    .setSnippet("User: $userName")
    .setIconRotation(0f)
    .setIsAnimationEnable(true)
    .build()

olaMap.addMarker(markerOptions)
```

### **6.3 SOS Alert System**  

Combines location tracking with notifications.  

```kotlin
private fun sendSOSAlert() {
    val location = getCurrentLocation()
    firestore.collection("sosAlerts")
        .add(mapOf("location" to location, "timestamp" to System.currentTimeMillis()))
}
```

---

## **7. Detailed Flowcharts**

### **7.1 High-Level Flowchart**

```plaintext
[User Opens App]  
    |  
    +---> [Permission Check]  
             |  
             +---> [Load Dashboard]  
                      |  
                      +---> [Access Features: Maps, SOS, Invites]  
```

### **7.2 Low-Level Flowchart: Invites**

```plaintext
[User Sends Invite]  
    |  
    +---> [Save Invite in Firestore]  
             |  
             +---> [Notify Invited User]  
                      |  
                      +---> [Accept/Deny Action]  
```

---

## **8. Screenshots and User Interface**

**Screenshots:**  
- Home Screen  
- SOS Alert Screen  
- Map View with Markers  

---

## **9. Workflows**

### **9.1 SOS Alerts**  
Triggers notifications with real-time location.

---

## **10. Privacy and Security**

### **Data Encryption:**  
All sensitive data is encrypted using AES-256 during transmission.  

---

## **11. Testing**

### **Sample Test Case:**  

| Test Case          | Expected Outcome                     | Result  |  
|---------------------|--------------------------------------|---------|  
| Add Contact         | Contact saved in database           | Pass    |  

---

## **12. Challenges and Solutions**

### **Challenge:** Location latency.  
**Solution:** Optimized location fetch intervals.

---

## **References**

1. [Ola Maps SDK Documentation](https://maps.olacabs.com)  
2. [Firebase Firestore Guide](https://firebase.google.com/docs/firestore)  
3. Android Developer Documentation  


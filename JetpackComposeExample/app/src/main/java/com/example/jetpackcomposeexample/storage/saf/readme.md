
# SAF

```
==========================
STORAGE ACCESS FRAMEWORK (SAF)
==========================

1) WHAT SAF IS
--------------
SAF is Android’s official way (since Android 5.0+ and enforced in Android 11+) 
to allow apps to access files OUTSIDE their own app sandbox.

Without SAF, an app CANNOT:
- Browse "Downloads"
- Browse "Documents"
- Browse SD Card
- Browse internal storage folders
- Access other apps' files
- View user storage except app-specific files

SAF provides a *secure, user-driven* picker UI that grants access only to
folders/files selected by the user.


2) WHY WE USE SAF IN 2025
-------------------------
Android 10–14 introduced and enforced SCOPEG STORAGE:
- Apps cannot freely access the entire internal storage.
- Apps cannot list *any* folders outside their sandbox.
- Permissions like READ_EXTERNAL_STORAGE are deprecated/ignored.

SAF is the ONLY correct way to:
- Pick a folder
- Read files inside it
- Persist permissions for long-term access
- Safely access user-owned files

This is REQUIRED for browsing meaningful folders like:
- Downloads
- DCIM (camera photos)
- WhatsApp folders
- SD card
- Documents


3) WHY WE USE THESE SPECIFIC COMPONENTS
---------------------------------------

A) OpenDocumentTree()
   → Allows user to pick a DIRECTORY, not just a file.
   → Essential for listing all files inside it.

B) DocumentFile API
   → SAF returns URIs, not direct File paths.
   → DocumentFile allows:
      • listing files
      • getting file type
      • reading metadata
      • deleting (with permissions)

   Reason: normal java.io.File will NOT work for SAF URIs.

C) takePersistableUriPermission()
   → Allows long-term access even after app restarts.
   → Without this, access disappears after activity is destroyed.

D) getType() and MimeTypeMap
   → Used to detect if a file is a text file.
   → Reading binary files (PDF/PNG/MP4) using InputStream as text 
     will crash or return garbage.

E) SAFUtils abstraction
   → Makes the code clean and reusable.
   → Keeps UI focused only on UI.


4) COMMON PITFALLS FIXED BY SAF
-------------------------------

❌ File(...) cannot access Downloads/DCIM  
❌ /storage/emulated/0/... paths not accessible in Android 11+  
❌ READ_EXTERNAL_STORAGE permission no longer works  
❌ Cannot list directories outside app  
❌ Cannot access WhatsApp folders directly  
❌ Cannot access SD card directly  

SAF fixes everything by letting the user explicitly choose a directory 
and granting your app access to it.


5) WHY NOT USE File API?
------------------------
File(path).listFiles() now works ONLY for:
- Internal app storage
- External app-scoped storage (`Android/data/<package>/files`)

It WILL NOT WORK for:
- Downloads
- DCIM
- Documents
- Shared storage

That's why SAF is mandatory.


6) SUMMARY
----------
SAF is REQUIRED for:
- Browsing device storage
- Accessing user-selected folders
- Reading text files safely
- Listing files from anywhere on the device

Our SAF module follows ALL modern Android rules.

```
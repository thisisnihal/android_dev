# Readme

```
======================
MEDIASTORE (2025 GUIDE)
======================

1) WHAT MEDIASTORE IS
---------------------
MediaStore is a system-managed database that represents:
- Photos
- Videos
- Audio
- Documents (partially)

It is the ONLY safe way to save media files (like images) into user-visible 
shared storage, such as the Gallery.

MediaStore works with:
- insert()
- query()
- update()
- openOutputStream()

It saves data inside public folders like:
- Pictures/
- DCIM/
- Music/
- Movies/


2) WHY WE USE MediaStore INSTEAD OF FILE API
--------------------------------------------

In Android 10+, saving images using File("/sdcard/DCIM/...") is ILLEGAL.

Reasons:
❌ You don't own that folder  
❌ Scoped storage blocks access  
❌ You cannot browse or write to public media without MediaStore  
❌ WRITE_EXTERNAL_STORAGE is deprecated/ignored

MediaStore solves all of this.


3) WHY WE USE THESE SPECIFIC METHODS
------------------------------------

A) ContentValues
   - Required to tell the system:
       • file name
       • mime type
       • relative folder path
   - MediaStore uses these values to create a correct virtual media entry.

B) insert(EXTERNAL_CONTENT_URI)
   - Asks the system to "create a media item".
   - Returns a content URI (content://...) which we can write into.

C) openOutputStream(uri)
   - Safely writes image bytes into the MediaStore-backed file.
   - Ensures thumbnails, metadata, and permissions are handled correctly.

D) Bitmap.compress()
   - Writes the final JPEG into the MediaStore URI.

E) allowHardware(false) in Coil ImageRequest
   - Hardware bitmaps CANNOT be converted to Bitmap.
   - MediaStore requires a software bitmap to compress.

F) ImageLoader + execute()
   - Ensures we obtain a `SuccessResult` with a drawable we can extract.


4) PITFALLS AVOIDED
--------------------

❌ Using FileOutputStream to write into DCIM/  
   → Fails on Android 10+ (permission denied).

❌ Using WRITE_EXTERNAL_STORAGE  
   → Completely deprecated and useless on Android 13+.

❌ Trying to save a hardware-accelerated Bitmap  
   → "Cannot access hardware bitmap" crash.

❌ Writing to Pictures/ via java.io.File  
   → Blocked by scoped storage.

❌ Saving from Coil without allowHardware(false)  
   → You get a hardware bitmap that can't be compressed.


5) WHY WE USE A HELPER UTIL (MediaStoreUtils)
---------------------------------------------
- Keeps the Activity clean
- Makes folder/path handling reusable
- Proper MediaStore pattern (values → insert → stream → compress)


6) WHY Gallery ALWAYS SHOWS THE IMAGE
-------------------------------------
Because:
- MediaStore registers a new media item
- It triggers media scanners immediately
- System Gallery apps sync automatically


7) RELATIVE_PATH Explanation
----------------------------
put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/StorageCourse")

Means:
Store the image in:
Internal shared storage → Pictures → StorageCourse

No permissions needed.


8) SUMMARY
----------
MediaStore is the only correct way to:

- Download images
- Save images into Gallery
- Let the user "own" the downloaded media
- Avoid scoped storage restrictions

```
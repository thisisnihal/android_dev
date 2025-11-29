
## Internal Storage

```
======================
INTERNAL STORAGE (2025)
======================

1) WHAT INTERNAL STORAGE IS
---------------------------
Internal storage is a private directory belonging exclusively to your app.

Path:
    /data/data/<your.package>/files/

Files here:
- cannot be accessed by other apps
- are deleted when the app is uninstalled
- require no permissions


2) WHY WE USE INTERNAL STORAGE
------------------------------
Because it is:
- secure (private)
- simple
- perfect for text files, configs, small documents
- not affected by scoped storage restrictions

Android guarantees:
- no external app can read it
- no Storage Access Framework is needed


3) KEY METHODS USED & WHY

A) openFileOutput(name, MODE_PRIVATE)
   → Creates OR overwrites a file.
   → MODE_PRIVATE ensures file cannot be shared.

B) openFileInput(name)
   → Opens file for reading.

C) File(filesDir, name).delete()
   → Deletes a specific internal file.

D) filesDir.listFiles()
   → Lists all internal files.


4) PITFALLS HANDLED

❌ Trying to save large data → use Room instead  
❌ Writing binary data using openFileOutput incorrectly  
❌ Assuming internal files are visible to user → they are NOT  
❌ Trying to share internal files to other apps → need FileProvider  


5) COMMON MISTAKES

❌ Opening a file without try/catch  
   → openFileInput throws FileNotFoundException  
   → we handle this gracefully in our readInternal()

❌ Using hardcoded paths (/data/data/…)  
   → Apps cannot access these paths manually.

❌ Forgetting that internal storage persists until app uninstall  
   → Must manually delete temporary files.


6) SUMMARY
----------
Internal storage is BEST for:
- Config files
- Caches
- Text notes
- Secrets that don't require encryption
- Tiny user files

Your module uses the safest Java/Kotlin APIs for this.
```


## External Storage
```
==============================
EXTERNAL STORAGE (APP-SCOPED)
==============================

1) WHAT APP-SCOPED EXTERNAL STORAGE IS
--------------------------------------
Since Android 10+, apps no longer have access to the entire external storage.

BUT each app gets its own sandboxed external folder:

    /storage/emulated/0/Android/data/<your.package>/files/

This is:
- external (SD card / shared storage partition)
- but private to your app
- NO permission needed


2) WHY APP-SCOPED STORAGE IS IMPORTANT
--------------------------------------
It is perfect for:
- Backup files
- Exported text/JSON
- Large logs
- Cache that should survive app uninstall? (No — it is deleted on uninstall)
- Files the user may eventually share (via SAF/FileProvider)


3) METHODS WE USE AND WHY

A) File(getExternalFilesDir(null), name)
   → Provides path inside app-scoped area.
   → Safe and sandboxed.

B) writeText(content)
   → Simple file write.

C) readText()
   → Pure Kotlin API for file reading.
   → Works only inside sandbox — perfect for our use.

D) delete()
   → Removes a file in the app’s sandbox.

E) getExternalFilesDir().listFiles()
   → Lists ALL files your app created.


4) PITFALLS AVOIDED

❌ Trying to write to shared folders (Downloads/DCIM/Documents)  
   → Forbidden by scoped storage without SAF or MediaStore.

❌ Using WRITE_EXTERNAL_STORAGE  
   → Deprecated and ignored on Android 13+.

❌ Attempting to access /sdcard directly  
   → Will fail unless user picks folder using SAF.

❌ Thinking external = readable by user  
   → App-scoped external is NOT visible in normal file explorers.


5) COMMON MISUNDERSTANDINGS

- External != public
  App-scoped external is **private to the app**.

- Cannot use java.io.File for public folders
  Only for app-scoped paths.

- Use SAF or MediaStore for public file access.


6) SUMMARY
----------
App-scoped external storage is the middle-ground:
- Not as secure as internal storage
- But sandboxed and safe
- Perfect for larger files

For public, user-visible folders → use SAF or MediaStore.
```
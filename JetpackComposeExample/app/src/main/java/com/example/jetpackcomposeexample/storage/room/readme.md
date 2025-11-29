# Room

```
====================
ROOM DATABASE (2025)
====================

1) WHAT ROOM IS
---------------
Room is the official Android ORM over SQLite.
It provides:
- Type safety
- SQL compile-time checking
- Coroutines/Flow integration
- Easy CRUD with DAOs
- Auto migration support

Room replaces:
❌ raw SQLiteOpenHelper
❌ handwritten SQL management


2) WHY ROOM IS USED (2025)
--------------------------
- Direct SQLite usage is complex and error prone.
- Room ensures SQL queries are correct at compile time.
- Works cleanly with Kotlin, coroutines, and Jetpack.
- Integrates perfectly with MVVM + Compose (Flow).


3) NECESSARY CLASSES & WHY THEY EXIST
-------------------------------------

A) @Entity (Note.kt)
    - Marks a data class as a database table.
    - Room uses reflection + annotation processing to create schema.

B) @Dao (NoteDao.kt)
    - Defines CRUD operations.
    - Each @Insert, @Update, @Delete is generated at compile time.

C) RoomDatabase (NoteDatabase.kt)
    - Abstract class that creates the SQLite DB under the hood.
    - Provides a global entry point via `get()` singleton.

D) Flow from Dao
    - getAll(): Flow<List<Note>>
    - Flow auto-updates UI when DB changes.
    - No need for LiveData.

E) lifecycleScope.launch
    - Required because Room’s insert/update/delete are suspend functions.

F) Lazy database initialization
    - Prevents unnecessary DB creation until needed.


4) WHY THESE METHODS ARE NEEDED
-------------------------------

- insert(note)
  → Creates a new row.

- update(note)
  → Updates existing row by primary key.

- delete(note)
  → Removes the row.

- getAll()
  → Returns “live” stream of notes using Flow.


5) COMMON PITFALLS FIXED
-------------------------

❌ Performing database access on main thread  
   → Room forces async/suspend functions.

❌ Forgetting primary keys  
   → @PrimaryKey(autoGenerate = true) fixes this.

❌ Using MutableList instead of Flow  
   → Would not update automatically.

❌ Creating multiple database instances  
   → Singleton pattern inside NoteDatabase prevents this.

❌ Using LiveData in Compose  
   → Flow is simpler and more modern.


6) SUMMARY
----------
Room is the safest, cleanest way to handle structured relational data.
It provides:
- Compile-time safety
- Auto-updating UI
- Easy CRUD
- Strong Kotlin integration

```
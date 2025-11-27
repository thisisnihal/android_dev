

##  Quick Visual Mental Model

```
 ┌───────────┐      navigate()        ┌───────────┐
 │  Home     │ ─────────────────────▶ │ Details   │
 └───────────┘                        └───────────┘
        ▲                                    │
   popBackStack()                            │
        └───────────────◀────────────────────┘
``` 

Think of NavController as a **stack of screens**:

```
Top of stack = Current screen
```

---

#  NavController Core Methods
---

## 1️⃣ `navigate(route)`

➡️ Go forward (push a new screen onto the stack)

### Usage:

```kotlin
navController.navigate("details")
```

### Visual:

```
Stack before: [Home]
Action: navigate("details")
Stack after:  [Home, Details]  ← now you're here
```

---

## 2️⃣ `popBackStack()`

⬅️ Go back to previous screen (pops the top screen)

```kotlin
navController.popBackStack()
```

### Visual:

```
Stack before: [Home, Details]
Action: popBackStack()
Stack after:  [Home]   ← back here
```

---

## 3️⃣ `popBackStack(route, inclusive)`

⬅️ Go back **to a specific screen**, removing screens above it.

```kotlin
navController.popBackStack("home", inclusive = false)
```

### Visual Example:

Stack:

```
[Home, List, Details]
```

Call:

```
popBackStack("List", inclusive = false)
```

🔥 Result:

```
[Home, List]  ← Details removed
```

If we do:

```kotlin
popBackStack("List", inclusive = true)
```

Result:

```
[Home]   ← List & Details removed
```

---

## 4️⃣ `navigate(route) { popUpTo(...) }`

🧼 Clears part of backstack **when navigating forward.**

### Example: After login, remove login screen so user cannot go back.

```kotlin
navController.navigate("home") {
    popUpTo("login") { inclusive = true }
}
```

### Visual:

```
Before: [Splash, Login]
After navigate: [Splash, Home]
Then popUpTo removes Login → [Splash, Home]
```

If `inclusive=true`, Login is removed.

---

## 5️⃣ `navigateUp()`

Similar to Back button behavior — but respects the navigation graph hierarchy.

```kotlin
navController.navigateUp()
```

Use when using **nested graphs** (bottom navigation, tabs, etc.).

---

## 6️⃣ `currentBackStackEntry`

🔍 Get arguments or savedState values from current screen.

```kotlin
val entry = navController.currentBackStackEntry
```

Example reading passed argument:

```kotlin
val id = entry?.arguments?.getString("id")
```

---

## 7️⃣ `previousBackStackEntry`

Useful for **sending data back**.

Example:

```kotlin
navController.previousBackStackEntry?.savedStateHandle?.set("result", "Hello!")
navController.popBackStack()
```

Visual:

```
Before: [Home, Picker]
After saving & pop: [Home] → Home receives result
```

---

# 🧪 Mini Practical Example App Flow

Let's build a 3-screen flow using all techniques:

```
Home → List → Details
     ↖──────── Back
```

### Navigation Setup:

```kotlin
NavHost(navController, "home") {
    composable("home") {
        HomeScreen(
            onNext = { navController.navigate("list") }
        )
    }

    composable("list") {
        ListScreen(
            onItemClick = { id ->
                navController.navigate("details/$id")
            },
            onBack = { navController.popBackStack() }
        )
    }

    composable("details/{id}") { backStack ->
        val id = backStack.arguments?.getString("id")
        DetailsScreen(id, onBack = { navController.popBackStack() })
    }
}
```

---

# 🧠 Summary Table (Memory Shortcut)

| Method                           | Purpose                                | Stack Effect | Example        |
| -------------------------------- | -------------------------------------- | ------------ | -------------- |
| `navigate(route)`                | Go forward                             | Push         | `"details"`    |
| `popBackStack()`                 | Go back one                            | Pop          | back press     |
| `popBackStack(route, inclusive)` | Jump back removing screens             | Pop multiple | `"home", true` |
| `navigate { popUpTo(...) }`      | Clear previous history when navigating | Replace      | login → home   |
| `navigateUp()`                   | Hierarchy-aware back                   | Pop          | nested nav     |
| `currentBackStackEntry`          | Read data from current screen          | No change    | get args       |
| `previousBackStackEntry`         | Send data back                         | No change    | return result  |

---


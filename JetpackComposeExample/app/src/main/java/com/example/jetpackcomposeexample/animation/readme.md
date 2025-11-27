

1. Animation Cheatsheet

![cheatsheet](https://developer.android.com/static/develop/ui/compose/images/animations/compose_animation_decision_tree_v2.jpg)

JETPACK COMPOSE ANIMATION – IMPORTANT NOTES (2025 EDITION)
==========================================================

1) animate*AsState (animateDpAsState, animateFloatAsState, animateColorAsState)
---------------------------------------------------------------------------
Use case:
- Simple state → simple UI change (size, color, alpha, padding).
- Automatically animates whenever its targetValue changes.

Good for:
- Button animations, color feedback, small UI transitions.

Pitfalls:
- Not for complex multi-property animations.
- Not good for gesture-driven movement (no manual control).

Key concept:
- Value is derived in composition, automatically animated.


2) Animatable
---------------------------------------------------------------------------
Use case:
- Full manual control over animation (start, stop, snap, velocity).
- Gestures, physics, drag animations, custom movement.

Why we need scope + launch:
- animateTo() is a suspend function.
- Suspend functions cannot run inside normal callbacks.
- Use rememberCoroutineScope() then scope.launch { anim.animateTo(...) }

Pitfalls:
- Don’t call animateTo() inside onClick without scope.
- Don’t wrap animateTo() inside LaunchedEffect inside a button — wrong trigger model.

Why Animatable?
- Supports interruption, cancellation, snapTo(), decay animations.
- Good for “finger movement” and natural physics.


3) AnimatedVisibility
---------------------------------------------------------------------------
Use case:
- Show/Hide UI with animation.

Good for:
- Collapsible sections, dropdowns, tooltips, modals.

Pitfalls:
- AnimatedVisibility re-composes its content only when visible.
- Heavy content inside AnimatedVisibility can cause recomposition jank.

Notes:
- enter/exit transitions can be combined using + operator.


4) AnimatedContent
---------------------------------------------------------------------------
Use case:
- Animate between two distinct states (like number changing, screen switching).
- Crossfades + size transitions by default.

Good for:
- Counters, small view transitions, state changes.

Pitfalls:
- Keep targetState lightweight.
- Do not use to animate large lists or heavy screens (performance).


5) updateTransition
---------------------------------------------------------------------------
Use case:
- Animate multiple properties together based on one state.

Example:
- size + color + alpha all synced to the same boolean.

Why use it:
- Guarantees all animated values change in a coordinated manner.

Pitfalls:
- Overkill for single-property animations.
- Prefer animate*AsState when simple.


6) rememberInfiniteTransition
---------------------------------------------------------------------------
Use case:
- Looping animations (pulsing, breathing, shimmer).

Pitfalls:
- Always looping → cost on battery & performance.
- Use with caution for persistent animations.


7) Modern Draggable + Animatable (2025 replacement for swipeable)
---------------------------------------------------------------------------
Why not use swipeable (deprecated)?
- Removed from Material library.
- Material3 does NOT provide swipeable.
- Must build custom gesture behavior.

Use case:
- Swipe cards, sliders, dismissible elements.

Why use Animatable here:
- Manual control over physics (snap, animate back).
- Smooth end-of-gesture animations.

Why draggable:
- Low-level gesture detector.
- Reports raw delta; we control movement precisely.

Pitfalls:
- Must clamp values manually (coerceIn).
- Must animate back to final state in onDragStopped.


8) Canvas + animation
---------------------------------------------------------------------------
Use case:
- Custom shapes, charts, loading indicators.

Pitfalls:
- Canvas is immediate-mode drawing → redraw each frame.
- Keep Canvas lightweight; avoid heavy computations inside drawScope.


9) animateContentSize
---------------------------------------------------------------------------
Use case:
- Smoothly animate layout size changes of a container when its children's size changes.
- Perfect for expanding/collapsing content when the measured size of children changes because of text, visibility toggles, or dynamic content.
- Great for lists where an item expands inline, accordions, or dynamic form fields.

API example:
- Column(
  modifier = Modifier.animateContentSize(
  animationSpec = spring(
  dampingRatio = Spring.DampingRatioNoBouncy,
  stiffness = Spring.StiffnessMedium
  )
  )
  )

Why use it:
- Automatically interpolates the container's measured size between old and new sizes.
- You don't need to animate explicit width/height values; Compose handles measurement -> animation.

Why choose over AnimatedContent:
- animateContentSize animates the container's size only.
- AnimatedContent replaces content and provides crossfade/slide-like transitions for the content itself.
- Use animateContentSize when the *layout* size changes matter but you want the content to remain stable.

Pitfalls:
- Triggers a layout pass for the duration of the animation (resource cost).
- Use with care for large trees — animating top-level containers can cause expensive re-layouts.
- Nested animateContentSize can interact badly — coordinate or avoid multiple simultaneous layout anims.
- Avoid heavy recomposition inside the container during the animation window.

Notes on spring params:
- dampingRatio = Spring.DampingRatioNoBouncy → no overshoot/bounce
- stiffness controls speed; medium is a nice default for UI resizing.


KEY PITFALLS TO AVOID
======================

1) Using LaunchedEffect inside onClick → WRONG
    - LaunchedEffect is a Compose-side effect, not for event handlers.
    - Use rememberCoroutineScope() + scope.launch { ... }.

2) Trying to animate a value without remembering it.
    - Always remember Animatable, state, or transition.

3) Running suspend animations directly inside composition.
    - Never call animateTo() directly inside @Composable (must be launched).

4) Overusing AnimatedVisibility / animateContentSize for heavy content.
    - Causes unnecessary recomposition and layout cost.

5) Using outdated Material2 APIs (swipeable, FractionalThreshold).
    - Deprecated; prefer draggable + Animatable or other modern replacements.


WHY WE USED scope AND launch
============================ 

- animateTo(), snapTo(), animateDecay() are suspend functions.  
- rememberCoroutineScope() provides a lifecycle-tied CoroutineScope inside a composable.  
- scope.launch { ... } starts animation safely without blocking UI.  
- Ensures cancellation when composable disposes and prevents "Composable invocations can only happen..." errors.


WHY WE USED DIFFERENT ANIMATION TYPES
=====================================

1) animate*AsState → simple UI value animation
   Easy, declarative, tied to state.

2) Animatable → complex, manual, gesture-controlled animation
   Needed for physics-like movement.

3) updateTransition → coordinated animation
   Multi-property animation tied to one state machine.

4) AnimatedVisibility → enter/exit effects for appearing items.

5) AnimatedContent → smoothly transition between different content.

6) InfiniteTransition → looping animation (breathing/pulse).

7) draggable + Animatable → modern, manual swipe interactions.

8) Canvas + animation → custom graphics or dynamic drawing.

9) animateContentSize → smooth resizing when measured child size changes.


TYPICAL EXAMPLES (short)
=========================

- Expandable card content:  
  Column { Header(); Body(visible) } with Body toggling visibility +  
  Column(modifier = Modifier.animateContentSize()) to smooth height change.

- Counter change:  
  AnimatedContent(targetState = count) { Text("$it") }  

- Floating toggle:  
  val offset = remember { Animatable(0f) }; onDrag use offset.snapTo/animateTo.  

- Pulse:  
  val t = rememberInfiniteTransition(); val a by t.animateFloat(...)  

- Layout resize w/o manual size calculation:  
  Modifier.animateContentSize() — lets Compose interpolate between measurements.  


THE BIG PICTURE (Mental Model)
==============================
- Simple state change → animate*AsState
- Multiple UI changes → updateTransition
- Show/Hide → AnimatedVisibility
- Replace content → AnimatedContent
- Continuous loop → InfiniteTransition
- Drag/Physics → Animatable + draggable
- Custom drawing → Canvas animations
- Measured layout size changes → animateContentSize

END


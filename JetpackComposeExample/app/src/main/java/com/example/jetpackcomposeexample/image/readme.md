

```plaintext
Jetpack Compose Image Loading

──────────────────────────────────────────
1. Local PNG/JPG Resources
──────────────────────────────────────────
• Loaded using painterResource().
• Supports PNG, JPG, WEBP (static), BMP.
• Fastest loading method because assets are bundled.
• Rounded corners via Modifier.clip(RoundedCornerShape()).
• Best for UI icons, offline images, splash screens.

Pitfalls:
• Very large PNGs increase app size.
• No caching layer (not needed since local).

──────────────────────────────────────────
2. Vector XML Drawables
──────────────────────────────────────────
• Loaded using painterResource().
• Perfect for icons and logos.
• Scale infinitely without pixelation.
• Very light on APK size.

Pitfalls:
• Complex vector paths can be slow to render.
• Avoid huge SVG-like vectors in drawable XML.

──────────────────────────────────────────
3. Loading Images with Picasso
──────────────────────────────────────────
• Uses Picasso.get().load(url).into(target).
• Requires manual bitmap state management.
• Must handle placeholder/error drawables manually.
• Must cancel request using picasso.cancelRequest().

Pitfalls:
• Picasso is older; not optimized for Compose.
• No automatic lifecycle awareness with Compose.
• Imperative API — more code, more errors.

──────────────────────────────────────────
4. Loading Images with Glide
──────────────────────────────────────────
• Requires CustomTarget<Bitmap>().
• Must clear the target in onDispose().
• Works well for bitmaps and caching.

Pitfalls:
• Glide does not integrate naturally with Compose.
• Must manage state + cleanup manually.
• More chances of memory leaks if clear() is forgotten.

──────────────────────────────────────────
5. Loading Images with Coil 3 (Recommended)
──────────────────────────────────────────
• Best modern image loader for Jetpack Compose.
• Uses AsyncImage → minimal code, auto lifecycle support.
• Handles caching, decoding, errors, placeholders, crossfade.
• Designed specifically for declarative UI.

Pitfalls:
• Requires Kotlin 2.0+ and Compose Compiler 1.6+.
• Not compatible with older projects using Kotlin 1.9.x.
• coil3.* API is different from coil.* (Coil 2).
• Many tutorials still use Coil 2 — mixing them breaks imports.

──────────────────────────────────────────
6. SVG Support with Coil 3
──────────────────────────────────────────
• Add dependency:
      implementation "io.coil-kt.coil3:coil-svg:3.x.x"
• Coil automatically registers SvgDecoder.
• AsyncImage can load SVG from URL or local resources.

Pitfalls:
• SVGs with embedded scripts/animations may not render.
• Large/complex SVGs decode slower.

──────────────────────────────────────────
7. GIF / Animated WebP Support with Coil 3
──────────────────────────────────────────
• Add dependency:
      implementation "io.coil-kt.coil3:coil-gif:3.x.x"
• AsyncImage automatically plays animations.
• No custom decoder or ImageLoader required.

Pitfalls:
• Very large GIFs consume a lot of memory.
• GIF playback uses CPU — prefer animated WebP.

──────────────────────────────────────────
8. Why Coil 3 Is Preferred Over Picasso/Glide
──────────────────────────────────────────
• Built for Jetpack Compose & Kotlin Multiplatform.
• AsyncImage works with recomposition and state.
• Cleaner code — no Targets, no custom callbacks.
• Supports:
      - SVG
      - GIF
      - Animated WebP
      - HEIF
      - AVIF
• Includes modern OkHttp-based pipeline.

Pitfalls:
• Cannot be used with old Compose Compiler.
• Requires migrating project to Kotlin 2.0 or higher.
• coil3.request.ImageRequest API changed from Coil 2.

──────────────────────────────────────────
9. Useful Image Modifiers (Compose)
──────────────────────────────────────────
• fillMaxWidth()           → Stretch image horizontally.
• sizeIn(maxHeight)        → Prevent OOM with very large images.
• clip(RoundedCornerShape) → Rounded corners.
• padding()                → Spacing around image.
• aspectRatio()            → Maintain aspect ratio.
• border()                 → Add border around image.

Pitfalls:
• Never load unbounded size images without sizeIn().
• Avoid full-screen images without constraints.

──────────────────────────────────────────
10. Major Differences: Coil 2 vs Coil 3
──────────────────────────────────────────
Coil 2 packages:   coil.*
Coil 3 packages:   coil3.*

Breaking changes:
• ImageLoader API changed.
• Request builders changed.
• Decoder pipeline updated.
• coil-compose moved from coil.compose to coil3.compose.

Pitfalls:
• DO NOT mix Coil 2 & Coil 3 dependencies.
• DO NOT import coil.* with coil3.* — types are incompatible.
• Migrating from Coil 2 → Coil 3 requires code updates.

──────────────────────────────────────────
11. Common Pitfalls When Using Coil 3
──────────────────────────────────────────
• Requires Kotlin 2.0+ — older projects will crash during build.
• Requires latest Compose Compiler (1.6+).
• Older articles or snippets using Coil 2 will not compile.
• Must update imports from:
      coil.request.ImageRequest  → coil3.request.ImageRequest
• Animated image support requires decoder modules.
• Coil 3 is still evolving; API may change in minor versions.

──────────────────────────────────────────
SUMMARY
──────────────────────────────────────────
• Local PNG/JPG → painterResource()
• Vector XML → painterResource()
• Picasso → Works, but outdated for Compose
• Glide → Works, but not Compose-first
• Coil 3 → Best option for Compose apps (modern, simple)
• For SVG → Add coil-svg
• For GIF/WebP → Add coil-gif
• Avoid mixing Coil 2 & Coil 3
• Coil 3 requires upgrading your Kotlin & Compose setup

```
# Cliplo Asset Style Contract

Every Cliplo-created asset must feel like a fragment of the same memory system.
This contract applies to hand-authored SVGs, generated illustrations, CSS
surfaces, and future motion assets.

## The visual idea: captured memory in motion

Cliplo visuals are made of soft, rounded fragments that gather, nest, route, or
protect information. They should feel calm and tactile—not mechanical, shiny,
or decorative for decoration's sake.

## Shared rules

1. **Use the memory loop.** Build around 2–4 rounded fragments, a subtle route,
   or a nested container. Avoid isolated generic symbols when a Cliplo-specific
   relationship can be shown.
2. **Keep the silhouette quiet.** Rounded corners, open breathing room, and
   simple geometry are more important than detail. An asset must remain legible
   at 20–24 px.
3. **Use restrained depth.** SVGs use outline plus one low-opacity fill at most.
   Raster art uses soft overlap, grain, and a diffuse glow—not chrome, glass
   reflections, or heavy shadows.
4. **Be theme-native.** Core SVGs use `currentColor`, `var(--cliplo-accent)`,
   or a documented semantic token. Never hard-code a light background into an
   asset. Dark and light mode must share the same form.
5. **Color carries meaning, not identity.** Mineral green is Cliplo's anchor.
   A single muted semantic accent may distinguish a collection, but each asset
   still uses the same material, geometry, and line weight.
6. **No embedded copy or third-party marks.** Artwork supports the interface;
   labels and accessibility text stay in the UI.

## Native SVG specification

- `viewBox="0 0 24 24"`, no fixed `width` or `height`
- `fill="none"`, `stroke="currentColor"`, `stroke-width="1.75"`
- `stroke-linecap="round"`, `stroke-linejoin="round"`
- Corners: 2–4 px radii at a 24 px viewBox
- Optional filled detail: `fill="currentColor" opacity=".14"`
- Optical padding: keep visible paths within 2–22 on both axes
- File names: lowercase kebab-case, semantic role first

## Generated illustration specification

- Use the same rounded fragments, grouped layers, and low-key depth as the
  native symbols.
- Palette: charcoal/mineral base, Cliplo green anchor, one semantic accent.
- Favor a centered isolated composition with usable breathing room around it.
- Ask for no text, no logos, no watermark, no browser/device frame, and no
  generic glossy 3D blob.

## Review gate

Before adding an asset, verify: it reads at small size; it works in dark mode;
it does not depend on an external brand; it shares the memory-loop geometry;
and it has a descriptive, accessible name in its consuming UI.

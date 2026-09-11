# Cliplo Asset Library

This is the home for original visual assets that make Cliplo recognizable
outside of its interface. Assets should express **captured memory in motion**:
deep mineral backgrounds, quiet material depth, and a restrained semantic
accent for each content type.

## Visual rules

- Keep all core symbols as dependency-free SVGs using `currentColor` where
  possible. They must work in both themes and at small Android sizes.
- Use generated raster art only for scenes, content fallbacks, and other
  detail-rich artwork. Never bake UI labels or required copy into an image.
- Use the family folders below as the source of truth. File names are lowercase
  kebab-case and describe the semantic role, not a particular screen.
- Do not use external product logos in Cliplo-created fallback art.

## Family map

| Directory | Contents | Preferred format |
| --- | --- | --- |
| `core-symbols/` | Collection, vault, search, capture, paste, and organize symbols | SVG |
| `collection-art/` | Messages, links, code, images, people, notes, and vault art | SVG or transparent raster |
| `content-fallbacks/` | Missing-preview, unsupported-source, broken-link, and offline art | Raster |
| `state-scenes/` | Empty, loading, error, permission-denied, success, and no-results states | Raster or SVG |
| `onboarding-moments/` | Copy, recognize, organize, retrieve, and protect scenes | Raster |
| `ambient-surfaces/` | Responsive backgrounds, texture, and capture-panel atmosphere | CSS or SVG |

Each family contains a local README to keep new assets consistent as the
library grows.

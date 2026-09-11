# Cliplo Art Direction

This is the generation and review rule for every Cliplo visual asset.

## The look

**Small, matte, recognisable product objects.** Cliplo art should look like it
belongs on a calm dark collection card: one clear object or relationship, two
to three soft solid colours, rounded geometry, and only enough depth to show
which piece sits in front.

Messages and Links are the reference assets for this rule.

## Non-negotiable generation rules

1. **Recognisable in one second.** A Messages asset uses speech bubbles; Links
   uses interlocked links; Code uses brackets; Images uses a picture frame;
   People uses a location pin or person marker. Do not replace meaning with
   abstract decoration.
2. **One compact object.** Use one primary form and, at most, one overlapping
   companion form. Keep the visual mass compact rather than filling the card.
3. **Flat matte material.** Solid, softly shaded fills only. Depth comes from a
   single darker overlap/underside edge—not glass, grain, chrome, glow, ribbons,
   gradients, photorealism, or large drop shadows.
4. **Transparent and text-free.** Generate isolated PNG art on a transparent
   background. Never bake labels, letters, third-party marks, or UI frames into
   the asset.
5. **Collection-card scale.** Compose in a square canvas with generous empty
   space. The object itself should occupy roughly 38–52% of the canvas and fit
   comfortably in the upper third of a 200px-wide collection card.
6. **Semantic colour, shared material.** Keep the same rounded matte treatment
   in every asset; change only the restrained collection palette.

## Collection palette map

| Collection | Primary | Companion |
| --- | --- | --- |
| Recent | Cliplo mint `#77C8AD` | warm gold `#D9A94A` |
| Links | mineral mint `#58AF92` | dark teal underside |
| Messages | warm peach `#E5B681` | Cliplo mint `#77C8AD` |
| Code | muted coral `#D17B63` | deep coral underside |
| People & places | terracotta `#D17B63` | warm sand accent |
| Images | olive `#82A95D` | warm gold `#D9A94A` |
| Notes | indigo `#7185B8` | pale lavender accent |
| Vault | Cliplo mint `#77C8AD` | deep evergreen underside |

## In-app usage

- Use `object-fit: contain`; never crop a Cliplo asset.
- Collection cards: render at 52–72px on phones, aligned to the upper-left.
- Empty/fallback states may scale to 120–160px but preserve the same visual
  mass, colour restraint, and transparent background.
- Onboarding moments may render at 180–232px, but must remain a single calm
  object group using no more than 45–55% of their source canvas. They explain
  one outcome (remember, gather, retrieve) rather than becoming a hero scene.
- Dark-card contrast is required; test every asset on `#151918` and its own
  collection tint before shipping.

## Prompt suffix

Append this instruction to every generation prompt:

> Match Cliplo’s approved collection-art language: compact, flat, matte,
> rounded product objects; one recognisable semantic cue; a subtle darker
> overlap edge only; transparent background; no glow, gradients, glass,
> texture, text, logos, UI frames, or watermark.

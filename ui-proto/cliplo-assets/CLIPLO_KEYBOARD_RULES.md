# Cliplo keyboard interaction rules

The keyboard is a retrieval surface, not a second home screen. Its default
state is Search + All clips. Collection navigation is an explicit temporary
mode opened by the roller control.

## Picker behavior

- Keep one selected value in a stable center row.
- Show only the immediately previous and next values; fade and scale them down
  so the center has a clear hierarchy.
- Keep collections in a predictable product order: Recent, Links, Messages,
  Code, Vault.
- A swipe or wheel gesture changes the centered value. Tapping the centered
  value commits it and returns to its clips.
- Do not show controls that do not help retrieve or paste the current clip.

## Motion

- Use the gesture direction: upward drag advances to the next collection;
  downward drag returns to the previous one.
- Animate the picker at roughly 220–280ms with a fast start and soft settle.
- Animate the mode swap as one bounded surface; do not animate unrelated host
  content or make the user wait before tapping.
- Respect `prefers-reduced-motion`: keep the state change and selected value,
  remove travel and scale effects.

## Type and hierarchy

- Use the app body face for search, metadata, and controls.
- Use Sora only for the selected collection name and other identity-level text.
- Keep one primary action per clip: paste. Never use decorative arrows as the
  action affordance.
- Keep the default list denser than the picker. The picker is a decision state,
  not a permanent header.

These rules follow Apple's picker guidance (stable centered selection and
predictable values), Apple's motion guidance (purposeful, brief, gesture-
consistent, cancellable motion), and Material's mobile timing guidance
(roughly 225–300ms with natural easing):

- https://developer.apple.com/design/human-interface-guidelines/motion
- https://codershigh.github.io/guidelines/ios/human-interface-guidelines/ui-controls/pickers/
- https://m1.material.io/motion/duration-easing.html

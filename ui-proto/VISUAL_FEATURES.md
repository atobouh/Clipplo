# Cliplo Visual Feature Inventory

This document lists the screens, components, and states that should exist in
the UI prototype before the native Compose build begins.

The visual goal is minimal but distinctive. Every screen should feel like part
of one product, while each surface has a clear visual purpose.

## 1. Main Library Screen

The main screen has three clearly aligned zones:

- Title zone with Cliplo identity, subtitle, and settings access
- Feed zone with the active collection label and clip cards
- Floating bottom capture/control panel above the feed

The screen should include:

- All Clips view
- Recent clips
- Source-app labels
- Clip type indicators
- Capture time
- Tags
- Copy action positioned for handedness
- Long-press affordance
- Scroll behavior behind the bottom panel
- Progressive blur behind the bottom panel

## 2. Clip Card System

Design one reusable card system with clear content variations:

- Link card
- Plain text card
- Code card
- Image card
- Phone number card
- Address card
- Messaging card
- Password or sensitive card
- Template card
- Paste-sequence card

Every card should define:

- Source icon treatment
- Content preview
- Type label
- Time metadata
- Tags
- Copy action
- Favorite state
- Pinned state
- Locked state
- Archived state
- Pressed state
- Long-content truncation

## 3. Smart Collections Screen

Create a dedicated visual home for automatic organization:

- Collection header
- Collection count
- Collection icon language
- Recent collection preview
- Links collection
- Contacts collection
- Code collection
- Images collection
- Messages collection
- Notes collection
- Sensitive collection
- Pinned collection
- Favorites collection

Collection cards should feel related but not identical. Use hierarchy,
iconography, and small semantic color differences instead of a grid of generic
cards.

The Collections screen is an organization space, not only a filter screen. It
should suggest meaningful groups such as “Create a YouTube collection” or
“Group clips from the same source” without automatically creating collections.
Suggestions can be surfaced through the bottom capture/control area or inside
the Collections screen. Users must be able to accept, dismiss, or postpone a
suggestion.

## 4. Favorites Screen

- Favorites title and count
- Favorite clip list
- Favorite card state
- Empty favorites state
- Favorite removal feedback
- Search within favorites
- Filter within favorites
- Back navigation

The empty state should be compact and intentional, not a large blank panel.

## 5. Pinned Screen

- Pinned title and count
- Pinned clip list
- Pinned indicator
- Unpin action
- Empty pinned state
- Search within pinned clips
- Back navigation

Pinned clips should be visually prioritized without using an aggressive colored
border or a second competing feed.

## 6. Custom Folders

- Folder list
- Create-folder action
- Folder icon selection
- Folder color selection
- Folder count
- Folder detail screen
- Move-to-folder action
- Multi-select folder actions
- Rename folder
- Delete folder confirmation
- Empty folder state

## 7. Search and Filter Surface

- Search mode in the bottom capture panel
- Search results state
- Search suggestions
- Recent searches
- Filter control
- Source-app filters
- Clip-type filters
- Date filters
- Folder filters
- Tag filters
- Pinned and favorite filters
- Active filter chips
- Clear filters action
- No-results state

## 8. Secure Vault

- Vault entry screen
- Locked Vault state
- Biometric prompt state
- PIN entry state
- Failed authentication state
- Empty Vault state
- Protected clip card
- Redacted clip preview
- Lock and unlock actions
- Vault folder state
- Sensitive-content suggestion
- Suggested private groups such as Google accounts, credentials, and banking
  details
- Collection-level Vault organization
- Confirm-before-grouping flow for inferred private collections

The Vault should feel calm and trustworthy, not alarmist or visually aggressive.

## 9. Keyboard Surface

Create a realistic keyboard-access prototype showing:

- Cliplo keyboard header
- Recent clips
- Search field
- Smart Collection shortcuts
- Folder shortcuts
- One-tap paste
- Locked clip state
- Paste sequence action
- Keyboard empty state
- Keyboard permission setup

The keyboard should feel like the fastest expression of Cliplo's promise:
something useful is already ready to paste.

## 10. Paste Sequences

- Sequence list
- Create-sequence flow
- Clip selection mode
- Selected clip count
- Drag-to-reorder interaction
- Sequence preview
- Paste next action
- Sequence completion state
- Save sequence action
- Empty sequence state

## 11. Templates

- Template list
- Template detail screen
- Create-template flow
- Variable tokens
- Variable input state
- Preview with filled variables
- Copy completed template
- Template folder assignment
- Empty templates state

## 12. Capture and Classification Feedback

The prototype should visibly demonstrate Cliplo's intelligence:

- Clip captured confirmation
- Source-app identified state
- Automatic classification label
- Collection assignment feedback
- Duplicate detected state
- Sensitive-content warning
- Capture paused state
- Excluded-app state
- Clipboard permission unavailable state
- Classification unavailable fallback

## 13. Onboarding Screens

- Welcome: “Copy once. Use anytime.”
- Smart organization explanation
- Local-first privacy explanation
- Capture permission explanation
- First captured clip celebration
- Keyboard access explanation
- Seven-day Pro trial explanation
- Completion screen

Onboarding should demonstrate the outcome before explaining the technology.

## 14. Pro Upgrade Surface

- Free-plan limit warning
- Feature locked state
- Pro benefits screen
- One-time purchase explanation
- Website purchase instruction
- License activation screen
- License restored state
- Invalid license state
- Offline activation state

Upgrade UI should be clear and restrained. It should not feel like an
aggressive subscription paywall.

## 15. Settings Screens

- Settings home
- Handedness control
- Light and dark theme control
- Capture pause control
- Excluded apps
- Sensitive filter
- Vault security settings
- Export library
- Import library
- Delete all data
- About Cliplo
- Pro status
- License management

## 16. Required UI States

Every major screen must have a visual treatment for:

- Default
- Loading
- Empty
- No results
- Error
- Disabled
- Pressed
- Selected
- Locked
- Offline
- Permission denied
- Success
- Undo feedback

## 17. Responsive and Accessibility Checks

- Small Android phone
- Large Android phone
- Short viewport with keyboard open
- Long clip content
- Large system font
- Screen reader labels
- Left-handed mode
- Right-handed mode
- Light theme
- Dark theme
- Reduced motion
- Minimum 44dp touch targets

## Prototype Build Order

1. Main Library with the three-zone composition
2. Clip card variations and states
3. Smart Collections
4. Favorites and Pinned screens
5. Search and filters
6. Custom folders
7. Vault
8. Keyboard surface
9. Templates and paste sequences
10. Settings and Pro upgrade states
11. Full onboarding
12. Accessibility and responsive pass

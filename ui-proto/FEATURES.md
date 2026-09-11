# Cliplo Feature List

Cliplo is a premium, offline-first personal recall system that remembers the
things people will need to reuse.

**Tagline:** Copy once. Use anytime.

## Product Positioning

Cliplo is not sold as clipboard storage. Clipboard capture is the underlying
engine. The user-facing product is instant recall and reuse.

- Clipboard history is infrastructure.
- Smart Collections are infrastructure.
- Search is infrastructure.
- The keyboard is the delivery surface.
- The product is the right thing, ready when the user needs it.

## Product Naming

- Cliplo Clipboard
- Cliplo Vault
- Cliplo Collections
- Cliplo Keyboard
- Cliplo Smart Sort

## Product Principles

- Capture once, reuse instantly.
- Keep all core data on the user's device.
- Make every clip easy to find, understand, and reuse.
- Keep sensitive information private and protected.
- Work from the Cliplo app, the phone keyboard, and the web experience.

## 1. Automatic Clip Capture

- Capture copied text, links, phone numbers, addresses, code, and images.
- Record when a clip was copied.
- Track the source application when available.
- Identify clips copied from WhatsApp and other messaging apps.
- Detect duplicate clips and avoid unnecessary duplicates.
- Support manual paste capture when system clipboard access is limited.
- Continue working fully offline.

## 2. Smart Classification

Automatically classify clips in the background:

- Links and URLs
- Phone numbers
- Email addresses
- Physical addresses
- Code snippets
- Images
- Plain text and notes
- WhatsApp and messaging content
- Passwords and sensitive data
- One-time passwords and authentication codes

Classification should remain editable by the user.

## 3. Smart Collections

Automatic collections provide instant organization without requiring manual setup:

- All Clips
- Links
- Contact Information
- Code
- Images
- Messaging
- Notes
- Sensitive
- Recent Clips
- Pinned Clips
- Favorites

Users can create custom collections and folders alongside the automatic ones.

## 4. Folders, Tags, and Organization

- Create, rename, reorder, and delete custom folders.
- Move one or multiple clips into a folder.
- Add and remove manual tags.
- Assign a clip to multiple folders where useful.
- Pin important clips.
- Favorite clips for quick access.
- Archive clips without deleting them.
- Move clips between automatic collections and custom folders.
- Preserve source app, type, and capture time as clip metadata.

## 5. Search and Filters

- Search clip content instantly on-device.
- Search by title, text, URL, source app, folder, and tag.
- Filter by clip type.
- Filter by source application.
- Filter by date or time range.
- Filter pinned, favorite, archived, or sensitive clips.
- Combine multiple filters.
- Highlight matching text in search results.

## 6. Clip Actions

- Copy a clip back to the system clipboard.
- Paste a clip into the current app through the keyboard.
- Open links in the appropriate app or browser.
- Share clips through the system share sheet.
- Edit text clips.
- Duplicate clips.
- Print or export clips where supported by the platform.
- Delete individual clips or multiple selected clips.
- Undo accidental deletion.

## 7. Secure Vault

- Mark individual clips as protected.
- Protect entire folders or collections.
- Require biometrics or a PIN to view protected content.
- Require authentication before copying or pasting protected content.
- Automatically detect and suggest protecting passwords, payment details,
  addresses, and authentication codes.
- Hide sensitive content in previews and notifications.
- Keep vault content encrypted locally.

## 8. Quick Paste Sequences

- Select 3 to 8 clips into a paste queue.
- Paste selected clips one after another with one action.
- Reorder clips in a queue.
- Save frequently used queues for reuse.
- Support form filling, addresses, contact details, and multi-part messages.
- Require confirmation before pasting protected clips.

## 9. Clip Templates

- Save reusable text snippets and templates.
- Add variables such as name, date, order number, and address.
- Fill variables before copying or pasting.
- Create templates from existing clips.
- Organize templates into custom folders.
- Pin frequently used templates.

## 10. Keyboard Access

- Browse recent clips from the phone keyboard.
- Search the complete clip library from the keyboard.
- Browse Smart Collections and folders.
- Paste a single clip or run a saved paste sequence.
- Access protected clips through biometrics or PIN.
- Keep keyboard access fast and usable without opening the main app.

## 11. Main App Experience

- Bottom capture and control panel always available on the main feed.
- Feed of recent clips with source, type, time, and tags.
- Smart Collections available from the main navigation.
- Clip detail view with full content and actions.
- Long-press actions for pin, favorite, share, archive, copy, and delete.
- Light and dark themes.
- Responsive web experience.
- Mobile-first Android app experience.

## 12. Data, Privacy, and Offline Behavior

- Store clip data locally by default.
- No cloud account required for core clipboard functionality.
- No clip content sent to a server for basic classification.
- Provide local export and import.
- Support encrypted backups as a future extension.
- Explain clipboard permissions clearly.
- Let users pause capture or exclude specific apps.
- Let users delete all stored data permanently.

## 13. Plans and Access

### Full Version Trial

- Available free on the website and Google Play Store.
- New users receive the complete feature set for 7 days.
- The trial includes unlimited organization, folders, Smart Collections,
  search, filtering, keyboard access, and protected clips.

### Free Version After Trial

- Maximum of 50 stored clips.
- Recent clipboard access remains available.
- Basic capture and copying remain available.
- Advanced organization is disabled, including custom folders and manual
  organization.
- Smart Collections and premium workflows may be limited.

### Product Decision To Confirm

The exact behavior for clips older than the free 50-clip limit must be defined:

- Keep older clips stored but locked until upgrading; or
- Automatically remove the oldest clips; or
- Keep older clips viewable but prevent new organization.

The app must communicate the limit before data is removed or becomes
inaccessible.

## Suggested Delivery Priority

### Phase 1: Core Differentiator

- Automatic capture
- Source-app tracking
- Smart classification
- Smart Collections
- Custom folders
- Search and filters
- Pinning and favorites
- Offline storage

### Phase 2: Premium Value

- Secure Vault
- Biometrics and PIN
- Keyboard access
- Bulk actions
- Advanced export and import

### Phase 3: Power Workflows

- Paste queues
- Saved paste sequences
- Templates with variables
- Multi-device or encrypted backup options

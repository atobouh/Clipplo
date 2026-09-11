# Cliplo Product and UX Decisions

This document records decisions that should guide the prototype and the future
Jetpack Compose implementation.

## Product Feeling

Cliplo should feel rich, premium, and calm. The interface can contain powerful
organization, but it should never feel like an administration dashboard.

- Reveal complexity progressively.
- Prefer suggestions over forced automation.
- Keep the main feed easy to scan.
- Use strong visual hierarchy instead of adding more controls.
- Let the user feel in control of their memory.
- Make every important action available without making every action visible.

## Collections Are an Organization Space

The Collections tab is where users organize everything. It is not just another
filter screen.

The tab should contain:

- Smart collections
- User-created collections
- Suggested collections
- Collection counts
- Collection previews
- A calm path to create a custom collection

## Suggest, Do Not Automatically Create

Cliplo should not create a new collection every time it notices a source app.
That would create clutter and make the user feel that the app is taking over.

Instead, Cliplo should suggest a collection when a meaningful pattern appears:

- Create a YouTube collection
- Create a Telegram collection
- Group clips from the same source
- Create a collection for repeated links
- Create a collection for frequently reused code

Suggestions should appear in the capture/control area or inside the Collections
tab, not as intrusive alerts.

The user can accept, dismiss, or postpone each suggestion. Rejected suggestions
should not repeatedly return without a meaningful change in the data.

## Collection Creation Flow

1. The user opens the Collections tab.
2. Suggested groups appear above or alongside existing collections.
3. The user taps a suggestion such as “Create a YouTube collection.”
4. Cliplo previews the clips that will be included.
5. The user confirms or edits the collection name.
6. The collection is created without moving or deleting the original clips.

Custom collections use the same flow but begin with an empty selection or a
manual clip selection.

## Collection and Navigation Relationship

The top-right plus button should not remain as a prominent standalone control in
the Collections screen. Collection creation should be available through the
bottom capture/control area or a contextual create action.

The bottom area can suggest actions such as:

- Create a YouTube collection
- Create a Telegram collection
- Group these clips
- Save this as a collection
- Create custom collection

These suggestions should be visually quiet and dismissible. They should feel
like Cliplo noticed something useful, not like it is requesting configuration.

## Vault Organization

Vault is not only a locked list. It is a private collection space for sensitive
information.

Vault should support inferred private groups such as:

- Google accounts
- Usernames and passwords
- Banking details
- Addresses
- Authentication codes
- Private work credentials

For example, seven Google account clips should be visually grouped as one
suggested private collection rather than displayed as seven unrelated secrets.

Possible suggestions include:

- Create a Google accounts collection
- Group these credentials
- Add these clips to a private collection
- Protect this collection with Vault

Inference must remain conservative. Cliplo should suggest a grouping when the
relationship is clear, but the user must confirm before it becomes a Vault
collection.

## Vault Visual Rules

- Keep Vault visually distinct through material, iconography, and access state.
- Do not make Vault visually loud or intimidating.
- Hide sensitive previews by default.
- Use collection cards instead of a long wall of locked rows.
- Let View reveal one item at a time.
- Keep Search and Paste as the only primary Vault navigation actions.
- Do not expose Pin and Favorite controls inside Vault.
- Keep locked, unlocked, and authentication states visually obvious.

## Main Screen Relationship

The main screen remains simple:

- Header and Cliplo identity
- Recent clip feed
- One Vault strip
- Bottom capture/control panel

Collections is the deeper organization space. It should not turn the main feed
into a grid of every possible category.

## Visual Quality Bar

Every collection surface should feel:

- Rich without being busy
- Premium without being ornamental
- Intelligent without being unpredictable
- Organized without feeling rigid
- Distinctive without breaking the Cliplo visual system
- Comfortable for frequent one-handed use

The user should always understand where they are, what Cliplo noticed, and what
will happen before confirming an organizational action.

/* Cliplo Keyboard: a collection wheel for fast retrieval. */
(function () {
  var screen = document.getElementById("keyboardScreen");
  var panel = document.getElementById("keyboardPanel");
  var search = document.getElementById("keyboardSearch");
  var empty = document.getElementById("keyboardEmpty");
  var nav = document.getElementById("keyboardCollectionNav");
  var modeToggle = document.getElementById("keyboardModeToggle");
  if (!screen || !panel || !search || !nav || !modeToggle) return;

  var collections = ["Recent", "Links", "Messages", "Code", "Vault"];
  var collectionIndex = 0;
  var dragStart = null;
  var collectionMode = false;
  var clipDragStart = null;
  var clipDragMoved = false;
  var clipTrack = panel.querySelector(".keyboard-clips");
  var clipIndex = 0;
  var clipHeight = 0;
  if (clipTrack) {
    var clipItems = Array.prototype.slice.call(clipTrack.querySelectorAll(".keyboard-clip"));
    var track = document.createElement("div");
    track.className = "keyboard-clip-track";
    clipItems.forEach(function (clip) { track.appendChild(clip); });
    clipTrack.appendChild(track);
    clipTrack = track;
  }

  window.openKeyboard = function () {
    collectionIndex = 0;
    toggleCollectionMode(false);
    var clips = panel.querySelector(".keyboard-clips");
    if (clips) clips.scrollTop = 0;
    screen.classList.add("is-open");
    screen.setAttribute("aria-hidden", "false");
    document.body.classList.add("keyboard-open");
    requestAnimationFrame(measureClipTrack);
    setTimeout(function () { search.focus(); }, 180);
  };
  window.closeKeyboard = function () {
    screen.classList.remove("is-open");
    screen.setAttribute("aria-hidden", "true");
    document.body.classList.remove("keyboard-open");
  };
  window.clearKeyboardSearch = function () {
    search.value = "";
    filterClips();
    search.focus();
  };

  function wrap(value) {
    return (value + collections.length) % collections.length;
  }
  function renderCollection() {
    var prev = nav.querySelector('[data-position="prev"]');
    var current = nav.querySelector('[data-position="current"]');
    var next = nav.querySelector('[data-position="next"]');
    prev.textContent = collections[wrap(collectionIndex - 1)];
    current.textContent = collections[collectionIndex];
    next.textContent = collections[wrap(collectionIndex + 1)];
    current.setAttribute("aria-label", "Selected collection: " + collections[collectionIndex]);
    var label = panel.querySelector(".keyboard-label");
    if (label) label.textContent = collectionIndex === 0 ? "All clips" : collections[collectionIndex];
    var clips = panel.querySelector(".keyboard-clips");
    if (clips) clips.scrollTop = 0;
    filterClips();
  }
  function moveCollection(direction) {
    collectionIndex = wrap(collectionIndex + direction);
    renderCollection();
    nav.classList.remove("is-shifting");
    void nav.offsetWidth;
    nav.classList.add("is-shifting");
    setTimeout(function () { nav.classList.remove("is-shifting"); }, 240);
  }
  function toggleCollectionMode(force) {
    collectionMode = typeof force === "boolean" ? force : !collectionMode;
    panel.classList.toggle("is-collection-mode", collectionMode);
    modeToggle.classList.toggle("is-active", collectionMode);
    modeToggle.setAttribute("aria-pressed", String(collectionMode));
    modeToggle.setAttribute("aria-label", collectionMode ? "Return to clips" : "Choose collection");
    if (collectionMode) renderCollection();
    else { filterClips(); search.focus(); }
  }
  function chooseCollection() {
    toggleCollectionMode(false);
    if (typeof window.toast === "function") window.toast(collections[collectionIndex] + " selected");
  }
  function filterClips() {
    var query = search.value.trim().toLowerCase();
    var active = collections[collectionIndex];
    var visible = 0;
    panel.querySelectorAll(".keyboard-clip").forEach(function (clip) {
      var matchesCollection = active === "Recent" || clip.dataset.keyboardCollection === active;
      var matchesQuery = !query || clip.textContent.toLowerCase().indexOf(query) > -1;
      clip.hidden = !matchesCollection || !matchesQuery;
      if (!clip.hidden) visible++;
    });
    clipIndex = 0;
    measureClipTrack();
    if (clipTrack) clipTrack.style.transform = "translate3d(0, 0, 0)";
    setActiveClip();
    if (empty) empty.classList.toggle("is-visible", visible === 0);
  }
  function measureClipTrack() {
    if (!clipTrack) return;
    var first = clipTrack.querySelector(".keyboard-clip:not([hidden])");
    if (!first) {
      clipHeight = 0;
      clipTrack.parentElement.style.height = "0px";
      clipTrack.parentElement.style.maxHeight = "0px";
      return;
    }
    clipHeight = first.getBoundingClientRect().height;
    clipTrack.parentElement.style.height = clipHeight + "px";
    clipTrack.parentElement.style.maxHeight = clipHeight + "px";
  }
  function setActiveClip() {
    var visible = Array.prototype.filter.call(panel.querySelectorAll(".keyboard-clip"), function (clip) { return !clip.hidden; });
    panel.querySelectorAll(".keyboard-clip").forEach(function (clip) { clip.classList.remove("is-active"); });
    if (visible[clipIndex]) visible[clipIndex].classList.add("is-active");
  }
  function moveClip(direction) {
    var visible = Array.prototype.filter.call(panel.querySelectorAll(".keyboard-clip"), function (clip) { return !clip.hidden; });
    if (!visible.length || !clipTrack) return;
    clipIndex = Math.max(0, Math.min(visible.length - 1, clipIndex + direction));
    measureClipTrack();
    clipTrack.style.transform = "translate3d(0, " + (-clipIndex * clipHeight) + "px, 0)";
    setActiveClip();
  }
  function paste(clip) {
    if (clip.dataset.keyboardCollection === "Vault") {
      if (typeof window.toast === "function") window.toast("Unlock Vault to use this clip");
      return;
    }
    var composer = screen.querySelector(".keyboard-composer");
    if (composer) composer.textContent = clip.dataset.keyboardValue;
    if (typeof window.toast === "function") window.toast("Pasted");
    window.closeKeyboard();
  }

  search.addEventListener("input", filterClips);
  modeToggle.addEventListener("click", function () { toggleCollectionMode(); });
  nav.querySelectorAll(".keyboard-collection-name").forEach(function (button) {
    button.addEventListener("click", function () {
      var position = button.dataset.position;
      if (position === "prev") moveCollection(-1);
      if (position === "next") moveCollection(1);
      if (position === "current") chooseCollection();
    });
  });
  nav.addEventListener("wheel", function (event) {
    event.preventDefault();
    moveCollection(event.deltaY > 0 ? 1 : -1);
  }, { passive: false });
  nav.addEventListener("pointerdown", function (event) {
    dragStart = event.clientX;
    nav.setPointerCapture(event.pointerId);
  });
  nav.addEventListener("pointerup", function (event) {
    if (dragStart === null) return;
    var distance = event.clientX - dragStart;
    if (Math.abs(distance) > 16) moveCollection(distance < 0 ? 1 : -1);
    dragStart = null;
  });
  nav.addEventListener("keydown", function (event) {
    if (event.key === "ArrowDown" || event.key === "ArrowRight") { event.preventDefault(); moveCollection(1); }
    if (event.key === "ArrowUp" || event.key === "ArrowLeft") { event.preventDefault(); moveCollection(-1); }
  });
  panel.querySelectorAll(".keyboard-clip").forEach(function (clip) {
    clip.addEventListener("click", function () { paste(clip); });
  });
  panel.querySelector(".keyboard-clips").addEventListener("wheel", function (event) {
    event.preventDefault();
    moveClip(event.deltaY > 0 ? 1 : -1);
  }, { passive: false });
  panel.querySelector(".keyboard-clips").addEventListener("pointerdown", function (event) {
    clipDragStart = event.clientY;
    clipDragMoved = false;
    this.setPointerCapture(event.pointerId);
  });
  panel.querySelector(".keyboard-clips").addEventListener("pointermove", function (event) {
    if (clipDragStart === null) return;
    if (Math.abs(event.clientY - clipDragStart) > 12) clipDragMoved = true;
  });
  panel.querySelector(".keyboard-clips").addEventListener("pointerup", function (event) {
    if (clipDragStart === null) return;
    var distance = event.clientY - clipDragStart;
    if (Math.abs(distance) > 16) moveClip(distance < 0 ? 1 : -1);
    clipDragStart = null;
    clipDragMoved = false;
  });
  panel.querySelector(".keyboard-clips").addEventListener("click", function (event) {
    if (clipDragMoved) {
      event.preventDefault();
      event.stopPropagation();
    }
  }, true);
  renderCollection();
  window.addEventListener("resize", measureClipTrack);
})();

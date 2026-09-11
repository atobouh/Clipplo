/* Pinned and Favorites sections. Kept separate so collection composition can
   evolve independently from the home feed. */
(function () {
  var mount = document.getElementById("collectionSections");
  if (!mount) return;
  mount.innerHTML =
    '<section class="pinned-sec show" id="pinPreview">' +
      '<div class="label"><span class="iconify" data-icon="lucide:pin"></span> <b>Pinned</b><span class="seeall" onclick="setView(\'pinned\')">See all</span></div>' +
      '<div id="pinPreviewList"></div>' +
    '</section>' +
    '<section class="pinned-sec" id="favPreview">' +
      '<div class="label"><span class="iconify" data-icon="lucide:heart" style="color:var(--accent)"></span> <b>Favorites</b><span class="seeall" onclick="setView(\'favorites\')">See all</span></div>' +
      '<div id="favPreviewList"></div>' +
    '</section>';
})();

/* Settings screen. Kept separate from the feed and collection surfaces. */
(function () {
  var mount = document.getElementById("settingsScreenMount");
  if (!mount) return;
  mount.innerHTML = `
    <div class="back-row">
      <button class="icon-btn" onclick="showScreen('screen-home')"><span class="iconify" data-icon="lucide:arrow-left"></span></button>
      <div class="settings-title">Settings</div>
    </div>
    <div class="settings-group-label">Privacy</div>
    <button class="settings-row">
      <span class="settings-row-ic"><span class="iconify" data-icon="lucide:shield-check"></span></span>
      <span class="settings-row-body"><span class="settings-row-title">Sensitive filter</span><span class="settings-row-desc">Block secrets, keys & cards</span></span>
      <span class="settings-row-tgl on"><span class="knob"></span></span>
    </button>
    <div class="settings-group-label">Appearance</div>
    <button class="settings-row" id="darkModeToggle" type="button" onclick="setDarkMode(document.documentElement.getAttribute('data-theme') !== 'dark')">
      <span class="settings-row-ic"><span class="iconify" data-icon="lucide:moon"></span></span>
      <span class="settings-row-body"><span class="settings-row-title">Dark mode</span><span class="settings-row-desc" id="darkModeDesc">Light theme</span></span>
      <span class="settings-row-tgl" id="darkModeSwitch"><span class="knob"></span></span>
    </button>
    <div class="settings-group-label">Interaction</div>
    <button class="settings-row" id="handednessToggle" type="button" onclick="toggleHandedness()">
      <span class="settings-row-ic"><span class="iconify" data-icon="lucide:hand"></span></span>
      <span class="settings-row-body"><span class="settings-row-title">Handedness</span><span class="settings-row-desc" id="handednessDesc">Right-handed layout</span></span>
      <span class="settings-row-tgl on" id="handednessSwitch"><span class="knob"></span></span>
    </button>
    <div class="settings-group-label">Navigation</div>
    <div class="settings-nav-card">
      <div class="settings-nav-intro">Choose the order of your main capture modes.</div>
      <div class="settings-nav-list" id="settingsNavList" aria-label="Main navigation order"></div>
    </div>
    <div class="settings-group-label">Data</div>
    <button class="settings-row">
      <span class="settings-row-ic"><span class="iconify" data-icon="lucide:download"></span></span>
      <span class="settings-row-body"><span class="settings-row-title">Export library</span><span class="settings-row-desc">Backup to JSON</span></span>
      <span class="settings-chev"><span class="iconify" data-icon="lucide:chevron-right"></span></span>
    </button>
    <button class="settings-row">
      <span class="settings-row-ic"><span class="iconify" data-icon="lucide:upload"></span></span>
      <span class="settings-row-body"><span class="settings-row-title">Import</span><span class="settings-row-desc">Restore from JSON</span></span>
      <span class="settings-chev"><span class="iconify" data-icon="lucide:chevron-right"></span></span>
    </button>`;
})();

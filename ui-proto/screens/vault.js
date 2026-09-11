/* Vault screen. Protected content uses the same card rhythm as the main feed. */
(function () {
  var mount = document.getElementById("vaultScreenMount");
  if (!mount) return;
  mount.innerHTML = `
    <div class="vault-view-top">
      <button class="vault-back" type="button" onclick="closeVault()" aria-label="Back to clips"><span class="iconify" data-icon="lucide:arrow-left"></span></button>
      <h2>Vault</h2>
    </div>
    <div class="vault-hero"><span class="vault-hero-icon"><span class="iconify" data-icon="lucide:key-round"></span></span><strong>Protected by Cliplo</strong></div>
    <div class="vault-collection-card"><span class="vault-collection-art"><span class="iconify" data-icon="lucide:users-round"></span></span><div><strong>Google accounts</strong><small>7 protected clips</small></div><button type="button" onclick="revealVaultClip(this)">View</button></div>
    <div class="vault-content">
      <article class="vault-clip"><span class="vault-clip-icon"><span class="iconify" data-icon="lucide:credit-card"></span></span><div class="vault-clip-copy"><strong class="vault-redacted" data-masked="Card details" data-value="XXXX 4821">Card details</strong><small>Saved 2 days ago</small></div><button class="vault-view-button" type="button" onclick="revealVaultClip(this)">View</button></article>
      <article class="vault-clip"><span class="vault-clip-icon"><span class="iconify" data-icon="lucide:map-pin"></span></span><div class="vault-clip-copy"><strong class="vault-redacted" data-masked="Home address" data-value="12 Garden Avenue">Home address</strong><small>Saved 1 week ago</small></div><button class="vault-view-button" type="button" onclick="revealVaultClip(this)">View</button></article>
      <article class="vault-clip"><span class="vault-clip-icon"><span class="iconify" data-icon="lucide:key-round"></span></span><div class="vault-clip-copy"><strong class="vault-redacted" data-masked="............" data-value="AB12 - 4F9X">............</strong><small>Saved yesterday</small></div><button class="vault-view-button" type="button" onclick="revealVaultClip(this)">View</button></article>
    </div>`;
})();

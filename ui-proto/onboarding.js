/* Cliplo first-run experience: promise first, setup after intent. */
(function () {
  var root = document.documentElement;
  var app = document.querySelector('.app');
  if (!app) return;

  var onboarding = document.createElement('section');
  onboarding.className = 'onboarding';
  onboarding.id = 'onboarding';
  onboarding.setAttribute('aria-label', 'Cliplo onboarding');
  onboarding.innerHTML = `
    <div class="onboarding-top">
      <div class="onboarding-brand"><span class="onboarding-mark"><span class="iconify" data-icon="lucide:bookmark"></span></span><span>Cliplo</span></div>
      <button class="onboarding-skip" type="button" data-onboard="skip">Skip</button>
    </div>
    <div class="onboarding-screen active" data-step="0">
      <div class="onboarding-content">
        <div class="onboarding-kicker">Your reusable memory</div><h1>Copy once.<br>Use anytime.</h1>
        <p class="onboarding-copy">That address you need again later? Cliplo keeps it close, then brings it back right when it matters.</p>
        <div class="onboarding-scene"><img src="cliplo-assets/onboarding-moments/copy-return.png" alt="An address kept in Cliplo and ready to return"></div>
      </div><div class="onboarding-actions"><button class="onboarding-primary" data-onboard="next">Continue</button></div>
    </div>
    <div class="onboarding-screen" data-step="1">
      <div class="onboarding-content">
        <div class="onboarding-kicker">You already do this</div><h1>Worth keeping<br>turns up everywhere.</h1>
        <p class="onboarding-copy">A message. A link. A place you meant to remember. Cliplo is made for the small things you end up hunting for.</p>
        <div class="onboarding-scene"><div class="onboarding-recognition" aria-label="Examples of things Cliplo can remember"><div class="recognition-item"><img src="cliplo-assets/collection-art/messages.png" alt=""><span>A message</span></div><div class="recognition-item"><img src="cliplo-assets/collection-art/links.png" alt=""><span>A useful link</span></div><div class="recognition-item"><img src="cliplo-assets/collection-art/people-places.png" alt=""><span>A place</span></div></div></div>
      </div><div class="onboarding-actions"><button class="onboarding-primary" data-onboard="next">Show me</button></div>
    </div>
    <div class="onboarding-screen" data-step="2">
      <div class="onboarding-content">
        <div class="onboarding-kicker">It builds with you</div><h1>The more you use it,<br>the more it helps.</h1>
        <p class="onboarding-copy">Your useful things gather into a calm personal memory. Similar copies stay clean, so you can find the one that matters.</p>
        <div class="onboarding-scene"><img src="cliplo-assets/onboarding-moments/memory-forms.png" alt="A small collection of remembered messages, links, and images"></div>
      </div><div class="onboarding-actions"><button class="onboarding-primary" data-onboard="next">Continue</button></div>
    </div>
    <div class="onboarding-screen" data-step="3">
      <div class="onboarding-content">
        <div class="onboarding-kicker">When you need it</div><h1>Right there.<br>One tap away.</h1>
        <p class="onboarding-copy">Open Cliplo where you are, find what you saved, and paste it without losing your place.</p>
        <div class="onboarding-scene"><img src="cliplo-assets/onboarding-moments/retrieve.png" alt="A Cliplo retrieval surface with a saved card ready to paste"></div>
      </div><div class="onboarding-actions"><button class="onboarding-primary" data-onboard="next">Continue</button></div>
    </div>
    <div class="onboarding-screen" data-step="4">
      <div class="onboarding-content">
        <div class="onboarding-kicker">A small promise</div><h1>Start building<br>your Cliplo.</h1>
        <p class="onboarding-copy">You should not have to find the same thing twice.</p>
        <div class="onboarding-scene"><div class="onboarding-note"><div class="onboarding-note-mark"><span class="iconify" data-icon="lucide:bookmark-check"></span></div><p>“We kept copying the same addresses, links and messages, then hunting for them again. So we made Cliplo remember them.”</p><small>Made for useful things.</small></div></div>
      </div><div class="onboarding-actions"><button class="onboarding-primary" data-onboard="start">Start clipping</button></div>
    </div>
    <div class="onboarding-screen" data-step="5" data-setup="true">
      <div class="onboarding-content">
        <div class="onboarding-kicker">One small setup</div><h1>Let Cliplo notice<br>what you choose to copy.</h1>
        <p class="onboarding-copy">Android may limit background capture. We will always be clear about what Cliplo can do, and you can use manual paste or sharing anytime.</p>
        <div class="onboarding-scene"><div class="onboarding-setup-card"><div class="setup-card-icon"><span class="iconify" data-icon="lucide:clipboard-check"></span></div><strong>Enable capture while Cliplo is available</strong><p>We will ask Android only after you choose to continue.</p><div class="setup-path"><span class="setup-dot"><span class="iconify" data-icon="lucide:copy"></span></span><i></i><span class="setup-dot"><span class="iconify" data-icon="lucide:bookmark-plus"></span></span><i></i><span>Clipped</span></div></div></div>
      </div><div class="onboarding-actions"><div class="onboarding-setup-status" aria-live="polite"></div><button class="onboarding-primary" data-onboard="enable-capture">Enable capture</button><button class="onboarding-secondary" data-onboard="skip-capture">I’ll use manual paste</button></div>
    </div>
    <div class="onboarding-screen" data-step="6" data-setup="true">
      <div class="onboarding-content">
        <div class="onboarding-kicker">Bring it back faster</div><h1>Find. Tap.<br>Paste.</h1>
        <p class="onboarding-copy">Set up Cliplo’s retrieval surface now, or keep exploring and do it when you need it.</p>
        <div class="onboarding-scene"><img src="cliplo-assets/onboarding-moments/retrieve.png" alt="Cliplo retrieval surface"></div>
      </div><div class="onboarding-actions"><button class="onboarding-primary" data-onboard="finish">Open my Cliplo</button><button class="onboarding-secondary" data-onboard="finish">Set this up later</button></div>
    </div>
    <div class="onboarding-progress" aria-label="Onboarding progress"><i class="active current"></i><i></i><i></i><i></i><i></i></div>`;

  app.insertBefore(onboarding, app.firstChild);
  var screens = onboarding.querySelectorAll('.onboarding-screen');
  var progress = onboarding.querySelectorAll('.onboarding-progress i');
  var step = 0;
  var storySteps = 5;

  function finish() {
    try { localStorage.setItem('cliplo-onboarding-complete', '1'); } catch (e) {}
    document.body.classList.remove('onboarding-active');
    onboarding.remove();
    var home = document.getElementById('screen-home');
    if (home) home.classList.add('active');
  }
  function show(next) {
    step = Math.max(0, Math.min(screens.length - 1, next));
    screens.forEach(function (screen, index) { screen.classList.toggle('active', index === step); });
    var progressStep = Math.min(step, storySteps - 1);
    progress.forEach(function (bar, index) { bar.classList.toggle('active', index <= progressStep); bar.classList.toggle('current', index === progressStep); });
    onboarding.querySelector('[data-onboard="skip"]').textContent = step >= storySteps ? 'Not now' : 'Skip';
  }
  onboarding.addEventListener('click', function (event) {
    var action = event.target.closest('[data-onboard]');
    if (!action) return;
    var type = action.dataset.onboard;
    if (type === 'next') show(step + 1);
    if (type === 'start') show(5);
    if (type === 'enable-capture') {
      var status = onboarding.querySelector('.onboarding-screen.active .onboarding-setup-status');
      if (status) status.textContent = 'Capture is ready while Cliplo is open.';
      action.textContent = 'Continue';
      action.dataset.onboard = 'next';
    }
    if (type === 'skip-capture') show(6);
    if (type === 'finish' || type === 'skip') finish();
  });
  var completed = false;
  try { completed = localStorage.getItem('cliplo-onboarding-complete') === '1'; } catch (e) {}
  if (completed && new URLSearchParams(location.search).get('onboarding') !== '1') onboarding.remove();
  else document.body.classList.add('onboarding-active');
  if (root.dataset.theme === 'light') document.body.classList.add('onboarding-light');
})();

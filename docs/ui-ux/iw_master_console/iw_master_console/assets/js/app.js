(function(){
  // Active nav highlight based on path
  const path = location.pathname.replace(/\/+$/, '');
  document.querySelectorAll('.nav a').forEach(a=>{
    const href = a.getAttribute('href');
    if(!href) return;
    const norm = href.replace(/\/+$/, '');
    if(path.endsWith(norm) || (path.endsWith('/index.html') && norm.endsWith('index.html'))){
      a.classList.add('active');
    }
  });

  // Tiny sparkline generator (SVG polyline)
  document.querySelectorAll('[data-spark]').forEach(el=>{
    const pts = (el.getAttribute('data-spark')||'').split(',').map(x=>parseFloat(x.trim())).filter(x=>!isNaN(x));
    if(!pts.length) return;
    const w = el.clientWidth || 240, h = el.clientHeight || 42, pad=4;
    const min = Math.min(...pts), max = Math.max(...pts);
    const scaleX = (w - pad*2) / (pts.length-1 || 1);
    const scaleY = (h - pad*2) / ((max-min) || 1);
    const points = pts.map((v,i)=>{
      const x = pad + i*scaleX;
      const y = h - pad - (v-min)*scaleY;
      return `${x.toFixed(1)},${y.toFixed(1)}`;
    }).join(' ');
    el.innerHTML = `
      <svg viewBox="0 0 ${w} ${h}" width="${w}" height="${h}" aria-hidden="true">
        <polyline points="${points}" fill="none" stroke="currentColor" stroke-width="2" opacity=".9"/>
      </svg>`;
  });

  // Table filter (optional)
  const q = document.querySelector('[data-table-filter]');
  if(q){
    q.addEventListener('input', ()=>{
      const term = q.value.toLowerCase().trim();
      document.querySelectorAll('table[data-filterable] tbody tr').forEach(tr=>{
        const txt = tr.innerText.toLowerCase();
        tr.style.display = txt.includes(term) ? '' : 'none';
      });
    });
  }
})();
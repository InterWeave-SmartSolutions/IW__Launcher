(function(){
  const path = location.pathname.replace(/\/+$/, '');
  document.querySelectorAll('.nav a').forEach(a=>{
    const href = a.getAttribute('href');
    if(!href) return;
    const norm = href.replace(/\/+$/, '');
    if(path.endsWith(norm) || (path.endsWith('/index.html') && norm.endsWith('index.html'))){
      a.classList.add('active');
    }
  });

  // Resource cards search
  const rs = document.querySelector('[data-resource-search]');
  if(rs){
    rs.addEventListener('input', ()=>{
      const term = rs.value.toLowerCase().trim();
      document.querySelectorAll('[data-resource-card]').forEach(card=>{
        const txt = card.innerText.toLowerCase();
        card.style.display = txt.includes(term) ? '' : 'none';
      });
    });
  }
})();
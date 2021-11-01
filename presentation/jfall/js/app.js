(function(){
  let slideshow = remark.create({
    ratio: '16:9',
    sourceUrl: 'presentation.md'
  });
 
  // Setup MathJax
  MathJax.Hub.Config({
    tex2jax: {
      skipTags: ['script', 'noscript', 'style', 'textarea', 'pre']
    }
  });
  MathJax.Hub.Configured();
})();

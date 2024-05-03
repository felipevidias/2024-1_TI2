const API_KEY = "AIzaSyC4HbuGNKl-ImE9MGxRPub9ttCFkqQSnlY";
const PLAYLIST_ID = "PL01y0oZQjUTz1lghLi8hWYzm-mOlmoDKL";

function loadVideosFromYouTube() {
  const playlist_area = document.querySelector(".playlist");
  
  // Função para fazer a solicitação da próxima página de resultados
  function fetchPlaylistPage(pageToken) {
    let url = `https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=${PLAYLIST_ID}&key=${API_KEY}&maxResults=50`;
    if (pageToken) {
      url += `&pageToken=${pageToken}`;
    }
    return fetch(url)
      .then(response => response.json())
      .then(data => {
        // Iterando sobre os itens da página atual
        data.items.forEach((item, index) => {
          const videoId = item.snippet.resourceId.videoId;
          const title = item.snippet.title;
          const thumbnail = item.snippet.thumbnails.default.url;
          
          // Criando um elemento de vídeo para cada item
          const div = document.createElement("div");
          div.classList.add("playlist-video");
          div.innerHTML = `
            <img src="${thumbnail}" alt="${title}">
            <label class="playlist-video-info">${title}</label>
          `;
          
          // Adicionando um evento de clique para trocar o vídeo principal
          div.addEventListener("click", () => {
            setVideo(videoId, title);
            updateActiveVideo(div);
          });
          
          playlist_area.appendChild(div);
          
          // Se for o primeiro vídeo, defini-lo como ativo
          if (index === 0 && !pageToken) {
            setVideo(videoId, title);
            updateActiveVideo(div);
          }
        });

        // Verificando se há mais páginas de resultados
        if (data.nextPageToken) {
          // Se houver mais páginas, fazer outra solicitação para a próxima página
          fetchPlaylistPage(data.nextPageToken);
        }
      })
      .catch(error => {
        console.error("Erro ao carregar vídeos:", error);
      });
  }

  // Iniciando a solicitação para a primeira página
  fetchPlaylistPage(null);
}

function setVideo(videoId, title) {
  const video_main = document.querySelector("#video-container");
  video_main.innerHTML = `
    <iframe width="560" height="315" src="https://www.youtube.com/embed/${videoId}" frameborder="0" allowfullscreen></iframe>
    <label>${title}</label>
  `;
}

function updateActiveVideo(selectedVideo) {
  const playlist_videos = document.querySelectorAll(".playlist-video");
  playlist_videos.forEach(video => {
    video.classList.remove("active");
  });
  selectedVideo.classList.add("active");
}

loadVideosFromYouTube();

document.querySelector('#adicionar').addEventListener('click', function() {
  window.location.href = 'indexcadastrovideoaulas.html';
});
const mostraDataRodape = () => {
    const dataRodape = document.getElementById("dataRodape");
    const data = new Date();
    dataRodape.innerHTML = "Data: " + data.toLocaleString();
    setTimeout(mostraDataRodape, 1);
};

$("#imagem").change(function (evt){
   const imagemUpload = evt.target.files[0]
   
   alterarTamanhoImagem(imagemUpload, function(resizedFile){
   const imagem = new FileReader();

    imagem.onload = function (){
      $("#imgPublicacao").attr("src",imagem.result);
      };
   imagem.readAsDataURL(resizedFile);
//   imagem.readAsDataURL(evt.target.files[0]);
});
});



// função para alterar o tamanho da imagem ao fazer o upload
function alterarTamanhoImagem(file, callback) {
    const maxWidth = 300;
    const maxHeight = 300;    
    const img = new Image();
    const reader = new FileReader();
    reader.onload = function(e) {
        img.src = e.target.result;
        img.onload = function() {
            const canvas = document.createElement('canvas');
            let width = img.width;
            let height = img.height;



            if (width > height) {
                if (width > maxWidth) {
                    height *= maxWidth / width;
                    width = maxWidth;
                }
            } else {
                if (height > maxHeight) {
                    width *= maxHeight / height;
                    height = maxHeight;
                }
            }

            canvas.width = width;
            canvas.height = height;

            const contex = canvas.getContext('2d');
            contex.drawImage(img, 0, 0, width, height);

            canvas.toBlob(function(blob) {
                const tirarEspacaoArquivo = new File([blob], file.name.replace(/\s+/g, '_'), { type: file.type });
                callback(tirarEspacaoArquivo);
            }, file.type);
        };
    };

    reader.readAsDataURL(file);
}
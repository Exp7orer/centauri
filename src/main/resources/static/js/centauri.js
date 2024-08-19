const mostraDataRodape = () => {
    const dataRodape = document.getElementById("dataRodape");
    const data = new Date();
    dataRodape.innerHTML = "Data: " + data.toLocaleString();
    setTimeout(mostraDataRodape, 1);
};

const $publicacao = $('#texto').summernote({
    lang: 'pt-BR',
    toolbar: [
        ['style', ['style']],
        ['font', ['bold', 'underline', 'clear']],
        ['fontname', ['fontname']],
        ['color', ['color']],
        ['para', ['ul', 'ol', 'paragraph']],
        ['view', ['fullscreen', 'codeview', 'help']],
    ],
    placeholder: 'Crie sua publicacão',
    tabsize: 4,
    height: 400,
});

$("#imagem").change(function (evt){
   const imagem = new FileReader();

    imagem.onload = function (){
      $("#imgPublicacao").attr("src",imagem.result);
   }
   imagem.readAsDataURL(evt.target.files[0]);
});


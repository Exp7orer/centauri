$(document).ready(function() {
    $('.btn-like').on('click', function() {
        var postId= $(this).data('id');
        $.ajax({
            url: '/' + postId + '/like',
            type: 'POST',
            success: function(response) {
//                console.log('Like deu certo');
                
                // Desativa o botão de dislike, trem doido, pulo do gato
                $('.btn-dislike[data-id="' + postId + '"]').addClass('btn-disabled');
            },
            error: function(xhr, status, error) {
//                console.error('Erro ao registrar o like:', error);
//                console.error('Resposta do servidor:', xhr.responseText);
            }
        });
    });






    $('.btn-dislike').on('click', function() {
        var postId = $(this).data('id');

        $.ajax({
            url: '/' + postId + '/dislike',
            type: 'POST',
            success: function(response) {
//                console.log('Dislike deu certo');
                
                // Desativa o btn like é o pulo do gato
                $('.btn-like[data-id="' + postId + '"]').addClass('btn-disabled');
            },
            error: function(xhr, status, error) {
//                console.error('Erro ao registrar o dislike:', error);
//                console.error('Resposta do servidor:', xhr.responseText);
            }
    });
 });
});
$(document).ready(function() {
    $('.btn-like').on('click', function() {
        var postId = $(this).data('id');
        var botaoLike = $(this); 

        $.ajax({
            url: '/like/' + postId,
            type: 'POST',
            success: function(response) {  
                botaoLike.addClass('btn-selecionado').prop('disabled', true);// desativar e destacar para evitar spam  
                $('.btn-dislike[data-id="' + postId + '"]').addClass('btn-disabled').prop('disabled', true);// desativar o botão de dislike
            },
            error: function(xhr, status, error) {
                console.error('Erro like:', error);
            }
        });
    });

    $('.btn-dislike').on('click', function() {
        var postId = $(this).data('id');
        var botaoDislike = $(this);
        $.ajax({
            url: '/' + postId + '/dislike',
            type: 'POST',
            success: function(response) {             
                botaoDislike.addClass('btn-selecionado').prop('disabled', true); // desativar e destacar o botao de dislike               
                $('.btn-like[data-id="' + postId + '"]').addClass('btn-disabled').prop('disabled', true); // desativar o botão de like
            },
            error: function(xhr, status, error) {
                console.error('Erro ', error);
            }
        });
    });
});
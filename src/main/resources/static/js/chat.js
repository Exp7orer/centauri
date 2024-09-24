let currentContact = null;
let currentAdress = null;
const messagesByContact = {};

function selectContact(contactName, contactAdress) {
    currentContact = contactName;
    currentAdress = contactAdress;
    document.getElementById('contact-name').textContent = contactName;
    document.getElementById('message-input').disabled = false;
    document.querySelector('.message-send-btn').disabled = false;

    const chatMessages = document.getElementById("chat-messages");
    chatMessages.innerHTML = "";

    if (messagesByContact[currentContact]) {
        messagesByContact[currentContact].forEach(message => {
            const messageElement = document.createElement("div");
            messageElement.classList.add("chat-message", "message-sender");
            messageElement.textContent = message;
            chatMessages.appendChild(messageElement);
        });
    }

    chatMessages.scrollTop = chatMessages.scrollHeight;
}

function sendMessage() {
    const messageInput = document.getElementById("message-input");
    const messageText = messageInput.value;

    if (messageText.trim() !== "") {
        const chatMessages = document.getElementById("chat-messages");

        const messageElement = document.createElement("div");
        messageElement.classList.add("chat-message", "message-sender");
        messageElement.textContent = messageText;

        chatMessages.appendChild(messageElement);
        chatMessages.scrollTop = chatMessages.scrollHeight;


        if (!messagesByContact[currentContact]) {
            messagesByContact[currentContact] = [];
        }
        messagesByContact[currentContact].push(messageText);

        messageInput.value = "";
    }

    // Envio da mensagem via AJAX
    const remetente = {
        nome: "Seu Nome", // Substitua pelo nome do remetente
        endereco: "seu_endereco@example.com" // Substitua pelo endereço do remetente
    };
    const destinatario = {
        nome: currentContact,
        endereco: "endereco_do_destinatario@example.com" // Substitua pelo endereço do destinatário
    };

    // Dados a serem enviados
    const payload = {
        remetente: remetente,
        destinatario: destinatario,
        mensagem: messageText
    };

    // Envio da requisição AJAX
    fetch('/chat/enviar', { // Certifique-se de que a URL está correta
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao enviar a mensagem');
            }
            return response.json();
        })
        .then(data => {
            console.log('Mensagem enviada com sucesso:', data);
        })
        .catch((error) => {
            console.error('Erro:', error);
        });

    messageInput.value = ""; // Limpa o campo de entrada

}

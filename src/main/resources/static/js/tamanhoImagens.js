document.getElementById('imagem').addEventListener('change', function(event) {
    const fileInput = event.target;
    const file = fileInput.files[0];
    if (!file) return;

    const img = new Image();
    const reader = new FileReader();

    reader.onload = function(e) {
        img.src = e.target.result;
    };
    reader.readAsDataURL(file);

    img.onload = function() {
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        
        const targetWidth = 300;
        const targetHeight = 300;
        
        canvas.width = targetWidth;
        canvas.height = targetHeight;

        const xOffset = Math.max(0, (img.width - targetWidth) / 2);
        const yOffset = Math.max(0, (img.height - targetHeight) / 2);

        ctx.drawImage(img, xOffset, yOffset, targetWidth, targetHeight, 0, 0, targetWidth, targetHeight);

        canvas.toBlob(function(blob) {
            const dataTransfer = new DataTransfer();
            dataTransfer.items.add(new File([blob], file.name, { type: file.type }));
            fileInput.files = dataTransfer.files;
        }, file.type);
    };
});
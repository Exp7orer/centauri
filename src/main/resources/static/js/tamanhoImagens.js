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
                
                const SIZE = 300; // alterar o tamanho da imagem para 300x300
                let width = img.width;
                let height = img.height;

                if (width > height) {
                    height = (SIZE / width) * height;
                    width = SIZE;
                } else {
                    width = (SIZE / height) * width;
                    height = SIZE;
                }

                canvas.width = SIZE;
                canvas.height = SIZE;
                ctx.drawImage(img, 0, 0, width, height);

                canvas.toBlob(function(blob) {
                    const dataTransfer = new DataTransfer();
                    dataTransfer.items.add(new File([blob], file.name, { type: file.type }));
                    fileInput.files = dataTransfer.files;
                }, file.type);
            };
        });
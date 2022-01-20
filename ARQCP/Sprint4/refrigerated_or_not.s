.section .data

	.global containers_in_ship
	.global lines
	.equ DATA_SIZE, 312 # total size
	.equ X_OFFSET, 76
	.equ Y_OFFSET, 80
	.equ Z_OFFSET, 84
	.equ REFRIGERATED_OFFSET, 100
	
.section .text

	.global refrigerated_or_not

refrigerated_or_not:

	movq containers_in_ship(%rip), %rcx 
	movl lines(%rip), %r8d               # total de contentores
	movl $0, %r9d                        # contador de contentores
	movl $0, %eax
	
	loop:
	cmpl %r8d, %r9d     	     # compara o contador de contentores com o total de contentores
	je end

	movl X_OFFSET(%rcx), %eax   
	cmpl %eax, %edi    			 # compara a posição x do contentor com a posiçao x passada por parâmetro
	jne inc

	movl Y_OFFSET(%rcx), %eax
	cmpl %eax, %esi				 # compara a posição y do contentor com a posiçao y passada por parâmetro
	jne inc

	movl Z_OFFSET(%rcx), %eax
	cmpl %eax, %edx				 # compara a posição z do contentor com a posiçao z passada por parâmetro
	jne inc

    cmpb $'R', REFRIGERATED_OFFSET(%rcx) 	# verifica se o contentor é refrigerado - R
	je refrigerated 

	movl $0, %eax				 # caso o contentor corresponda às posições pretendidas mas nao seja refrigerado é retornado o valor 0
	jmp end

	inc:
	addl $1, %r9d				 # adiciona 1 ao contador de posições
	movl $-1, %eax               # se o contentor nao existir na posiçao pretendida retorna -1
	addq $DATA_SIZE, %rcx        # passa para o próximo contentor
	jmp loop

refrigerated:

	movl $1, %eax				 # retorno de contentor refrigerado

end:

   ret


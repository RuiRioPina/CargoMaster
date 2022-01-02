.section .data

.section .text

.global free_occupied_slots

free_occupied_slots:
incq %rdi          
movl $0, %edx      # contador de posições
movq $0, %rcx      # nr slots livres
movq $0, %rax      # nr slots ocupados

loop:

cmpl %esi, %edx    # verifica se o contador de posições atingiu o máximo SIZE 
je end

cmpb $0, (%rdi)   # compara a posição com 0 (null), para verificar se o slot está livre
je free

addq $1, %rax      # adiciona 1 ao nr de slots ocupados

increment:
addq $8, %rdi      # passa para a próxima posição do array
addl $1, %edx      # adiciona 1 ao contador de posições
jmp loop

free: 
addq $1, %rcx      # adiciona 1 ao nr de slots livres
jmp increment

end:
shl $32, %rcx      # shift à esquerda do rcx de forma a ficar nos 4 bytes mais signficativos do registo
addq %rcx, %rax    # adiciona o registo dos livres aos ocupados

ret

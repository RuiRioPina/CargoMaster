.section .data
.global ptr_pos_init
.global ptr_pos
.global ptr_vetorpos
.global ptr_inicial
.global  ptr_array_size

.section .text

.global is_array_occupied
.global num_of_occupied_positions
.global is_array_occupied_ptr


is_array_occupied_ptr:
movq ptr_pos_init(%rip), %rax
cmpq $0 ,%rax
je null1
jmp not_null1
null1:
movl $0,%eax
jmp fim
not_null1:
movl $1,%eax
jmp fim1
fim1:
ret



is_array_occupied:
cmpq $0 ,%rax
je null
jmp not_null
null:
movl $0,%eax
jmp fim
not_null:
movl $1,%eax
jmp fim
fim:
ret

num_of_occupied_positions:
movl $0,%edx
movq ptr_inicial(%rip), %rsi
movl ptr_array_size(%rip) ,%ecx
ciclo:
movq (%rsi),%rax
pushq %rsi
pushq %rcx
call is_array_occupied
popq %rcx
popq 	%rsi
addl %eax,%edx
addq $8 ,%rsi
loop ciclo
fin:
movl %edx,%eax
   ret







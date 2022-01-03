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

# function for a single position
is_array_occupied_ptr:
movq ptr_pos_init(%rip), %rax
cmpq $0 ,%rax #sees if the content of the adress is empty and jumps to the corresponding value
je null1
jmp not_null1
null1:
movl $0,%eax
jmp fim
not_null1:
movl $1,%eax
jmp fim1
fim1: # returns 1 or 0 depending on the content of the adress pointed by the pointer
ret


# auxiliary function to see if a pointer in the array pointer is occupied or not (similar to the above)
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

num_of_occupied_positions: #traverses an array with the pointers to adresses to see how many occupied positions it has
movl $0,%edx
movq ptr_inicial(%rip), %rsi
movl ptr_array_size(%rip) ,%ecx 
ciclo:# cycle that uses the auxiliary function for each adress and continuosuly adds 1 or 0 depending on if its occupied or not.
movq (%rsi),%rax
pushq %rcx
pushq %rsi
call is_array_occupied
popq %rsi
popq %rcx
addl %eax,%edx
addq $8 ,%rsi
loop ciclo
fin:
movl %edx,%eax
   ret







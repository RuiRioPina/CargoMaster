#Makefile
main: fill_array.o free_occupied_slots.o is_array_occupied.o main.o
	gcc -g -Wall main.o fill_array.o free_occupied_slots.o is_array_occupied.o -o main

main.o: main.c
	gcc -g -Wall -c main.c -o main.o

fill_array.o: fill_array.c
	gcc -g -Wall -c fill_array.c -o fill_array.o

free_occupied_slots.o: free_occupied_slots.s
	gcc -g -Wall -c free_occupied_slots.s -o free_occupied_slots.o
	
is_array_occupied.o: is_array_occupied.s
	gcc -g -Wall -c is_array_occupied.s -o is_array_occupied.o

clean:
	rm -f *.o

run: main
	./main

#Makefile
main: main.o fill_array.o
	gcc -g -Wall main.o fill_array.o -o main

fill_array.o: fill_array.c
	gcc -g -Wall -c fill_array.c -o fill_array.o

main.o: main.c
	gcc -g -Wall -c main.c -o main.o

clean:
	rm -f *.o

run: main
	./main

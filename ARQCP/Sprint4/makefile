#Makefile
main: refrigerated_or_not.o main.o
	gcc -g -Wall main.o refrigerated_or_not.o  -o main

main.o: main.c
	gcc -g -Wall -c main.c -o main.o

refrigerated_or_not.o: refrigerated_or_not.s
	gcc -g -Wall -c refrigerated_or_not.s -o refrigerated_or_not.o
	
clean:
	rm -f *.o

run: main
	./main

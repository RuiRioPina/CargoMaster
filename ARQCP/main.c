#include <stdio.h>
#include "fill_array.h"

char * array[20][20][20] = {0};
int SIZE;
char* ptr_pos_init;
char *ptr_pos[5];
char** ptr_inicial;
int ptr_array_size;
int main ( void ) {
	int ocu;
	int num_of_occupied_array_positions;
   fill_array(array);
   
		/**Prints for debugging US313 | To be deleted later on
		for(int i = 0; i < 20;i++) {
		for(int j = 0; j < 20;j++) {
		for(int k = 0; k < 20;k++) {
			printf("array[%d][%d][%d] = %s\n",i,j,k,array[i][j][k]);
		}	
		}	
		}
		printf("\n");
		**/
		
		printf("array[%d][%d][%d] = %s\n",1,1,1,array[1][1][1]);
		printf("array[%d][%d][%d] = %s\n",3,3,1,array[3][3][1]);
		printf("array[%d][%d][%d] = %s\n",4,4,4,array[4][4][4]);
		printf("array[%d][%d][%d] = %s\n",7,5,1,array[7][5][1]);
		printf("array[%d][%d][%d] = %s\n",8,5,8,array[8][5][8]);
		printf("array[%d][%d][%d] = %s\n",12,12,12,array[12][12][12]);
		
		//US314
		SIZE = sizeof(array)/sizeof(array[0][0][0]);
		
		char * position;
		position = (char *) array;
	    position++;
		long result = free_occupied_slots(position, SIZE);

		int *ptr;
		ptr = (int *) & result;
	    printf("\nNumber of occupied slots: %d\n", *ptr);
	    ptr++;
		printf("Number of free slots: %d\n", *ptr);
		int x=7;
		int y=5;
		int z=1;
		ptr_pos_init= array[x][y][z];
		ocu=is_array_occupied_ptr();
		printf("Is the 3d matrix occupied in the position[%d][%d][%d]? :%d\n",x,y,z ,ocu);
		ptr_array_size=5;

		ptr_pos[0]= array[4][4][4];

		ptr_pos[1]= array[3][4][4];

		ptr_pos[2]= array[7][5][8];

		ptr_pos[3]= array[3][3][1];

		ptr_pos[4]= array[1][1][1];

		ptr_inicial=ptr_pos;

		num_of_occupied_array_positions=num_of_occupied_positions();

		printf ("The number of postions that are occupied in the list of positions is : %d\n", num_of_occupied_array_positions);
			
   return 0;
}


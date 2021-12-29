#include <stdio.h>

#include "fill_array.h"
char *array[20][20][20] = {0};
int main ( void )
{

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


   return 0;
}


#include <stdio.h>
#include <string.h>
#include <stdlib.h>
char *array[20][20][20] = {0};

void fill_array() {
   static const char filename[] = "ContainersInManifest.csv";  
   FILE *file = fopen ( filename, "r" );  
   if ( file != NULL ) 
   {    
     char line [ 128 ]; 
     while ( fgets ( line, sizeof line, file ) != NULL )   
	    {
	    	char *token[20];
			int count = 0;
 			token[0] = strtok (line,",");
  			while (token[count] != NULL)
  			{
				count++;
				token[count] = strtok (NULL, ",");


			}
				int x = 0;
				int y = 0;
				int z = 0;
				x = atoi(token[6]);
				y = atoi(token[7]);
				z = atoi(token[8]);
			
				array[x][y][z] = strdup(token[3]);
				
				
		}
		
		
		
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
		/**Just to test in each members pc, it is importing the containers**/
		printf("array[%d][%d][%d] = %s\n",1,1,1,array[1][1][1]);
		printf("array[%d][%d][%d] = %s\n",3,3,1,array[3][3][1]);
		printf("array[%d][%d][%d] = %s\n",4,4,4,array[4][4][4]);
		printf("array[%d][%d][%d] = %s\n",7,5,1,array[7][5][1]);
		printf("array[%d][%d][%d] = %s\n",8,5,8,array[8][5][8]);
		printf("array[%d][%d][%d] = %s\n",12,12,12,array[12][12][12]);



}


		fclose ( file );   

}
 
 

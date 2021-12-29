#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void fill_array(char *array[20][20][20]) {
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
		


}


		fclose ( file );   

}
 
 

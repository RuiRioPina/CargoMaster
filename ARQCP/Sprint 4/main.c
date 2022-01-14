	#include <stdio.h>

#include <string.h>

#include <stdlib.h>

#include "fill_array.h"

char * array[20][20][20] = {0};

#define n 3

struct container {


    char typeOfContainer[40]; /* 40 bytes */

    char load[40]; /* 40 bytes */

    char arrivalPort[40]; /* 40 bytes */

    char arrivalDate[40]; /* 40 bytes */

    char  departureDate[40]; /* 40 bytes */

    int mmsi; /* 4 bytes */

    double length; /* 4 bytes */

    double width; /* 4 bytes */
    
    double thicknessExternal; /* 4 bytes */
    
    double thicknessMiddle; /* 4 bytes */

    double thicknessInternal; /* 4 bytes */
    
    double kExternal; /* 4 bytes */
    
    double kMiddle; /* 4 bytes */
    
    double kInternal; /* 4 bytes */
    
    char x; /* 1 byte */

    char y; /* 1 byte */

    char z; /* 1 byte */
    
    char xMax; /* 1 bytes */

    char yMax; /* 1 byte */

    char zMax; /* 1 byte */

    char numberContainer[11]; /* 11 bytes */


   

};

struct container ***containers_in_ship;





void fill_array_of_structs() {
	
	 
	
	
   char first_line = 0;

   static const char filename[] = "ContainersInManifest.csv";  

   FILE *file = fopen ( filename, "r" );  

   if ( file != NULL ) {    

     char line [ 512 ]; 

     while ( fgets ( line, sizeof line, file ) != NULL ) {
				
	    	char *token[40];
			int count = 0;
 			token[0] = strtok (line,";");

  			while (token[count] != NULL) {
				count++;

				token[count] = strtok (NULL, ";");
			}


			/*pegar no array de structs e meter na posicao do x, y ,z pegando pelo atoi e depois é que se faz isto tudo*/

				int x = atoi(token[6]);

				int y = atoi(token[7]);

				int z = atoi(token[8]);
				if(first_line != 0) {
				if(first_line == 1) {
					int xMax, yMax, zMax;
					int i,j;
					xMax=atoi(token[12]);
					yMax=atoi(token[13]);
					zMax=atoi(token[14]);

					containers_in_ship = (struct container ***)malloc(xMax*sizeof(struct container**));

					for (i = 0; i< xMax; i++) {

					containers_in_ship[i] = (struct container **) malloc(yMax*sizeof(struct container *));

					for (j = 0; j < yMax; j++) {

					containers_in_ship[i][j] = (struct container *)malloc(zMax*sizeof(struct container));
					}

					}
	
				}

				containers_in_ship[x][y][z].mmsi = atoi(token[0]);

				containers_in_ship[x][y][z].length = atoi(token[1]);
			

				containers_in_ship[x][y][z].width = atoi(token[2]);

				strcpy(containers_in_ship[x][y][z].numberContainer, strdup(token[3]));

				strcpy(containers_in_ship[x][y][z].typeOfContainer, strdup(token[4]));

				strcpy(containers_in_ship[x][y][z].load, strdup(token[5]));

				

				containers_in_ship[x][y][z].x = x;

				containers_in_ship[x][y][z].y = y;

				containers_in_ship[x][y][z].z = z;

				strcpy(containers_in_ship[x][y][z].arrivalPort, strdup(token[9]));

				strcpy(containers_in_ship[x][y][z].arrivalDate, strdup(token[10]));

				strcpy(containers_in_ship[x][y][z].departureDate, strdup(token[11]));

				containers_in_ship[x][y][z].xMax = atoi(token[12]);

				containers_in_ship[x][y][z].yMax = atoi(token[13]);

				containers_in_ship[x][y][z].zMax = atoi(token[14]);
				
				containers_in_ship[x][y][z].zMax = atoi(token[14]);
				
				containers_in_ship[x][y][z].thicknessExternal = atof(token[15]);
				
				containers_in_ship[x][y][z].thicknessMiddle = atof(token[16]);

				containers_in_ship[x][y][z].thicknessInternal = atof(token[17]);
				
				containers_in_ship[x][y][z].kExternal = atof(token[18]);

				containers_in_ship[x][y][z].kMiddle = atof(token[19]);

				containers_in_ship[x][y][z].kInternal = atof(token[20]);




		}
							first_line++;

		}

}


		fclose ( file );   
}

int main ( void ) {

	





	

		//US314

	

		fill_array_of_structs(containers_in_ship);

		

		printf("numberContainer %s\n", containers_in_ship[1][1][1].numberContainer);

		printf("typeOfContainer %s\n", containers_in_ship[1][1][1].typeOfContainer);

		printf("load %s\n", containers_in_ship[1][1][1].load);

		printf("arrivalPort %s\n", containers_in_ship[1][1][1].arrivalPort);

		printf("arrivalDate %s\n", containers_in_ship[1][1][1].arrivalDate);

	

		printf("departureDate %s\n", containers_in_ship[1][1][1].departureDate);

		printf("mmsi %d\n", containers_in_ship[1][1][1].mmsi);

		printf("x %d\n", containers_in_ship[1][1][1].x);

		printf("y %d\n", containers_in_ship[1][1][1].y);

		

		printf("z %d\n", containers_in_ship[1][1][1].z);

		printf("length %f\n", containers_in_ship[1][1][1].length);

		printf("width %f\n", containers_in_ship[1][1][1].width);
		
		printf("thicknessExternal %f\n", containers_in_ship[1][1][1].thicknessExternal);
		
		printf("thicknessMiddle %f\n", containers_in_ship[1][1][1].thicknessMiddle);
		
		printf("thicknessInternal %f\n", containers_in_ship[1][1][1].thicknessInternal);
		
		printf("kExternal %f\n", containers_in_ship[1][1][1].kExternal);
		
		printf("kMiddle %f\n", containers_in_ship[1][1][1].kMiddle);
		
		printf("kInternal %f\n", containers_in_ship[1][1][1].kInternal);
		


		
		printf("numberContainer %s\n", containers_in_ship[12][12][12].numberContainer);








   

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

	

		

   return 0;

}




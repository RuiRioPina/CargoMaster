#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "asmc.h"

struct container {
	
    double length; /* 8 bytes */
    double width; /* 8 bytes */
    double height; /* 8 bytes */
    double thicknessExternal; /* 8 bytes */
    double thicknessMiddle; /* 8 bytes */
    double thicknessInternal; /* 8 bytes */    
    double kExternal; /* 8 bytes */    
    double kMiddle; /* 8 bytes */    
    double kInternal; /* 8 bytes */    
    int mmsi; /* 4 bytes */      
	int x; /* 4 byte */
    int y; /* 4 byte */
    int z; /* 4 byte */    
    int xMax; /* 4 bytes */
    int yMax; /* 4 byte */
    int zMax; /* 4 byte */    
    char typeOfContainer[40]; /* 40 bytes */
    char load[40]; /* 40 bytes */
    char arrivalPort[40]; /* 40 bytes */
    char arrivalDate[40]; /* 40 bytes */
    char departureDate[40]; /* 40 bytes */   
    char numberContainer[11]; /* 11 bytes */ 
    
};

struct container * containers_in_ship;
int lines = 0;

void fill_array_of_structs() {
	
   char first_line = 0;

   static const char filename[] = "ContainersInManifest.csv";  

   FILE *file = fopen ( filename, "r" );  

   if ( file != NULL ) {    

     char line [ 512 ]; 
     char ch[512];
     while(!feof(file))
{
  *ch = fgetc(file);
  if(*ch == '\n')
  {
	
    lines++;
  }
}

    lines--;
	containers_in_ship = (struct container *)malloc(lines*sizeof(struct container));
	rewind(file);
		
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

				containers_in_ship[first_line-1].mmsi = atoi(token[0]);

				containers_in_ship[first_line-1].length = atof(token[1]);
			
				containers_in_ship[first_line-1].height = atof(token[21]);

				containers_in_ship[first_line-1].width = atof(token[2]);

				strcpy(containers_in_ship[first_line-1].numberContainer, strdup(token[3]));

				strcpy(containers_in_ship[first_line-1].typeOfContainer, strdup(token[4]));

				strcpy(containers_in_ship[first_line-1].load, strdup(token[5]));

				
				containers_in_ship[first_line-1].x = x;

				containers_in_ship[first_line-1].y = y;

				containers_in_ship[first_line-1].z = z;

				strcpy(containers_in_ship[first_line-1].arrivalPort, strdup(token[9]));

				strcpy(containers_in_ship[first_line-1].arrivalDate, strdup(token[10]));

				strcpy(containers_in_ship[first_line-1].departureDate, strdup(token[11]));

				containers_in_ship[first_line-1].xMax = atoi(token[12]);

				containers_in_ship[first_line-1].yMax = atoi(token[13]);

				containers_in_ship[first_line-1].zMax = atoi(token[14]);
				
				containers_in_ship[first_line-1].zMax = atoi(token[14]);
				
				containers_in_ship[first_line-1].thicknessExternal = atof(token[15]);
				
				containers_in_ship[first_line-1].thicknessMiddle = atof(token[16]);

				containers_in_ship[first_line-1].thicknessInternal = atof(token[17]);
				
				containers_in_ship[first_line-1].kExternal = atof(token[18]);

				containers_in_ship[first_line-1].kMiddle = atof(token[19]);

				containers_in_ship[first_line-1].kInternal = atof(token[20]);
		}
				first_line++;	
		}

}
		fclose ( file );   
}

double energy_calculator(int xx, int yy, int zz) {

		double temperature, actualTemperature = 20;
		double R_external, R_middle, R_internal, R_total;
		double totalArea, Q, E;
		struct container c;
		
		int type = refrigerated_or_not(xx, yy, zz);
		
		if (type == 0) {
			temperature = -5;
		}
		if (type == 1 ) {
			temperature = 7;
		}
		if (type == -1) {
			return 0;
		}
		
		for(int i = 0; i < lines; i++) {
		if (containers_in_ship[i].x == xx && containers_in_ship[i].y == yy && containers_in_ship[i].z == zz) {
			c = containers_in_ship[i];
			} 
		}
		
		totalArea = ((c.length * c.height) * 3) + ((c.height * c.width) * 2);
		
		R_external = c.thicknessExternal / (totalArea * c.kExternal);
		R_middle = c.thicknessMiddle / (totalArea * c.kMiddle);
		R_internal = c.thicknessInternal / (totalArea * c.kInternal);
		
		R_total = R_external + R_middle + R_internal;
		
		Q = (actualTemperature - temperature) / R_total;
		
		E = Q * 3600; //energy per hour 
		
		return E;
	
}
double energy_calculator_411(int xx, int yy, int zz,double tempe) {

		double temperature, actualTemperature = tempe;
		double R_external, R_middle, R_internal, R_total;
		double totalArea, Q, E;
		struct container c;
		
		int type = refrigerated_or_not(xx, yy, zz);
		
		if (type == 0) {
			temperature = -5;
		}
		if (type == 1 ) {
			temperature = 7;
		}
		if (type == -1) {
			return 0;
		}
		
		for(int i = 0; i < lines; i++) {
		if (containers_in_ship[i].x == xx && containers_in_ship[i].y == yy && containers_in_ship[i].z == zz) {
			c = containers_in_ship[i];
			} 
		}
		
		totalArea = ((c.length * c.height) * 3) + ((c.height * c.width) * 2);
		
		R_external = c.thicknessExternal / (totalArea * c.kExternal);
		R_middle = c.thicknessMiddle / (totalArea * c.kMiddle);
		R_internal = c.thicknessInternal / (totalArea * c.kInternal);
		
		R_total = R_external + R_middle + R_internal;
		
		Q = (actualTemperature - temperature) / R_total;
		
		E = Q * 3600; //energy per hour 
		
		return E;
	
}
double energy_for_ship(struct container* containers,double temperature){
	double finalvalue=0;
		for (int i =0;i<lines;i++){
			finalvalue+=energy_calculator_411(containers[i].x,containers[i].y,containers[i].z,temperature);
		}
		return finalvalue;
}
/* Esta função é uma alternativa mais eficiente à outra
double energy_for_ship2(struct container* containers){
	double temperature, actualTemperature = 20;
		double R_external, R_middle, R_internal, R_total;
		double totalArea, Q, E;

	double finalvalue=0;
		for (int i =0;i<lines;i++){
			if (strcmp(containers[i].typeOfContainer,"MAXMINUS5")==0){
				temperature = -5;
			}
			if (strcmp(containers[i].typeOfContainer,"Refrigerated")==0){
				temperature =7;
			}
			totalArea = ((containers[i].length * containers[i].height) * 3) + ((containers[i].height * containers[i].width) * 2);
		
		R_external = containers[i].thicknessExternal / (totalArea * containers[i].kExternal);
		R_middle = containers[i].thicknessMiddle / (totalArea * containers[i].kMiddle);
		R_internal = containers[i].thicknessInternal / (totalArea * containers[i].kInternal);
		
		R_total = R_external + R_middle + R_internal;
		
		Q = (actualTemperature - temperature) / R_total;
		
		E = Q * 3600; //energy per hour 	
			finalvalue+=E;
		}
		return finalvalue;
}
* */
void is_energy_enough(struct container* containers,double temperature,double gen_energy){
	printf("Generator energy:\n%f\n",gen_energy*3600);
	double needed_energy=energy_for_ship(containers,temperature);
	printf("Needed energy:\n%f\n",needed_energy);
	if (needed_energy>gen_energy*3600){
		printf("Energy provided is not enough!\n");
	}else{
		printf("Energy provided is enough.\n");
	}
}
int main ( void ) {

		//US409
		//Prints for debugging US409 | To be deleted later on

		fill_array_of_structs(containers_in_ship);
	
		for(int i = 0; i < lines;i++) {
		printf("numberContainer %s\n", containers_in_ship[i].numberContainer);
		printf("typeOfContainer %s\n", containers_in_ship[i].typeOfContainer);
		printf("load %s\n", containers_in_ship[i].load);
		printf("arrivalPort %s\n", containers_in_ship[i].arrivalPort);
		printf("arrivalDate %s\n", containers_in_ship[i].arrivalDate);
		printf("departureDate %s\n", containers_in_ship[i].departureDate);
		printf("mmsi %d\n", containers_in_ship[i].mmsi);
		printf("x %d\n", containers_in_ship[i].x);
		printf("y %d\n", containers_in_ship[i].y);
		printf("z %d\n", containers_in_ship[i].z);
		printf("length %f\n", containers_in_ship[i].length);
		printf("width %f\n", containers_in_ship[i].width);
		printf("height %f\n", containers_in_ship[i].height);
		printf("thicknessExternal %f\n", containers_in_ship[i].thicknessExternal);
		printf("thicknessMiddle %f\n", containers_in_ship[i].thicknessMiddle);
		printf("thicknessInternal %f\n", containers_in_ship[i].thicknessInternal);
		printf("kExternal %f\n", containers_in_ship[i].kExternal);
		printf("kMiddle %f\n", containers_in_ship[i].kMiddle);
		printf("kInternal %f\n", containers_in_ship[i].kInternal);
		printf("\n");
		printf("-");
		printf("\n");
		}
		
		//US410
		int x1 = 1;
		int y1 = 1;
		int z1 = 1;
		
		double energy1 = energy_calculator(x1,y1,z1);
		printf("The container in the position %d,%d,%d needs %f J of energy per hour\n",x1,y1,z1,energy1);
		//US411
		double cdegrees=20;
		double generator_energy=999999999;
		is_energy_enough(containers_in_ship,cdegrees,generator_energy);
		
		
   return 0;

}




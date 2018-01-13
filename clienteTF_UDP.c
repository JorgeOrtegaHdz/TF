#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netdb.h>
#include <strings.h>
#include <string.h>
#include <math.h>
#define MAX_TAM 20
#define MAX_TAM_1 3

int puerto = 7200;

int main(int argc,char** argv){
   struct sockaddr_in msg_to_server_addr, client_addr;
   int s, num[2], res,j,pos=0;
   long n=atoi(argv[2]);
   double x,y,k,i;
   char dir[10];//Sirve para indicar la direccion del servidor
   char cad[50];
   memcpy(dir,argv[1],13*sizeof(char));
   s = socket(AF_INET, SOCK_DGRAM, 0);
   printf("Enviando a: %s\n",dir);
   /* rellena la dirección del servidor */
   bzero((char *)&msg_to_server_addr, sizeof(msg_to_server_addr));
   msg_to_server_addr.sin_family = AF_INET;
   msg_to_server_addr.sin_addr.s_addr = inet_addr(dir);
   msg_to_server_addr.sin_port = htons(6000);
   
   /* rellena la direcciòn del cliente*/
   bzero((char *)&client_addr, sizeof(client_addr));
   client_addr.sin_family = AF_INET;
   client_addr.sin_addr.s_addr =INADDR_ANY;
   client_addr.sin_port=htons(1234); /*cuando se utiliza por numero de puerto el 0, el sistema se encarga de asignarle uno */
   
   //Hacemos un canal de comunicación
   bind(s, (struct sockaddr *)&client_addr,sizeof(client_addr));

   
   //Hacemos el calculo del n-esimo termino de la serie de Furier
   printf("Se calculara S%i\n",n);

   for(x=-M_PI*200;x<=M_PI*200;x+=1.5708){
      y=0;
      k=1;
      for(j=0;j<n;j++){
         y+=(1/k*sin(k*(x/200)));
         k+=2;
      }
      y*=4/M_PI;
      y*=200;
      printf("Se genero: x=%f  ,  y=%f\n",x,y);
      //Se arma el mensaje
      bzero(&cad,sizeof(cad));
      sprintf(&cad,"%i/%f/%f/%d",pos,x,y,1);
      //Se envia el mensaje
      sendto(s, &cad,sizeof(cad), 0, (struct sockaddr *) &msg_to_server_addr, sizeof(msg_to_server_addr));
      printf("Se envio: %s\n",cad);
      pos++;
      usleep(100000);
   }
   close(s);
   return 0;
}

#include <Wire.h>

#define adress_2 0x2
#define adress_1 0x1


int vec_note[100];
int dur_note[100];//duration
int nr_note=0;
int buzzer=5;


int selectie = 1;

void setup() {
  pinMode(buzzer,OUTPUT);
  Serial.begin(9600);
  Wire.begin(adress_2);
  Wire.onReceive(receive_func);
 
}

void loop() {

}

char buff[100];
char nota_s[15];
char durata_s[10];

void receive_func(int howMany) {
  if(Wire.available()>0){
    byte l = Wire.read();
    for(int i=0; i<l; i++)
    {
      char c = Wire.read();
      buff[i]=c;
    }
    buff[l]='\0';
    strncpy(nota_s,buff,10);
    strncpy(durata_s,buff+10,10);
    int nota = atoi(nota_s);
    int durata = atoi(durata_s);
    
    
      command(nota,durata);

  }
}

void command(int nota, int durata)
{
      tone(buzzer,nota,durata);
}

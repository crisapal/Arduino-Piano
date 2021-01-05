//programare pian - arduino mega
#include <LiquidCrystal_I2C.h>


//setare frecventa pinuri
#define T_DO1 261
#define T_RE 293
#define T_MI 329
#define T_FA 349
#define T_SOL 392
#define T_LA 440
#define T_SI 493
#define T_DO2 523

// I2C address 0x27, 16 column and 2 rows
LiquidCrystal_I2C lcd(0x27, 16, 2); 

//setare globala a pinilor pentru note
const int DO1 = 2;
const int RE = 3;
const int MI = 4;
const int FA = 5;
const int SOL = 6;
const int LA = 8;
const int SI = 9;
const int DO2 = 10;


const int Buzz = 12;
const int LED = 13;

void setup()
{
  pinsSetting();
  lcd.init(); // initialize the lcd
  lcd.backlight();
  lcd.print("Bella, ciao!");
 
}

void loop()
{
  while(digitalRead(DO1) == LOW)
  {
    tone(Buzz,T_DO1);
    digitalWrite(LED,HIGH);
  }

  while(digitalRead(RE) == LOW)
  {
    tone(Buzz,T_RE);
    digitalWrite(LED,HIGH);
  }

  while(digitalRead(MI) == LOW)
  {
    tone(Buzz,T_MI);
    digitalWrite(LED,HIGH);
  }

  while(digitalRead(FA) == LOW)
  {
    tone(Buzz,T_FA);
    digitalWrite(LED,HIGH);
  }

  while(digitalRead(SOL) == LOW)
  {
    tone(Buzz,T_SOL);
    digitalWrite(LED,HIGH);
  }

  while(digitalRead(LA) == LOW)
  {
    tone(Buzz,T_LA);
    digitalWrite(LED,HIGH);
  }

  while(digitalRead(SI) == LOW)
  {
    tone(Buzz,T_SI);
    digitalWrite(LED,HIGH);
  }

   while(digitalRead(DO2)== LOW)
  {
    tone(Buzz,T_DO2);
    digitalWrite(LED,HIGH);
  }

  noTone(Buzz);
  digitalWrite(LED,LOW);

}


void pinsSetting(){
   pinMode(LED, OUTPUT);
  pinMode(DO1, INPUT);
  digitalWrite(DO1,HIGH);
  
  pinMode(RE, INPUT);
  digitalWrite(RE,HIGH);
  
  pinMode(MI, INPUT);
  digitalWrite(MI,HIGH);
  
  pinMode(FA, INPUT);
  digitalWrite(FA,HIGH);
  
  pinMode(SOL, INPUT);
  digitalWrite(SOL,HIGH);
  
  pinMode(LA, INPUT);
  digitalWrite(LA,HIGH);
  
  pinMode(SI, INPUT);
  digitalWrite(SI,HIGH);

  pinMode(DO2, INPUT);
  digitalWrite(DO2,HIGH);


  digitalWrite(LED,LOW);}

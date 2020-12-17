//programare pian - arduino mega
#include <LiquidCrystal_I2C.h>

#include <Wire.h>

 
#define adress_2 0x2
#define adress_1 0x1
    

// I2C address 0x27, 16 column and 2 rows
LiquidCrystal_I2C lcd(0x27, 16, 2); 

//setare globala a pinilor pentru note
const int pins[8]={2,3,4,5,6,8,9,10}; //clape DO1,RE,MI,FA,SOL,LA,SI,DO2

//setare frecventa pinuri
const int notes[8]={261, 292, 329, 349,392, 440, 493, 523};

//starea butoanelor -high or low
int stare[8]={0,0,0,0, 0, 0,0,0};
int stare_recenta[8]={0,0,0,0,0,0,0,0};
int startPressed[8]={0,0,0,0,0,0,0,0};
int endPressed[8]={0,0,0,0,0,0,0,0};
int timeReleased[8]={0,0,0,0,0,0,0,0};
int timeHold[8]={0,0,0,0,0,0,0,0};

char timeHoldS[10];
char nota[10];
char durata[10];
char structura[20];


void setup()
{
  Serial.begin(9600);
  pinsSetting();
  lcd.init(); // initialize the lcd
  lcd.backlight();
  lcd.print("Bella, ciao!");

  Wire.begin(adress_1);
 
 
}

void loop() {
  
  for(int i =0;i<8;i++){
  
   stare[i] = digitalRead(pins[i]);

  if (stare[i] != stare_recenta[i]) {
      
      if (stare[i] == HIGH) {
          startPressed[i] = millis();
          timeReleased[i] = startPressed[i] - endPressed[i];

          itoa(notes[i],nota,10);
          itoa(0,durata,10);
          strncpy(structura,nota,10);
          for(int j=strlen(nota); j<=10; j++)
            structura[j]=' ';
          strncpy(structura+10,durata,10);
          Serial.print(structura);
          Wire.beginTransmission(adress_2);
          Wire.write(20);
          Wire.write(structura);
          Wire.endTransmission();

      } else {
      
          endPressed[i] = millis();
          timeHold[i] = endPressed[i] - startPressed[i];
          itoa(0,nota,10);
          itoa(timeHold[i],durata,10);
           strncpy(structura,nota,10);
          for(int j=strlen(nota); j<=10; j++)
            structura[j]=' ';
          strncpy(structura+10,durata,10);
          Serial.print(structura);
          Wire.beginTransmission(adress_2);
          Wire.write(20);
          Wire.write(structura);
          Wire.endTransmission();
          
      }

  }
  }
  
  for(int i=0;i<8;i++)
  stare_recenta[i] = stare[i];
 
  }


void pinsSetting(){
  for(int i=0;i<8;i++)
      pinMode(pins[i], INPUT);
  }

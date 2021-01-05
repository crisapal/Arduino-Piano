#include <LiquidCrystal_I2C.h>


LiquidCrystal_I2C lcd(0x27, 16, 2); // I2C address 0x27, 16 column and 2 rows
// keys frequenqy
#define T_DO1 261
#define T_RE 293
#define T_MI 329
#define T_FA 349
#define T_SOL 392
#define T_LA 440
#define T_SI 493
#define T_DO2 523

//global setting of piano keys
const int DO1 = 11;
const int RE = 10;
const int MI = 9;
const int FA = 7;
const int SOL = 5;
const int LA = 4;
const int SI = 3;
const int DO2 = 2;

//buzzer hard setup
const int Buzz = 12;
const int LED = 13;

char songName[17] = ""; // a String to hold incoming data
char notes[151] = ""; // song notes
char comanda[5] = ""; // control flow command
char workingString[300] = "";
int index = 0, i = 0;

int recordedSong[200];
unsigned long duration[200];

int indexRecorded = 0;
unsigned long prevMillis = 0;
unsigned long curMillis;
boolean playedOnce = false; //applied as a recording flag

int Li = 16; //limits for the animated LCD display
int Lii = 0;

void setup() {
  pinsSetting();
  lcd.init(); // initialize the lcd
  lcd.backlight();
  Serial.begin(9600); //connecting to the Serial port
}

void loop() {

  if (strcmp(comanda, "PLAY") == 0) { //playing song and printing notes in a specific animation

    lcd.setCursor(0, 0);
    lcd.print(songName);

    curMillis = millis();
    if (i < 160) {
      if ((curMillis - prevMillis) >= 400) {
        lcd.setCursor(0, 1);
        lcd.print(Scroll_LCD_Left(convertToString(notes, 150)));
        prevMillis = curMillis;
        i++;
      }
    } else {
      prevMillis = curMillis;
      cleanLCD();

    }

  } else if (strcmp(comanda, "STOP") == 0) { // printing stop
    lcd.setCursor(0, 0);
    lcd.print("STOPPED");
    lcd.setCursor(0, 1);
    lcd.print(songName);

         

  } else if (strcmp(comanda, "RCRD") == 0) { //listening to recording
    lcd.setCursor(0, 0);
    lcd.print("PLAYING REC");
    lcd.setCursor(0, 1);
    lcd.print(songName);

        
    if (playedOnce == false) {
      playRecord();
      playedOnce = true;
    }
  
  }

  playSong();

}

void serialEvent() { //geting commands and dat from the Java app

  while (Serial.available()) {

    char inChar = (char) Serial.read();
    workingString[index++] = inChar;

    if (workingString[0] == 'P' && workingString[1] == 'L' && workingString[2] == 'A' && workingString[3] == 'Y' 
    && strstr(workingString, "!") && strstr(workingString, ".")) {
      cleanLCD();
      cleanRecord();
      strncpy(comanda, "PLAY", 4);
      strncpy(songName, workingString + 4, 16);
      strncpy(notes, workingString + 20, 150);

      //cleaning code
      cleanResources();

    } 
    
    else if (workingString[0] == 'S' && workingString[1] == 'T' && workingString[2] == 'O' && workingString[3] == 'P') {
      cleanLCD();
      sendSong();
      
      strncpy(comanda, "STOP", 4);
      //cleaning code

         
      cleanResources();

    } else if (workingString[0] == 'R' && workingString[1] == 'C' && workingString[2] == 'R' && workingString[3] == 'D') {
      cleanLCD();
      strncpy(comanda, "RCRD", 4);
          
      //cleaning code
      cleanResources();

    }

  }
}

//cleaning filled buffers
void cleanResources() {
  for (int i = 0; i < 300; i++)
    workingString[i] = 0;

  index = 0;
  playedOnce = false;
  Li = 16;
  Lii = 0;

}

//action on prssed buttons
void playSong() {
  boolean ok_do1 = false, ok_re = false, ok_mi = false, ok_fa = false, ok_sol = false, ok_la = false, ok_si = false, ok_do2 = false;

  unsigned long now = millis();

  while (digitalRead(DO1) == LOW) {
    tone(Buzz, T_DO1);
    digitalWrite(LED, HIGH);
    ok_do1 = true;
  }
  if (ok_do1) {
    recordedSong[indexRecorded] = T_DO1;
    duration[indexRecorded++] = millis() - now;
    ok_do1 = false;
  }

  while (digitalRead(RE) == LOW) {
    tone(Buzz, T_RE);
    digitalWrite(LED, HIGH);
    ok_re = true;

  }
  if (ok_re) {
    recordedSong[indexRecorded] = T_RE;
    duration[indexRecorded++] = millis() - now;

    ok_re = false;
  }

  while (digitalRead(MI) == LOW) {
    tone(Buzz, T_MI);
    digitalWrite(LED, HIGH);
    ok_mi = true;

  }
  if (ok_mi) {
    recordedSong[indexRecorded] = T_MI;
    duration[indexRecorded++] = millis() - now;

    ok_mi = false;
  }

  while (digitalRead(FA) == LOW) {
    tone(Buzz, T_FA);
    digitalWrite(LED, HIGH);
    ok_fa = true;
  }
  if (ok_fa) {
    recordedSong[indexRecorded] = T_FA;
    duration[indexRecorded++] = millis() - now;

    ok_fa = false;
  }

  while (digitalRead(SOL) == LOW) {
    tone(Buzz, T_SOL);
    digitalWrite(LED, HIGH);
    ok_sol = true;
  }
  if (ok_sol) {
    recordedSong[indexRecorded] = T_SOL;
    duration[indexRecorded++] = millis() - now;

    ok_sol = false;
  }

  while (digitalRead(LA) == LOW) {
    tone(Buzz, T_LA);
    digitalWrite(LED, HIGH);
    ok_la = true;

  }
  if (ok_la) {
    recordedSong[indexRecorded] = T_LA;
    duration[indexRecorded++] = millis() - now;

    ok_la = false;
  }

  while (digitalRead(SI) == LOW) {
    tone(Buzz, T_SI);
    digitalWrite(LED, HIGH);
    ok_si = true;
  }
  if (ok_si) {
    recordedSong[indexRecorded] = T_SI;
    duration[indexRecorded++] = millis() - now;

    ok_si = false;
  }

  while (digitalRead(DO2) == LOW) {
    tone(Buzz, T_DO2);
    digitalWrite(LED, HIGH);
    ok_do2 = true;
  }
  if (ok_do2) {
    recordedSong[indexRecorded] = T_DO2;
    duration[indexRecorded++] = millis() - now;

    ok_do2 = false;
  }
  digitalWrite(LED, LOW);
  noTone(Buzz);

}

//initial setup for buttons
void pinsSetting() {
  pinMode(LED, OUTPUT);
  digitalWrite(LED, LOW);

  pinMode(DO1, INPUT);
  digitalWrite(DO1, HIGH);

  pinMode(RE, INPUT);
  digitalWrite(RE, HIGH);

  pinMode(MI, INPUT);
  digitalWrite(MI, HIGH);

  pinMode(FA, INPUT);
  digitalWrite(FA, HIGH);

  pinMode(SOL, INPUT);
  digitalWrite(SOL, HIGH);

  pinMode(LA, INPUT);
  digitalWrite(LA, HIGH);

  pinMode(SI, INPUT);
  digitalWrite(SI, HIGH);

  pinMode(DO2, INPUT);
  digitalWrite(DO2, HIGH);

}

// LCD left to right animation 
String Scroll_LCD_Left(String StrDisplay) {
  String result;
  String StrProcess = "                " + StrDisplay + "                ";
  result = StrProcess.substring(Li, Lii);
  Li++;
  Lii++;
  if (Li > StrProcess.length()) {
    Li = 16;
    Lii = 0;
  }
  return result;
}



String convertToString(char * a, int size) {
  int i;
  String s = "";
  for (i = 0; i < size; i++) {
    s = s + a[i];
  }
  return s;
}

//playing by duration and note frequency
void playRecord() {

  for (int i = 0; i < indexRecorded; i++) {
    tone(Buzz, recordedSong[i],duration[i]);
    delay(400);

  }
  //Stop tone on buzzerPin
  noTone(Buzz);
  delay(100);

}


//String with char notes to be pattern matched in Java
String convertToNotes(int notes[200]) {
  String songNotes = "";
  songNotes.reserve(200);

  for (int i = 0; i < 200; i++) {
    if (notes[i] == T_DO1)
      songNotes += "DO1 ";
    else
    if (notes[i] == T_RE)
      songNotes += "RE ";
    if (notes[i] == T_MI)
      songNotes += "MI ";
    if (notes[i] == T_FA)
      songNotes += "FA ";
    if (notes[i] == T_SOL)
      songNotes += "SOL ";
    if (notes[i] == T_LA)
      songNotes += "LA ";
    if (notes[i] == T_SI)
      songNotes += "SI ";
    if (notes[i] == T_DO2)
      songNotes += "DO2 ";
  }
  return songNotes;
}

//non-blocking LCD cleaning
void cleanLCD() {
  lcd.setCursor(0, 0);
  lcd.print("                       ");
  lcd.setCursor(0, 1);
  lcd.print("                       ");
}

void cleanRecord() {
  for (int i = 0; i < 200; i++) {
    recordedSong[i] = 0;
    duration[i] = 0;
  }

  indexRecorded = 0;
}

//sending notes in a serial way
void sendSong(){

   char sendable[150]="";
   String copie=convertToNotes(recordedSong);
          for(int i=0;i<149;i++)
          if(i<copie.length())
          sendable[i]=copie[i];
          else
          sendable[i]=' ';
          sendable[149]='\0';
          
          Serial.write(sendable,150);
          Serial.flush();

 }

#include <Wire.h>
#include <LiquidCrystal_I2C.h>


LiquidCrystal_I2C lcd(0x27, 16, 2);
void setup()
{
  lcd.init(); 
  lcd.backlight();

 Wire.begin(); 
lcd.print("Master setup");
}

byte x = 0;

void loop()
{
 Wire.beginTransmission(9); 
 Wire.write(x);               
 Wire.endTransmission();   
lcd.setCursor(0,1);
lcd.print(x);
 x++;
 delay(1000);
}

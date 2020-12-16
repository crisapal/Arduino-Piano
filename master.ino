#include <Wire.h>
#include <LiquidCrystal_I2C.h>


LiquidCrystal_I2C lcd(0x27, 16, 2);
void setup()
{
  lcd.init(); // initialize the lcd
  lcd.backlight();

 Wire.begin(); // join i2c bus (address optional for master)
lcd.print("Master setup");
}

byte x = 0;

void loop()
{
 Wire.beginTransmission(9); // transmit to device #4
 Wire.write(x);              // sends one byte  
 Wire.endTransmission();    // stop transmitting
lcd.setCursor(0,1);
lcd.print(x);
 x++;
 delay(1000);
}

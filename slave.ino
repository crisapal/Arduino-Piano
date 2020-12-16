#include <Wire.h>


byte x = 0;

void setup()
{
  Serial.begin(9600);           // start serial for output
 Wire.begin(9);                // join i2c bus with address #4
 Wire.onReceive(receiveEvent); // register event
 Serial.println("Saracia");
}

void receiveEvent(int bytes) {
x = Wire.read();
}

void loop() {
 Serial.println(x);
 delay(1000);
}
 

#include <SoftwareSerial.h>

SoftwareSerial SIM900A(10,11); // RX | TX
// Connect the SIM900A TX to Arduino pin 2 RX. 
// Connect the SIM900A RX to Arduino pin 3 TX. 
char c = ' ';
void setup() 
{
// start th serial communication with the host computer
Serial.begin(9600);
while(!Serial);
Serial.println("Arduino with SIM900A is ready");
// start communication with the SIM900A in 9600
SIM900A.begin(9600); 
Serial.println("SIM900A started at 9600");
delay(1000);
Serial.println("Setup Complete! SIM900A is Ready!");
}
void loop()
{
// Keep reading from SIM800 and send to Arduino Serial Monitor
if (SIM900A.available())
{ c = SIM900A.read();
Serial.write(c);}
// Keep reading from Arduino Serial Monitor and send to SIM900A
if (Serial.available())
{ c = Serial.read();
/*if(c=='m'){
  Serial.println("Invio sms!");
  SIM900A.write(0x1a);
}else{*/
SIM900A.write(c); 
//}
}


}

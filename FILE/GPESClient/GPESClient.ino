#include "Internet.h"
Internet internet;

WiFiUDP Udp;

void setup()
{
  Serial.begin(9600);
  internet= Internet("Saccani ROG MODEM","SaCCaNiWiFi01",4020);
  internet.connect();

}

void loop()
{


}

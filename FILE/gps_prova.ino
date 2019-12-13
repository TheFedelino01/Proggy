#include <TinyGPS++.h>

#include <SoftwareSerial.h>

TinyGPSPlus gps;  // The TinyGPS++ object

SoftwareSerial ss(4, 5); // The serial connection to the GPS device
float latitude , longitude;
int year , month , date, hour , minute , second;
String date_str , time_str , lat_str , lng_str;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  ss.begin(9600);
  
}

void loop() {
  while (ss.available() > 0)
    if (gps.encode(ss.read()))
    {
      if (gps.location.isValid())
      {
        latitude = gps.location.lat();
        lat_str = String(latitude , 6);
        longitude = gps.location.lng();
        lng_str = String(longitude , 6);
      }

      if (gps.date.isValid())
      {
        date_str = "";
        date = gps.date.day();
        month = gps.date.month();
        year = gps.date.year();

      }
      date_str += date;
      date_str += "/";
      date_str += month;
      date_str += "/";
      date_str += year;

      
      if (gps.time.isValid())
      {
        time_str = "";
        hour = gps.time.hour();
        minute = gps.time.minute();
        second = gps.time.second();
      }
        if(month <= 3 || month >= 10){
        hour++;
        }
        time_str += String(hour);

        time_str += " : ";

        time_str += String(minute);    
    }
  Serial.println(lng_str);
  Serial.println(lat_str);
  Serial.println(time_str);
  Serial.println(date_str);
  delay(50);
    }

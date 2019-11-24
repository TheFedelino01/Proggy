#include "Arduino.h"
#include "Internet.h"

//--------------------------------------------------------------------------------------------------------------
//METODI PUBBLICI
//--------------------------------------------------------------------------------------------------------------
Internet::Internet()
{
  this->wifiName="";
  this->password="";
  this->porta=-1;
}

Internet::Internet(String wifiName, String password, int porta)
{
  this->wifiName=wifiName;
  this->password=password;
  this->porta=porta;
}

void Internet::connect(){
  riconnessioneInizio=millis();
  
  localUdpPort = porta;
  
  Serial.println("Avvio connessione");
  
  WiFi.begin(wifiName, password);
  WiFi.setSleepMode(WIFI_NONE_SLEEP); //Non deve andare in sospensione

  
  /*IPAddress ip(192,168,1,60); //IP con virgole! Non con i punti
  IPAddress gateway(192,168,1,1);
  IPAddress subnet(255,255,255,0);
  WiFi.config(ip, gateway, subnet);   */

  Serial.println("Connecting");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.println(".");
    delay(500);
  }

  Udp.begin(localUdpPort);
  
  Serial.println("Connessione Avvenuta! IP:"+ String(WiFi.localIP().toString().c_str())+", UDP port:"+localUdpPort);
  Serial.println("Status WIFI: "+statusToString(WiFi.status()));
}


boolean Internet::chkConnection() {
  if(millis()-riconnessioneInizio>INTERVAL_CONNECTION_CHECK){
    Serial.println("Effettuo riconnessione forzata avendo aspettato: "+ String(INTERVAL_CONNECTION_CHECK)+" ms");
    WiFi.disconnect();
    WiFi.disconnect();
    Serial.println("Status WIFI: "+statusToString(WiFi.status()));
    connect();
    return true;
  }
  return false;
}


String Internet::receive(){
  int packetSize = Udp.parsePacket();//Prendo il pacchetto "se esiste"
  
  if (packetSize)
  {
    // receive incoming UDP packets
    Serial.println("Ricevuto "+String(packetSize)+" bytes: "+Udp.remoteIP().toString().c_str()+", port: "+Udp.remotePort());
    
    int len = Udp.read(incomingPacket, sizeof(incomingPacket));
    if (len > 0)
    {
      incomingPacket[len] = 0;
    }
    Serial.println("Contenuto pacchetto UDP: "+String(incomingPacket));
    
    return String(incomingPacket);
  }
  return "";
}


void Internet::reply(String txt){
  txt.toCharArray(replyPacket, sizeof(replyPacket));
  
  //send back a reply, to the IP address and port we got the packet from
  Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
  Udp.write(replyPacket);
  Udp.endPacket();
}


//--------------------------------------------------------------------------------------------------------------
//METODI PRIVATI
//--------------------------------------------------------------------------------------------------------------



String Internet::statusToString(wl_status_t status) {
  switch (status) {
    case WL_NO_SHIELD: return "WL_NO_SHIELD";
    case WL_IDLE_STATUS: return "WL_IDLE_STATUS";
    case WL_NO_SSID_AVAIL: return "WL_NO_SSID_AVAIL";
    case WL_SCAN_COMPLETED: return "WL_SCAN_COMPLETED";
    case WL_CONNECTED: return "WL_CONNECTED";
    case WL_CONNECT_FAILED: return "WL_CONNECT_FAILED";
    case WL_CONNECTION_LOST: return "WL_CONNECTION_LOST";
    case WL_DISCONNECTED: return "WL_DISCONNECTED";
  }
}

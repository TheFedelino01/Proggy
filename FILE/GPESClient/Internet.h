#ifndef Internet_h
#define Internet_h

//Libbrerie
#include "Arduino.h"
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

class Internet
{
  private:
    WiFiUDP Udp;
    int localUdpPort = 4210;  // local port to listen on
    char incomingPacket[2048];  // buffer for incoming packets
    char replyPacket[2048];  // a reply string to send back
    int INTERVAL_CONNECTION_CHECK=3600000; //1h
    int riconnessioneInizio;
    
    //Parametri di connessione
    String wifiName, password;
    int porta;
    
    //METODI PRIVATI
    
    String statusToString(wl_status_t status);
    
  public:
    Internet();
    Internet(String wifiName, String password, int porta);
    void connect();
    boolean chkConnection(); //Da richiamare per essere sicuri che ESP rimanga online (NECESSARIO)
    String receive(); //Restituisce la str che riceve (se non riceve niente, restituisce "")
    void reply(String txt); //Risponde all'ultimo IP ricevuto, invia il messaggio di parametro
  
};

#endif

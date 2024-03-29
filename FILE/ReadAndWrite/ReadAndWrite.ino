#include <SPI.h>
#include <MFRC522.h>

#define RST_PIN         9           // Configurable, see typical pin layout above
#define SS_PIN          10          // Configurable, see typical pin layout above

MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance.

MFRC522::MIFARE_Key key;
MFRC522::MIFARE_Key key2;


/**
   Initialize.
*/
void setup() {
  Serial.begin(9600); // Initialize serial communications with the PC
  while (!Serial);    // Do nothing if no serial port is opened (added for Arduinos based on ATMEGA32U4)
  SPI.begin();        // Init SPI bus
  mfrc522.PCD_Init(); // Init MFRC522 car
  key.keyByte[0] = 0xd3;
  key.keyByte[1] = 0xf7;
  key.keyByte[2] = 0xd3;
  key.keyByte[3] = 0xf7;
  key.keyByte[4] = 0xd3;
  key.keyByte[5] = 0xf7;

  for (byte i = 0; i < 6; i++) {
    key2.keyByte[i] = 0xFF;
  }

  //Serial.println(F("Attendo carta...."));
}

/**
   Main loop.
*/
void loop() {

  byte sector         = 1;
  byte blockAddr      = 4;

  byte trailerBlock   = 7;
  MFRC522::StatusCode status;
  byte buffer[18];
  byte size = sizeof(buffer);
  int nBlock[] = {4, 5, 6, 8, 9, 10};


  String s = "";
  if (!Serial.available() > 0) {
    return;
  }
  else {
    s = readLine();
    Serial.println(s);
    Serial.println(s.length());
    byte dataBlock1[]    = {
      0x00, 0x00, 0x03, 0x0b, //i byte a 0xfe sono scrivibli
      0xd1, 0x01, 0x07, 0x54,
      0x02, 0x65, 0x6e, 0xfe,
      0xfe, 0xfe, 0xfe, 0xfe
    };

    byte dataBlock[]    = {
      0xfe, 0xfe, 0xfe, 0xfe, //i byte a 0xfe sono scrivibli
      0xfe, 0xfe, 0xfe, 0xfe,
      0xfe, 0xfe, 0xfe, 0xfe,
      0xfe, 0xfe, 0xfe, 0xfe
    };

    byte dataBlock5[]    = {
      0x00, 0x00, 0x03, 0x0c,
      0xd1, 0x01, 0x08, 0x54,
      0x02, 0x65, 0x6e, 0xfe,
      0xfe, 0xfe, 0xfe, 0xfe
    };
    if (s.length() < 5) {

      while (true) {

        if (mfrc522.PICC_IsNewCardPresent()) {

          // Select one of the cards
          if (mfrc522.PICC_ReadCardSerial()) {
            //Serial.println(F("Carta rilevata!"));

            connetto(trailerBlock);
            stringToDataBlocco1(&dataBlock1[0], s);
            scrivo(4, dataBlock1);

            // Halt PICC
            mfrc522.PICC_HaltA();
            // Stop encryption on PCD
            mfrc522.PCD_StopCrypto1();
            return;
          }
        }
      }
    } else if (s.length() == 5) {
      while (true) {


        if (mfrc522.PICC_IsNewCardPresent()) {

          // Select one of the cards
          if (mfrc522.PICC_ReadCardSerial()) {
            //Serial.println(F("Carta rilevata!"));

            connetto(trailerBlock);
            stringToDataBlocco1(&dataBlock5[0], s);
            scrivo(4, dataBlock5);

            // Halt PICC
            mfrc522.PICC_HaltA();
            // Stop encryption on PCD
            mfrc522.PCD_StopCrypto1();
            return;
          }
        }
      }
    } else if (s.length() > 5) {
      while (true) {

        if (mfrc522.PICC_IsNewCardPresent()) {

          // Select one of the cards
          if (mfrc522.PICC_ReadCardSerial()) {
            //Serial.println(F("Carta rilevata!"));

            connetto(trailerBlock);
            uint8_t len = s.length();
            dataBlock5[3] = len + 7;
            dataBlock5[6] = len + 3;
            String s1 = s.substring(0, 5);
            stringToDataBlocco1(&dataBlock5[0], s1);
            scrivo(4, dataBlock5);

            String s2 = s.substring(5);
            for (int i = 0;i<5; i = i + 1) {
                        Serial.println(i);

              byte thisData[]    = {
                0xfe, 0xfe, 0xfe, 0xfe, //i byte a 0xfe sono scrivibli
                0xfe, 0xfe, 0xfe, 0xfe,
                0xfe, 0xfe, 0xfe, 0xfe,
                0xfe, 0xfe, 0xfe, 0xfe
              };

              String subs = s2.substring(i * 16);
              Serial.println(subs.substring(0,16));
              if (subs != "") {
                stringToData(&thisData[0], subs.substring(0,16));
                scrivo(nBlock[i + 1], thisData);
              } else {
                return;
              }
            }


            // Halt PICC
            mfrc522.PICC_HaltA();
            // Stop encryption on PCD
            mfrc522.PCD_StopCrypto1();
            //Serial.println("Preciso, tutto ok!");
            return;
          }
        }
      }
      //end while

    }
  }
}
/**
   Helper routine to dump a byte array as hex values to Serial.
*/
void dump_byte_array(byte *buffer, byte bufferSize) {
  for (byte i = 0; i < bufferSize; i++) {
    //Serial.print(buffer[i] < 0x10 ? " 0" : " ");
    //Serial.print(buffer[i], HEX);
  }
}
MFRC522::StatusCode connetto(int trailerBlock) {
  //Prova le 2 password per autenticarsi
  MFRC522::StatusCode status;
  //Serial.println(F("Connessione..."));
  status = (MFRC522::StatusCode) mfrc522.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, trailerBlock, &key, &(mfrc522.uid));
  if (status != MFRC522::STATUS_OK) {
    status = (MFRC522::StatusCode) mfrc522.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, trailerBlock, &key2, &(mfrc522.uid));
    if (status != MFRC522::STATUS_OK) {
      //Serial.print(F("PCD_Authenticate() failed: "));
      //Serial.println(mfrc522.GetStatusCodeName(status));
    }
  }

  //Serial.println(F("CONNESSO"));
  return status;
}
MFRC522::StatusCode scrivo(int blockAddr, byte dataBlock[]) {
  MFRC522::StatusCode status;
  //Serial.println(F("Scrittura in corso..."));
  status = (MFRC522::StatusCode) mfrc522.MIFARE_Write(blockAddr, dataBlock, 16);
  if (status != MFRC522::STATUS_OK) {
    //Serial.print(F("MIFARE_Write() failed: "));
    //Serial.println(mfrc522.GetStatusCodeName(status));
  }
  //Serial.println(F("Scrittura completata"));
  //Serial.println();
  return status;
}

void stringToDataBlocco1(byte *dataBlock, String s) {//il blocco 1 è stornzo che ha il bit stuffing all'inzio
  for (int i = 11; i <= 15; i++) {
    dataBlock[i] = s.charAt(i - 11);
  }
}

void stringToData(byte *dataBlock, String s) {
  for (int i = 0; i <= 15; i++) {
    dataBlock[i] = s.charAt(i);
  }
}


String readLine() {

  String datoricevuto = "";
  char c;

  while (Serial.available() > 0) {
    c = Serial.read();

    if (c == '\n')
      break;

    else
      datoricevuto += c;
    delay(10);
  }
  return datoricevuto;
}

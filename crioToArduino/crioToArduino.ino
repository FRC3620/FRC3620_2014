#include <Adafruit_NeoPixel.h>

#define PIN 7

// Parameter 1 = number of pixels in strip
// Parameter 2 = Arduino pin number (most are valid)
// Parameter 3 = pixel type flags, add together as needed:
//   NEO_KHZ800  800 KHz bitstream (most NeoPixel products w/WS2812 LEDs)
//   NEO_KHZ400  400 KHz (classic 'v1' (not v2) FLORA pixels, WS2811 drivers)
//   NEO_GRB     Pixels are wired for GRB bitstream (most NeoPixel products)
//   NEO_RGB     Pixels are wired for RGB bitstream (v1 FLORA pixels, not v2)
Adafruit_NeoPixel strip = Adafruit_NeoPixel(132, PIN, NEO_GRB + NEO_KHZ800);

// IMPORTANT: To reduce NeoPixel burnout risk, add 1000 uF capacitor across
// pixel power leads, add 300 - 500 Ohm resistor on first pixel's data input
// and minimize distance between Arduino and first pixel.  Avoid connecting
// on a live circuit...if you must, connect GND first.


//46

//96

//40

int AllianceColor ;

void setup() {

  strip.begin();
  strip.show(); // Initialize all pixels to 'off'
  Serial.begin(9600);
  //getAllianceColor();

}

void loop() {
  int i = getColorCode();


  //getColorCode();

  Serial.print("ac = ");
  Serial.print(getAllianceColor());
  Serial.print("   cc = ");
  Serial.print(getColorCode());



  if (i == 0)
    colorAll(i, 50, strip.Color( 0, 0, 0)); // All off
  else if (i == 1){
    colorWipe(i, 10, strip.Color(255 ,230, 0)); // Blue and yellow colorwipe
    colorWipe(i, 10, strip.Color(0, 0, 255)); 
  }
  else if (i == 2)
    colorAll(i, 15, strip.Color(255,0,0));
  else if (i == 3)
    colorAll(i, 15, strip.Color(0,0,255));
  else if (i == 4)
    allianceColorWithSonar(i, 15, strip.Color( 0, 255, 0));
  else if (i == 5)
    colorAll(i, 0, strip.Color( 255, 0, 235)); // All off
  else if (i == 6)
    colorAll(i, 50, strip.Color( 0, 0, 0)); // All off
  else if (i == 7)
    colorAll(i, 50, strip.Color( 0, 0, 0)); // All off
}

int getAllianceColor() {
  if (getColorCode() == 2){
    AllianceColor = 0; //red
    return AllianceColor;
  }
  else if (getColorCode() == 3){
    AllianceColor = 1; //blue
    return  AllianceColor;
  }
}

void allianceColorWithSonar(uint8_t cc, uint8_t wait, uint32_t c) {
  uint16_t i=0;
  uint16_t j=46;
  uint16_t k=86;
  if (getAllianceColor() == 0) {
    for(i; i<strip.numPixels(); i++) {
      strip.setPixelColor(i, strip.Color(255,0,0));
    }
    for(j; j<strip.numPixels(); j++) {
      strip.setPixelColor(j, c);
    }
    for(k; k<strip.numPixels(); k++) {
      strip.setPixelColor(k, strip.Color(255,0,0)); 
    }
    strip.show();
  }
  else if (getAllianceColor() == 1) {
    for(i; i<strip.numPixels(); i++) {
      strip.setPixelColor(i, strip.Color(0,0,255));
    }
    for(j; j<strip.numPixels(); j++) {
      strip.setPixelColor(j, c);
    }
    for(k; k<strip.numPixels(); k++) {
      strip.setPixelColor(k, strip.Color(0,0,255)); 
    }
    strip.show();

  }
  int ccc = getColorCode();
  if (ccc != cc)
    return;

}

void colorWipe(uint8_t cc, uint8_t wait, uint32_t c) {
  uint16_t i=66;
  uint16_t j=66;
  for(i; i<strip.numPixels(); i++) {
    j--;
    strip.setPixelColor(i, c);
    strip.setPixelColor(j, c);
    strip.show();
    delay(wait);
    int ccc = getColorCode();
    if (ccc != cc)
      return;
  }
}
int getColorCode()
{
  int rv = 0;
  if ( digitalRead(10) == HIGH)
    rv = rv + 1;
  if ( digitalRead(11) == HIGH)
    rv = rv + 2;
  if ( digitalRead(12) == HIGH)
    rv = rv + 4;
  return rv; 
}
void colorAll(uint8_t cc, uint8_t wait, uint32_t c) {
  for(uint16_t i=0; i<strip.numPixels(); i++) {
    strip.setPixelColor(i, c);
    strip.show();
    int ccc = getColorCode();
    if (ccc != cc)
      return;
  }

}



















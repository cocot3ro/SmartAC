// Sniffer de tramas IR (ESP32)
// Conectar: salida del receptor IR -> GPIO32
// GND común: receptor IR <-> ESP32
//
// Parámetros ajustados para protocolos tipo NEC:
//   - Primer pulso: LOW ~9000us
//   - Segundo pulso: HIGH ~4500us
//   - MIN_PULSO = 350us
//   - GAP = 100000us (100ms en HIGH => fin de trama)

const int dataPin = 32;              // GPIO del receptor IR
const int MAX_PULSOS = 1000;         // Máximo de transiciones por trama
const unsigned long GAP = 100000;    // 100 ms sin cambios => fin de trama
const unsigned long MIN_PULSO = 350; // Ignorar pulsos < 350 us (ruido)

unsigned long duraciones[MAX_PULSOS];
int niveles[MAX_PULSOS];
int indexPulsos = 0;

int lastState;
unsigned long lastTime;

void setup() {
  Serial.begin(115200);
  pinMode(dataPin, INPUT);
  lastState = digitalRead(dataPin);
  lastTime = micros();

  Serial.println("=== Sniffer IR iniciado (MIN_PULSO=350us, GAP=100ms) ===");
}

void loop() {
  int state = digitalRead(dataPin);
  unsigned long now = micros();

  // Detecta cambio de estado
  if (state != lastState) {
    unsigned long duracion = now - lastTime;

    if (duracion >= MIN_PULSO && indexPulsos < MAX_PULSOS) {
      duraciones[indexPulsos] = duracion;
      niveles[indexPulsos] = lastState;
      indexPulsos++;
    }

    lastState = state;
    lastTime = now;
  }

  // Si pasa GAP sin cambios y hay datos -> fin de trama
  if ((now - lastTime) > GAP && indexPulsos > 0) {
    // Comprobar si cumple el "header" esperado (~9000us LOW + ~4500us HIGH)
    bool tramaValida = false;
    if (indexPulsos >= 2) {

      unsigned long t1 = duraciones[1];
      unsigned long t2 = duraciones[2];
      if (niveles[1] == LOW && niveles[2] == HIGH &&
          t1 > 8000 && t1 < 10000 &&  // ~9000 us
          t2 > 4000 && t2 < 5000) {   // ~4500 us
        tramaValida = true;
      }
    }

    if (tramaValida) {
      // Imprimir en formato CSV
      for (int i = 0; i < indexPulsos; i++) {
        Serial.print((niveles[i] == HIGH) ? "+" : "-");
        Serial.print(duraciones[i]);
        if (i < indexPulsos - 1) Serial.print(',');
      }
      Serial.println();
    }

    // Reiniciar buffer para la próxima trama
    indexPulsos = 0;
  }
}

// Sniffer de tramas (ESP32)
// Conectar: DATA (pin 3 del AC) -> divisor 5V->2.5V -> GPIO dataPin
// GND común: GND AC <-> GND ESP32

const int dataPin = 27;               // GPIO conectado (después del divisor)
const int MAX_PULSOS = 5000;          // Máximo de transiciones por trama
const unsigned long GAP = 10000;      // 10 ms sin cambios => fin de trama (en microsegundos)
const unsigned long MIN_PULSO = 500;  // Ignorar pulsos < 500 us (ruido)

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

  Serial.println("=== Sniffer iniciado (MIN_PULSO=500us, GAP=8000us) ===");
}

void loop() {
  int state = digitalRead(dataPin);
  unsigned long now = micros();

  // Detecta cambio de estado
  if (state != lastState) {
    unsigned long duracion = now - lastTime;

    // Filtrado por duración mínima y espacio en buffer
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
    // Imprimir en formato CSV
    for (int i = 0; i < indexPulsos; i++) {
      // ejemplo: prefijo "-" para BAJO, "+" para ALTO
      Serial.print((niveles[i] == HIGH) ? "+" : "-");
      Serial.print(duraciones[i]);
      if (i < indexPulsos - 1) Serial.print(',');
    }
    Serial.println();

    // Reiniciar buffer
    indexPulsos = 0;
  }
}

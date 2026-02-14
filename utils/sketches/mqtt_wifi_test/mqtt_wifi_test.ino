#include <WiFi.h>
#include <PubSubClient.h>

// ----------------------
// Wifi
// ----------------------
const char* ssid = "TpLink_DecoMesh_IoT";
const char* password = "rUnPtUyf$JeAqaKn%bU963%8%KBq3c!c7Pe*XXjbuzeG%fep8H";

// ----------------------
// MQTT
// ----------------------
const char* mqtt_server = "raspberry.lan";
const int mqtt_port = 1883;
const char* mqtt_user = "";
const char* mqtt_pass = "";

WiFiClient espClient;
PubSubClient client(espClient);

// ----------------------
// Callback de mensajes MQTT
// ----------------------
void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Mensaje recibido [");
  Serial.print(topic);
  Serial.print("]: ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();
  
  // Aquí podrías interpretar comandos, por ejemplo:
  // if (strcmp(topic, "ac/control") == 0) {
  //   if (payload[0] == '1') {
  //     encenderAC();
  //   } else {
  //     apagarAC();
  //   }
  // }
}

// ----------------------
// Conectar a MQTT
// ----------------------
void reconnect() {
  while (!client.connected()) {
    Serial.print("Intentando conectar a MQTT...");
    if (client.connect("ESP32Client", mqtt_user, mqtt_pass)) {
      Serial.println("conectado!");
      client.subscribe("ac/control");   // Suscripción a comandos
    } else {
      Serial.print("falló, rc=");
      Serial.print(client.state());
      Serial.println(" intentando de nuevo en 5 segundos");
      delay(5000);
    }
  }
}

// ----------------------
// Setup
// ----------------------
void setup() {
  Serial.begin(115200);
  delay(1000);

  // Conectar a WiFi
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  Serial.print("Conectando a WiFi");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println(" Conectado!");

  // Configurar cliente MQTT
  client.setServer(mqtt_server, mqtt_port);
  client.setCallback(callback);
}

// ----------------------
// Loop principal
// ----------------------
void loop() {
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  // Publicar cada 5 segundos
  static unsigned long lastMsg = 0;
  unsigned long now = millis();
  if (now - lastMsg > 5000) {
    lastMsg = now;
    client.publish("ac/status", "ESP32 activo");
  }
}

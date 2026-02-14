package dev.cocot3ro.smartac.network

import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
import dev.cocot3ro.smartac.core.display.DisplayFrame
import dev.cocot3ro.smartac.core.state.AcStatus
import dev.cocot3ro.smartac.core.state.StateRepository
import dev.cocot3ro.smartac.network.mqtt.MqttConstants

class NetworkRepository(
    private val stateRepository: StateRepository,
    private val mqttClient: Mqtt5AsyncClient
) {

    @OptIn(ExperimentalUnsignedTypes::class)
    fun startMqttConnection() {
        mqttClient.connectWith()
            .keepAlive(30)
            .cleanStart(true)
            .send()
            .join()

        mqttClient.subscribeWith()
            .topicFilter(MqttConstants.Topics.STATE)
            .callback { pub ->
                DisplayFrame.decode(pub.payloadAsBytes.toUByteArray())
                    .onSuccess(stateRepository::updateState)
            }
            .send()

        mqttClient.subscribeWith()
            .topicFilter(MqttConstants.Topics.STATUS)
            .qos(MqttQos.AT_LEAST_ONCE)
            .callback { pub ->
                val message: String = pub.payloadAsBytes.toString(Charsets.UTF_8)
                val status: AcStatus = AcStatus.valueOf(value = message, ignoreCase = true)
                stateRepository.updateStatus(status = status)
            }
            .send()
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun sendIrCommand(data: UByteArray) {
        mqttClient.publishWith()
            .topic(MqttConstants.Topics.COMMAND)
            .payload(data.toByteArray())
            .send()
    }
}

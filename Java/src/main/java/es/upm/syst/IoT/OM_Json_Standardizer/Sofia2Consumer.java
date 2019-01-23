package es.upm.syst.IoT.OM_Json_Standardizer;

import org.fusesource.mqtt.client.QoS;

import com.indra.sofia2.ssap.kp.Kp;
import com.indra.sofia2.ssap.kp.Listener4SIBIndicationNotifications;
import com.indra.sofia2.ssap.kp.SSAPMessageGenerator;
import com.indra.sofia2.ssap.kp.config.ConnectionConfig;
import com.indra.sofia2.ssap.ssap.SSAPMessage;
import com.indra.sofia2.ssap.ssap.SSAPQueryType;
import com.indra.sofia2.ssap.ssap.body.SSAPBodyReturnMessage;

public class Sofia2Consumer {
	private static final String HOST = "sofia2.com";
	private static final int PORT = 1883;
	private static final String TOKEN = "3b2f5d745c4f45ecb293d55f72d06fe4";
	private static final String KP_INSTANCE = "Prod_TemperaturaEjBienvenida:Prod_TemperaturaEjBienvenida 01";
	private static final String ONTOLOGY_NAME = "SensorTemperaturaEjBienvenida";

	public static void main(final String... $args) 
	{
		// Configura los parámetros de la conexión MQTT
		MQTTConnectionConfig config = new MQTTConnectionConfig();
		config.setHostSIB(HOST);
		config.setPortSIB(PORT);
		config.setKeepAliveInSeconds(5);
		config.setQualityOfService(QoS.AT_LEAST_ONCE);
		config.setTimeOutConnectionSIB(5000);

		// Intancia la interfaz del KP con la implementación de MQTT para la
		// configuración indicada
		Kp kp = new KpMQTTClient(config);

		// Crea una conexión MQTT física con el SIB
		kp.connect();

		// Construye un mensaje SSAP JOIN
		SSAPMessage msgJoin = SSAPMessageGenerator.getInstance().generateJoinByTokenMessage(TOKEN, KP_INSTANCE);

		// Envia el mensaje JOIN al SIB
		SSAPMessage responseJoin = kp.send(msgJoin);

		// Recupera la sessionKey
		String sessionKey = responseJoin.getSessionKey();

		// Registra un listener para recibir notificaciones para las suscripciones
		kp.addListener4SIBNotifications(new Listener4SIBIndicationNotifications() {

			/**
			 * Metodo Invocado por el API cuando se recibe una notificacion de suscripción
			 * desde el SIB.
			 */
			@Override
			public void onIndication(String messageId, SSAPMessage ssapMessage) {
				SSAPBodyReturnMessage indicationMessage = SSAPBodyReturnMessage
						.fromJsonToSSAPBodyReturnMessage(ssapMessage.getBody());

				if (indicationMessage.isOk()) {
					System.out.println(indicationMessage.getData());
				}

			}
		});

		// Construye un mensaje SSAP SUBSCRIBE
		SSAPMessage msg = SSAPMessageGenerator.getInstance().generateSubscribeMessage(sessionKey, ONTOLOGY_NAME, 100,
				"select * from " + ONTOLOGY_NAME, SSAPQueryType.SQLLIKE);

		// Envia el mensaje SUBSCRIBE al SIB
		SSAPMessage msgSubscribe = kp.send(msg);

		SSAPBodyReturnMessage responseSubscribeBody = SSAPBodyReturnMessage
				.fromJsonToSSAPBodyReturnMessage(msgSubscribe.getBody());

		// Queda a la espera de recibir notificaciones
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

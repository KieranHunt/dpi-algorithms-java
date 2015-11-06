package casa.kieran.input;

import org.jnetpcap.Pcap;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.packet.Payload;

/**
 * Created by kieran on 2015/11/06.
 */
public class Packet implements Input {

    public Packet(String packetFile) {

        StringBuilder errorBuffer = new StringBuilder();

        final Pcap pcap = Pcap.openOffline(packetFile, errorBuffer);

        pcap.loop(10, new JPacketHandler<StringBuilder>() {

            Payload payload = new Payload();

            public void nextPacket(JPacket packet, StringBuilder errbuf) {
                if (packet.hasHeader(payload)) {
                    packet.getHeader(payload);
                    System.out.println(payload.data());
                }
            }

        }, errorBuffer);
    }

    @Override
    public Character getInputAt(Integer location) {
        return null;
    }

    @Override
    public Integer getLength() {
        return null;
    }

    @Override
    public Integer indexOf(Character character) {
        return null;
    }

    @Override
    public Integer indexOf(String string) {
        return null;
    }

    @Override
    public Integer indexOf(String string, Integer start) {
        return null;
    }
}

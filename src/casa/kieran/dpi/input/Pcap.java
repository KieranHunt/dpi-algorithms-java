package casa.kieran.dpi.input;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Pcap implements Iterable<Input> {

    private static Logger LOGGER = LoggerFactory.getLogger(Pcap.class);

    private List<Input> packets;

    private PcapHandle pcapHandle;

    public Pcap(String pcapLocation) {
        try {
            pcapHandle = org.pcap4j.core.Pcaps.openOffline(pcapLocation, PcapHandle.TimestampPrecision.NANO);
        } catch (PcapNativeException e) {
            e.printStackTrace();
        }

        packets = new ArrayList<>();

        while (true) {
            try {
                org.pcap4j.packet.Packet packet = pcapHandle.getNextPacketEx();
                Packet packetToBeAdded = new Packet(packet);
                packets.add(packetToBeAdded);
            } catch (NotOpenException | PcapNativeException | EOFException | TimeoutException e) {
                break;
            }
        }

        LOGGER.info("Read " + packets.size() + " packets from " + pcapLocation);
    }

    public List<Input> getAllPackets() {
        return packets;
    }

    @Override
    public Iterator<Input> iterator() {
        return packets.iterator();
    }
}

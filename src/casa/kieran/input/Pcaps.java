package casa.kieran.input;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Pcaps implements Iterable<Packet> {
    private List<Packet> packets;

    private PcapHandle pcapHandle;

    public Pcaps(String pcapsLocation) {
        try {
            pcapHandle = org.pcap4j.core.Pcaps.openOffline(pcapsLocation, PcapHandle.TimestampPrecision.NANO);
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
    }

    @Override
    public Iterator<Packet> iterator() {
        return packets.iterator();
    }
}

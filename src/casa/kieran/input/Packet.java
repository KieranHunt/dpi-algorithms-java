package casa.kieran.input;

public class Packet implements Input {

    private org.pcap4j.packet.Packet packet;

    public Packet(final org.pcap4j.packet.Packet packet) {
        this.packet = packet;
    }

    @Override
    public Byte getByte(Integer location) {
        return packet.getRawData()[location];
    }

    @Override
    public Integer getLength() {
        return packet.getRawData().length;
    }
}

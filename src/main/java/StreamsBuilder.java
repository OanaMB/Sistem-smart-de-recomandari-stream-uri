// builder folosit pentru initializarea obiectelor de tip Streams
public class StreamsBuilder {
    private Streams streams = new Streams();

    public StreamsBuilder withStreamType(Integer stringType) {
        streams.setStreamType(stringType);
        return this;
    }

    public StreamsBuilder withId(Integer id) {
        streams.setId(id);
        return this;
    }

    public StreamsBuilder withStreamGenre(Integer streamGenre) {
        streams.setStreamGenre(streamGenre);
        return this;
    }

    public StreamsBuilder withNoOfStreams(Long noOfStreams) {
        streams.setNoOfStreams(noOfStreams);
        return this;
    }

    public StreamsBuilder withStreamerId(Integer streamerId) {
        streams.setStreamerId(streamerId);
        return this;
    }

    public StreamsBuilder withLength(Long length) {
        streams.setLength(length);
        return this;
    }

    public StreamsBuilder withDateAdded(Long dateAdded) {
        streams.setDateAdded(dateAdded);
        return this;
    }

    public StreamsBuilder withName(String name) {
        streams.setName(name);
        return this;
    }

    public Streams build() {
        return streams;
    }
}

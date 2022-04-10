public class Card {
    int value;
    int point = 0;
    boolean used = false;
    boolean closed = false;
    public Card(int value) {
        this.value = value;
        this.point = 0;
        this.closed = false;
        this.used = false;
    }
    public Card flip() {
        if (this.closed) {
            this.closed = false;
            return this;
        }
        else {
            this.closed = true;
            return this;
        }
    }
    public int getValue() {
        return this.value;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + this.value; 
    }
}
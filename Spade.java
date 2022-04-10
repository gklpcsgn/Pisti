public class Spade extends Card {
    public Spade(int value) {
        super(value);
        if (value == 1) {
            point = 1;
        }
        if (value == 11) {
            point = 1;
        }
        if (value == 2) {
            point = 2;
        }
    }
    @Override
    public String toString() {
        if (this.value == 1 ) {
            return "\uD83C\uDCA1";
        }
        else if (this.value == 2) {
            return "\uD83C\uDCA2";
        }
        else if (this.value == 3) {
            return "\uD83C\uDCA3";
        }
        else if (this.value == 4) {
            return "\uD83C\uDCA4";
        }
        else if (this.value == 5) {
            return "\uD83C\uDCA5";
        }
        else if (this.value == 6) {
            return "\uD83C\uDCA6";
        }
        else if (this.value == 7) {
            return "\uD83C\uDCA7";
        }
        else if (this.value == 8) {
            return "\uD83C\uDCA8";
        }
        else if (this.value == 9) {
            return "\uD83C\uDCA9";
        }
        else if (this.value == 10) {
            return "\uD83C\uDCAA";
        }
        else if (this.value == 11) {
            return "\uD83C\uDCAB";
        }
        else if (this.value == 12) {
            return "\uD83C\uDCAD";
        }
        else if (this.value == 13) {
            return "\uD83C\uDCAE";
        }
        else {
            return ((Card)this).toString();
        }

    }
}

public class Heart extends Card {
    public Heart(int value) {
        super(value);
        if (value == 1) {
            point = 1;
        }
        if (value == 11) {
            point = 1;
        }
    }
    @Override
    public String toString() {
        if (this.value == 1 ) {
            return "\uD83C\uDCB1";
        }
        else if (this.value == 2) {
            return "\uD83C\uDCB2";
        }
        else if (this.value == 3) {
            return "\uD83C\uDCB3";
        }
        else if (this.value == 4) {
            return "\uD83C\uDCB4";
        }
        else if (this.value == 5) {
            return "\uD83C\uDCB5";
        }
        else if (this.value == 6) {
            return "\uD83C\uDCB6";
        }
        else if (this.value == 7) {
            return "\uD83C\uDCB7";
        }
        else if (this.value == 8) {
            return "\uD83C\uDCB8";
        }
        else if (this.value == 9) {
            return "\uD83C\uDCB9";
        }
        else if (this.value == 10) {
            return "\uD83C\uDCBA";
        }
        else if (this.value == 11) {
            return "\uD83C\uDCBB";
        }
        else if (this.value == 12) {
            return "\uD83C\uDCBD";
        }
        else if (this.value == 13) {
            return "\uD83C\uDCBE";
        }
        else {
            return ((Card)this).toString();
        }

    }
}
    

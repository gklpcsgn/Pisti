public class Diamond extends Card {
    public Diamond(int value) {
        super(value);
        this.point = 0;
        if (value == 10) {
            point = 3;            
        }
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
            return "\uD83C\uDCC1";
        }
        else if (this.value == 2) {
            return "\uD83C\uDCC2";
        else if (this.value == 3) {
            return "\uD83C\uDCC3";
        }
        else if (this.value == 4) {
            return "\uD83C\uDCC4";
        }
        else if (this.value == 5) {
            return "\uD83C\uDCC5";
        }
        else if (this.value == 6) {
            return "\uD83C\uDCC6";
        }
        else if (this.value == 7) {
            return "\uD83C\uDCC7";
        }
        else if (this.value == 8) {
            return "\uD83C\uDCC8";
        }
        else if (this.value == 9) {
            return "\uD83C\uDCC9";
        }
        else if (this.value == 10) {
            return "\uD83C\uDCCA";
        }
        else if (this.value == 11) {
            return "\uD83C\uDCCB";
        }
        else if (this.value == 12) {
            return "\uD83C\uDCCD";
        }
        else if (this.value == 13) {
            return "\uD83C\uDCCE";
        }
        else {
            return ((Card)this).toString();
        }

    }
}
